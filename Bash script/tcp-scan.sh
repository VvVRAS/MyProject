gnome-terminal -e 'sudo tcpdump -i wlo1'
#gnome-terminal -e " sudo -S tcpdump 'tcp[13] & 32 != 0'" # ACK ACKNOWLEDGE
#gnome-terminal -e "sudo -S tcpdump 'tcp[13] & 16 != 0'" # PSH PUSH
#gnome-terminal -e "sudo -S tcpdump 'tcp[13] & 8 != 0'"  # RST RESET
#gnome-terminal -e "sudo -S tcpdump 'tcp[13] & 4 != 0'"  # SYN SYNCHRONIZE
#gnome-terminal -e "sudo -S tcpdump 'tcp[13] & 2 != 0'"  # FIN FINISH
