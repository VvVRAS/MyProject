while [ True ]; do
	clear
	echo "----------------------------------------------------------------"
	lsblk
	echo "----------------------------------------------------------------"
	read -p "Write selected partition /dev/" sdxx
	echo "----------------------------------------------------------------"
	partic="/dev/"$sdxx
	read -p "Its corect $partic ? [y/n] " select
	echo "----------------------------------------------------------------"
	if [[ $select -eq "y" ]]; then
		clear
		break
	fi
done
usrcod=$(lsblk -f | grep "$sdxx" | cut -f 9 -d " ")
echo "--------------------------------"
echo "| USB USER CODE IS \"$usrcod\" |"
echo "--------------------------------"
umountloc=lsblk | grep $sdxx | cut -d " " -f 13
echo $umountloc
sleep 2
sudo umount $umountloc
echo "Unmounting..."
echo "Mounting..."
sudo mount -U $usr_cd /mnt
