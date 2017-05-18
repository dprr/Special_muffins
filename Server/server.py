import requests
import json
import random
import signal
import os

server_name = 'https://special-muffins-12d5c.firebaseio.com'

# test = requests.get(server_name + '/test_noam.json')
# print(test.json())


def parse_range(alarm):
    return 1, 10


def randomize_time(alarm):
    time_range = parse_range(alarm)
    actual_time = random.randrange(time_range[0], time_range[1])
    return actual_time


def get_new_alarms():
    newNF = requests.get(server_name + '/newNF')
    return 'hi'



def alarm_phone(signum, frame):
    pass

def set_alarm(alarm):
    pass


def run_server():
    while True:
        new_alarms = get_new_alarms()
        for alarm in new_alarms:
            alarm_time = randomize_time(alarm)
            break

print(get_new_alarms())