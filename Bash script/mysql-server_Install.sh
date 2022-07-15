#/bin/bash
clear

if [ $EUID -ne 0 ]; then
	echo "Please run as ROOT otherwise this script will not run"
	sleep 1
	exit

	else

		sleep 1
	
		echo "Waiting."
		sudo systemctl stop mysql &> /dev/null
		tput cuu1
		
		echo "Waiting.."
		sudo apt-get purge mysql-server mysql-client mysql-common mysql-server-core-* mysql-client-core-* &> /dev/null
		sudo apt-get remove --purge mysql* &> /dev/null
		tput cuu1
		
		echo "Waiting..."
		sudo rm -rf /etc/mysql /var/lib/mysql &> /dev/null
		tput cuu1
		
		echo "Waiting...."
		sudo apt autoremove &> /dev/null
		tput cuu1
		
		echo "Waiting....."
		sudo apt autoclean &> /dev/null
		tput cuu1
fi
