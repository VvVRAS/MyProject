echo "------------------------------------------------------------------------"
echo "Compile"
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml $1.java
echo "------------------------------------------------------------------------"
echo "Run"
java --module-path $PATH_TO_FX --add-modules javafx.controls $1
echo "------------------------------------------------------------------------"
