##Introduc date
echo "Name:"
read name
clear
#--------------------------------
echo "Phone number:"
read phone
phone=+4$phone
clear
#-------------------------------
echo "E-mail"
read email
clear
#-------------------------------
echo "Function"
read fct
clear
#-------------------------------
## for ((i=0; i<${#name}; i++)); do
## echo "${name:$i:1}"
## done
#-----------------------------
#nume
for ((i=0; i<${#name}; i++)); do
echo "${name:$i:1}"
clear
done
j=28-$i
#----------------------------------
for ((j1=0; j1<=$j; j1++)); do
name=$name-
done
#----------------------------------
#nr telefon
for ((ip=0; ip<${#phone}; ip++)); do
echo "${phone:$ip:1}"
clear
done
jp=14-$ip
#------------------------------
for ((jp1=0; jp1<=$jp; jp1++)); do
phone=$phone-
done
#-----------------------------------
#E-mail
for ((ie=0; ie<${#email}; ie++)); do
echo "${email:$ie:1}"
clear
done
je=20-$ie
#------------------------------
for ((je1=0; je1<=$je; je1++)); do
email=$email-
done
#-----------------------------------
cd /root/linux/prog/data_base
echo $name-$phone-$email----$fct >> data_base.txt
cd /root
