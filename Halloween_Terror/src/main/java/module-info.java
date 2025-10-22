module org.example.halloween_terror {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.halloween_terror to javafx.fxml;
    exports org.example.halloween_terror;
}