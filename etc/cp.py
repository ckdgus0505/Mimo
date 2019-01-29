# File : cp.py
# Brief : 라즈베리파이 '/www/var/cp.py' 에 위치 시켜놓고 cp 명령어 대신 실행

import os

os.system("sudo rm -rf html")
os.system('sudo cp -r ~/Documents/GitHub/Mimo/html/ html/')
