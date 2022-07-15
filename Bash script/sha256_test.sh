#/bin/bash
clear
if [ -n "$2" ]
	then 
		sha256_s=$2
	else
		read -p "Write SHA256-Verify_code: " sha256_v
		sha256_s=$(sha256sum $1 | cut -d" " -f 1)
fi

if [[ $sha256_v == $sha256_s ]]
	then
		echo -e "\e[32m SHA256 CODE MATCHED\e[0m"
	else
		echo -e "\e[31m SHA256 CODE NOT MATCHED\e[0m"
		echo "---------------------------------------------------"
		echo "SHA256-File_code"
		echo "$sha256_s"
		echo "---------------------------------------------------"
		echo "SHA256-Verify_code"
		echo "$sha256_v"
		echo "---------------------------------------------------"
fi
