clear
while :
	do 
	{
		echo "Hello World"
		sleep 1
		tput cuu1
		echo "Hello world! Again"
		sleep 1
		tput el 
		printf "\e[A\e[k"
		clear
	}
done
