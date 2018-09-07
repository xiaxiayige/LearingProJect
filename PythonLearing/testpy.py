import requests
import io
import json
import os
try:
    files = {'file': io.open('filepath', 'rb')}
    print(files)
    param = {"_api_key": "input your api key"}
    req = requests.post('http://www.pgyer.com/apiv2/app/upload',data=param,files=files)
    print("success: %s" %  req.json)
except Exception as e:
    print("error %s" % e)