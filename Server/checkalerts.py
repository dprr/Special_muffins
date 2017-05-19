from datetime import datetime
import requests
import json
import time
import time

server_name = 'https://special-muffins-12d5c.firebaseio.com/'
FCMserver = "https://fcm.googleapis.com/fcm/send"
fcm_server_key = "AAAAsMDXeAE:APA91bHD_IQVtEPI-m7NpxMQVgr15qSePPLU7B4S-U2KjSCT6zyFLmwB8R7OFgjkPWazSs1pGxgEygrlKsl3vEkd0Qh1UBTyFWvAB0uoifsk4suuRvfffC38rmPf48V1YorWT3JdaSVD"
headers = {'Content-Type': 'application/json', "Authorization": "key=" + fcm_server_key }


# take alerts from DB (as dictionary), check if any of them need to happen


def execute_alert(alert_json):
    phone_const_id = alert_json["constPhoneId"]
    print(phone_const_id)
    phone = requests.get(server_name + 'phone/'+ phone_const_id + '.json').json()
    phone_firebase_id = phone["firebaseId"]

    json_to_send = {
            "to" : phone_firebase_id,
            "data" : {
                "skeletonId" : alert_json["skeletonId"] }
        }

    requests.post(FCMserver, json=json.dumps(json_to_send), headers=headers)


def check_for_alerts():
    alerts = requests.get(server_name + 'alert' + '.json').json()
    for alert_id, value in alerts.items():
        alert_time_str = value["time"]
        day, month, year, hour, minute = alert_time_str.split(".")
        alert_time = datetime(int(year), int(month), int(day), int(hour), int(minute))
        cur_time = datetime.now()
        if alert_time <= cur_time:
            print(alert_id)
            execute_alert(value)
            requests.delete(server_name + 'alert/' + alert_id + '.json')


while True:
    check_for_alerts()
    time.sleep(60)

