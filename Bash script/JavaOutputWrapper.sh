#/bin/bash

macOutput = $1

echo $macOutput

echo "Mac wrapped test"
echo "MAC verif"
MACold = ifconfig wlo1 | grep -o -E '([[:xdigit:]]{1,2}:){5}[[:xdigit:]]{1,2}'

if (( $macOutput -eq $MACold )) {
  echo $macOutput $MACold
} else {
  echo -e "0;31 FAIL 1;37"
}
  
