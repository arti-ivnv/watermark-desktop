package com.artisoft.watermarkdesktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class LoadPngController {
    @FXML
    public Label origFilesLabel;
    @FXML
    public Label filesCounter;
    @FXML
    public Rectangle originalDropBox;
    @FXML
    public ImageView exitButton;
    @FXML
    public Rectangle exitButtonFilter;

    private File watermarkFile = null;

    @FXML
    protected void handleDragOver(DragEvent e) {
        if (e.getDragboard().hasFiles()) {
            e.acceptTransferModes(TransferMode.ANY);
            this.originalDropBox.setFill(Color.GREEN);
        }
    }

    @FXML
    protected void clickExitButton(){

    }

    @FXML
    protected void mouseOverExit(MouseEvent e){
        exitButtonFilter.setVisible(true);
    }

    @FXML
    protected void mouseOutExit(MouseEvent e){
        exitButtonFilter.setVisible(false);
    }

    @FXML
    protected void dragExit(DragEvent e) {
        System.out.println(e.getClass().getName());
        originalDropBox.setFill(Color.rgb(72,72,72));
    }

    @FXML
    protected void handleDrop(DragEvent e) {
        if (e.getDragboard().getFiles().size() > 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can upload only one watermark image!", ButtonType.OK);
            alert.showAndWait();
        }
        this.watermarkFile = e.getDragboard().getFiles().get(0);
        this.origFilesLabel.setText("File: " + this.watermarkFile.getName());
    }
}
