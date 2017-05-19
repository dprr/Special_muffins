from datetime import date, timedelta, datetime
import time
import json
import random
import requests

server_name = 'https://special-muffins-12d5c.firebaseio.com/'

def parse_hour(skeleton):
    start_hour = (int(skeleton['startHour'][:2]), int(skeleton['startHour'][3:]))
    end_hour = (int(skeleton['endHour'][:2]), int(skeleton['endHour'][3:]))
    date.today().weekday()

    if end_hour[0] < start_hour[0]:
        start_hour[0] += 24
    actual_time = random.randrange(start_hour[0]*100 + start_hour[1],
                                   end_hour[0]*100 + end_hour[1] + 1) % 2400
    if actual_time % 100 > 60:
        actual_time -= 60
    return "%d.%d" % (actual_time // 100,  actual_time % 100)


def parse_date(possible_days, is_new_alarm):
    print(possible_days)
    today = date.today()
    if not is_new_alarm:
        today += timedelta(2)
    today_weekday = today.weekday()
    possible_dates = [i for i in range(today_weekday, today_weekday + 7) if int(possible_days[i % 7])]
    actual_date = today + timedelta(random.choice(possible_dates) - today_weekday)
    return "%d.%d.%d" % (actual_date.day, actual_date.month, actual_date.year)


def parse_time(skeleton, is_new_alarm=True):
    return parse_date(skeleton['possibleDays'], is_new_alarm) + '.' + parse_hour(skeleton)


def update_branch(branch_name, new_key, new_val):
    address = server_name + branch_name + '.json'
    branch = requests.get(address).json()
    branch[new_key] = new_val
    requests.put(address, data=json.dumps(branch))


def create_new_alarms():
    new_skeletons = requests.get(server_name + 'newAddedSkeleton' + '.json').json()
    if new_skeletons is None:
        return
    for skeleton_id in new_skeletons:
        skeleton = requests.get(server_name + 'skeleton/' + skeleton_id + '.json').json()
        time = parse_time(skeleton)
        r = requests.post(server_name + 'alert' + '.json',
                          data=json.dumps({'constPhoneId': skeleton['constPhoneId'],
                                           'skeletonId': skeleton_id, 'time': time}))
        skeleton['nextAlert'] = r.json()['name']
        requests.put(server_name + 'skeleton/' + skeleton_id + '.json', data=json.dumps(skeleton))
        requests.delete(server_name + 'newAddedSkeleton/' + skeleton_id + '.json')

        
def add_alarm(skeleton_id):
    skeleton = requests.get(server_name + 'skeleton/' + skeleton_id + '.json').json()
    if skeleton is None:
        return
    time = parse_time(skeleton)
    r = requests.post(server_name + 'alert' + '.json',
                      data=json.dumps({'constPhoneId': skeleton['constPhoneId'],
                                       'skeletonId': skeleton_id, 'time': time}))
    skeleton['nextAlert'] = r.json()['name']
    

def edit_alarms():
    edited_skeletons = requests.get(server_name + 'editedSkeleton' + '.json').json()
    if edited_skeletons is None:
        return
    for skeleton_id in edited_skeletons:
        skeleton = requests.get(server_name + 'skeleton/' + skeleton_id + '.json').json()
        requests.delete(server_name + 'alarm/' + skeleton_id + '.json')
        time = parse_time(skeleton)
        r = requests.post(server_name + 'alert' + '.json',
                          data=json.dumps({'constPhoneId': skeleton['constPhoneId'],
                                           'skeletonId': skeleton_id, 'time': time}))
        skeleton['nextAlert'] = r.json()['name']
        requests.put(server_name + 'skeleton/' + skeleton_id + '.json', data=json.dumps(skeleton))
        requests.delete(server_name + 'editedSkeleton/' + skeleton_id + '.json')



FCMserver = "https://fcm.googleapis.com/fcm/send"
fcm_server_key = "AAAAsMDXeAE:APA91bHD_IQVtEPI-m7NpxMQVgr15qSePPLU7B4S-U2KjSCT6zyFLmwB8R7OFgjkPWazSs1pGxgEygrlKsl3vEkd0Qh1UBTyFWvAB0uoifsk4suuRvfffC38rmPf48V1YorWT3JdaSVD"
headers = {'Content-Type': 'application/json', "Authorization": "key=" + fcm_server_key }


# take alerts from DB (as dictionary), check if any of them need to happen


def execute_alert(alert_json):
    phone_const_id = alert_json["constPhoneId"]
    if phone_const_id is None:
        return None
    print(phone_const_id)
    phone = requests.get(server_name + 'phone/'+ phone_const_id + '.json').json()
    phone_firebase_id = phone["firebaseId"]

    json_to_send = {
        "to" : phone_firebase_id,
        "data" : {
            "skeletonId" : alert_json["skeletonId"] }
    }
    requests.post(FCMserver, json.dumps(json_to_send), headers=headers)
    return alert_json["skeletonId"]


def check_for_alerts():
    alerts = requests.get(server_name + 'alert' + '.json').json()
    if alerts is None:
        return
    for alert_id, value in alerts.items():
        alert_time_str = value["time"]
        day, month, year, hour, minute = alert_time_str.split(".")
        alert_time = datetime(int(year), int(month), int(day), int(hour), int(minute))
        cur_time = datetime.now()
        if alert_time <= cur_time:
            print(alert_id)
            # testExecute()
            skeleton_id = execute_alert(value)
            if skeleton_id is None:
                requests.delete(server_name + 'alert/' + alert_id + '.json')
                return
            add_alarm(skeleton_id)
            requests.delete(server_name + 'alert/' + alert_id + '.json')

def testExecute():
    print("in test")
    json_to_send = {"to": "dzO2vBTwE9E:APA91bF98HYKYwV4flxTdz-91DSp4VqU_d04Uha7Yqd43jeGN6ONQfrXN2IKVonWlKJqMWnal5sX2_m52Ue3ZUu2m5MMcT0yTcQEzIzRDXaIa0il16qXluz3z3z7uB1nBRHSQJeCJz4s",
                    "data": { "skeletonId": "h34e85ll652o" }}
    MSG = requests.post(FCMserver, json.dumps(json_to_send), headers=headers)


def run_server():
    while True:
        print('hi')
        create_new_alarms()
        print('create')
        edit_alarms()
        print('edit')
        check_for_alerts()
        print('check')
        time.sleep(30)

if __name__ == '__main__':
    run_server()
