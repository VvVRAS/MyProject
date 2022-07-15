#/bin/bash
clear
case $1 in
list)
sudo rfkill list
;;
stop) 
sudo rfkill block all
sudo systemctl disable network-manager
sudo systemctl stop network-manager
;;
start)
sudo rfkill unblock all
sudo systemctl enable network-manager
sudo systemctl start network-manager
;;
*) echo -e "\e[31mERROR \tERROR \tERROR \tERROR \tERROR \t\e[0m"
echo -e "list \t\t Print list\nstop \t\t stop network\nstart \t\t start network\nrestart \t restart network board"
;;
esac
