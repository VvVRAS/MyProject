while :; do
  date; 
  tcpdump -i wlo1 -n -c 100 'tcp[tcpflags] & (tcp-syn) != 0' & 'tcp[tcpflags] & (tcp-ack) == 0' 2> /dev/null \
  | awk '{ print $3}' \
  | sort | uniq -c | sort | tail -5;
  echo;
  sleep 1
done
