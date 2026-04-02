module com.mapstob.group35 {
    requires javafx.controls;
    requires javafx.fxml;
    requires annotations;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.annotation;

    opens com.mapstob.group35 to javafx.fxml;
    exports com.mapstob.group35;
}