package com.project.view;

import java.io.IOException;

import com.project.MainApp;

import javafx.fxml.FXML;

public class TuloksetViewController {

    @FXML
    private void initialize(){
        setListView();
    }

    private void setListView(){
        System.out.println("Ajettu");
    }

    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }
}
