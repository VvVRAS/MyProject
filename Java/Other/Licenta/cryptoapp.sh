#/bin/bash
javac -module-path $jfx $1.java
java -module-path $jfx $1
