module com.mapstob.group35 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.mapstob.group35 to javafx.fxml;
    exports com.mapstob.group35;
    exports com.mapstob.group35.core;
    opens com.mapstob.group35.core to javafx.fxml;
}