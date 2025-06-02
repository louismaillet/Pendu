javac -d bin --module-path lib --add-modules javafx.controls src/*.java
java -cp bin:img --module-path lib --add-modules javafx.controls Pendu
