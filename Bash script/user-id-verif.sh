#!/bin/bash
USERID="$1"
DETECTED=$( egrep -o "^$USERID:" < /etc/passwd )
if [[ -n "${DETECTED}" ]] ; then
echo -e "\e[1;92m $USERID -- SAFE \e[0m"
else
echo -e "\e[1;91m $USERID -- ALERT!!! \e[0m"
fi
