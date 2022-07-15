sudo systemctl stop tftp
sudo systemctl stop apache2
sudo /etc/init.d/smbd stop
sudo /etc/init.d/tftp stop
sudo /etc/init.d/xinetd stop
sudo /etc/init.d/cupsys stop 
sudo kill $(sudo lsof -t -i:5353) #zeroconf/unknown
sudo kill $(sudo lsof -t -i:631)
sudo kill $(sudo lsof -t -i:137)
sudo kill $(sudo lsof -t -i:138)
#port 111
systemctl stop rpcbind
sleep 1
systemctl disable rpcbind
sleep 1
systemctl mask rpcbind
sleep 1
systemctl stop rpcbind.socket
sleep 1
systemctl disable rpcbind.socket
sleep 1
systemctl status rpcbind
ss -tpna|grep 111
