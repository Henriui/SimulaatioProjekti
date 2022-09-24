module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.project to javafx.fxml;

    exports com.project;
}
