read -p "Write port numeber: " port
echo "-------------------------------------------------"
sudo kill $(sudo lsof -t -i:$port)
