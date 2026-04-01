module com.mapstob.group35 {
    requires javafx.controls;
    requires javafx.fxml;
    requires annotations;
    requires com.fasterxml.jackson.databind;

    opens com.mapstob.group35 to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.mapstob.group35;
    exports com.mapstob.group35.core;
    opens com.mapstob.group35.core to javafx.fxml, com.fasterxml.jackson.databind;
}