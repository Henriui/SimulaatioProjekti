module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports com.project;
    exports com.project.view;
    opens com.project.view to javafx.fxml;
}
