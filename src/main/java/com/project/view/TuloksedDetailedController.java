package com.project.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TuloksedDetailedController {
    
    private NewSimulationController controller = new NewSimulationController();

    @FXML
    private Button removeButton;
    @FXML
    private Button saveButton;

    @FXML
    private void remove(){
        Stage stage = (Stage) removeButton.getScene().getWindow();
        controller.popupOpen(false);
        stage.close();
    }

    @FXML
    private void save(){
        
    }
}
