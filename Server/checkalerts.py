from datetime import datetime
import requests
import json
server_name = 'https://special-muffins-12d5c.firebaseio.com/'
FCMserver = "https://fcm.googleapis.com/fcm/send"
fcm_server_key = "AAAAsMDXeAE:APA91bHD_IQVtEPI-m7NpxMQVgr15qSePPLU7B4S-U2KjSCT6zyFLmwB8R7OFgjkPWazSs1pGxgEygrlKsl3vEkd0Qh1UBTyFWvAB0uoifsk4suuRvfffC38rmPf48V1YorWT3JdaSVD"
headers = {'Content-Type': 'application/json', "Authorization": "key=" + fcm_server_key }


# take alerts from DB (as dictionary), check if any of them need to happen


def execute_alert(alert_json):
    phone_const_id = alert_json["constPhoneId"]
    phone = requests.get(server_name + 'phone/'+ phone_const_id + '.json').json()
    phone_firebase_id = phone["firebaseId"]

    json_to_send = {
            "to": phone_firebase_id,
            "data" : {
                "skeletonId" : alert_json["skeletonId"] }
        }

    requests.post(FCMserver, json=json.dumps(json_to_send), headers=headers)


def check_for_alerts():
    alerts = requests.get(server_name + 'alert' + '.json').json()
    for alert_id, value in alerts.items():
        alert_time_str = value["time"]
        year, month, day, hour, minute = alert_time_str.split(".")
        alert_time = datetime(year, month, day, hour, minute)
        cur_time = datetime.now()
        if alert_time >= cur_time:
            execute_alert(value)
