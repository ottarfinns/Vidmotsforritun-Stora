module vidmot.eventmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens vidmot.eventmanager to javafx.fxml;
    exports vidmot.eventmanager;
    exports vinnsla;
}
