import requests
import json

server_name = 'https://special-muffins-12d5c.firebaseio.com'

test = requests.get(server_name + '/test_noam.json')

print(json.loads(test.content))


