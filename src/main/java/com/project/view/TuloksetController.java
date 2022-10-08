package com.project.view;

import java.io.IOException;

import com.project.MainApp;
import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.Tulokset;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableView;

public class TuloksetController {

    @FXML
    private TableView tw;

    private ITuloksetDAO tulokset = new TuloksetDAO();
    private ObservableList<Tulokset> tol = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        setTableView();
    }

    private void setObservableList() {
        tulokset.openConnection();
        int r = tulokset.getRowCount();
        for (int i = 0; i < r; i++) {
            //tol.add(new Tulokset(tulokset.queryTulos(i)));
        }
    }

    private void setTableView() {
        tw.setItems(null);
        System.out.println("Ajettu");
    }

    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }
}
