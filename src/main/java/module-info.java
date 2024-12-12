module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens main to javafx.fxml;
    exports main;
}
