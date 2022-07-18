#!/bin/bash

REPOs=( 
  "/home/user/Documents/MyProject/Bash"
  "/home/user/Documents/MyProject/Java"
  "/home/user/Documents/MyProject/Phyton"
)

echo -e "/n/n Getting latest" ${#REPOs[@]} "repositories using pull --rebase"

for REP in "${REPOs[@]}"
do
  echo -e "/n---------- Getting latest for " ${REP} " -----------"
  cd "${REP}"
  git pull --rebase
  echo "---------------------------------------------"
done
