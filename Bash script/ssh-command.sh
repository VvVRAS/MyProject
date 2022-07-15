#/bin/bash
ssh user@192.168.1.1 "enable; show ip int br"

sshpass -p cisco1234 ssh user@192.168.1.1

scp -r /home/user/test.txt --rsh="sshpass -f pass_file ssh -l user" 192.168.1.12:/home/rpi/text.txt
