from datetime import date, timedelta
import time
import json
import random
import requests

from checkalerts import check_for_alerts
server_name = 'https://special-muffins-12d5c.firebaseio.com/'


def parse_hour(skeleton):
    start_hour = (int(skeleton['startHour'][:2]), int(skeleton['startHour'][3:]))
    end_hour = (int(skeleton['endHour'][:2]), int(skeleton['endHour'][3:]))
    date.today().weekday()

    if end_hour[0] < start_hour[0]:
        start_hour[0] += 24
    actual_time = random.randrange(start_hour[0]*100 + start_hour[1],
                                   end_hour[0]*100 + end_hour[1]) % 2400
    if actual_time % 100 > 60:
        actual_time -= 60
    return "%d.%d" % (actual_time % 100,  actual_time // 100)


def parse_date(possible_days):
    today = date.today()
    today_weekday = today.weekday()
    possible_dates = [i for i in range(today_weekday, today_weekday + 7) if int(possible_days[i % 7])]
    actual_date = today + timedelta(random.choice(possible_dates))
    return "%d.%d.%d" % (actual_date.day, actual_date.month, actual_date.year)


def parse_time(skeleton):
    return parse_date(skeleton['possibleDays']) + '.' + parse_hour(skeleton)


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


def run_server():
    while True:
        create_new_alarms()
        edit_alarms()
        check_for_alerts()
        time.sleep(300)
