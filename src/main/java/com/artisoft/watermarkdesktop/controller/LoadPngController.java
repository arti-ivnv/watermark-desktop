package com.artisoft.watermarkdesktop.controller;

import com.artisoft.watermarkdesktop.utils.GlobalDataHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;

public class LoadPngController {
    @FXML
    public Label origFilesLabel;
    @FXML
    public Rectangle originalDropBox;
    @FXML
    public ImageView exitButton;
    @FXML
    public Rectangle exitButtonFilter;
    @FXML
    public Button operationButton;

    public File watermarkFile = null;


    @FXML
    protected void handleDragOver(DragEvent e) {
        if (e.getDragboard().hasFiles()) {
            e.acceptTransferModes(TransferMode.ANY);
            this.originalDropBox.setFill(Color.GREEN);
        }
    }

    @FXML
    protected void clickExitButton(MouseEvent e){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void clickOperationButton(MouseEvent e){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
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
        } else {
            operationButton.setBackground(Background.fill(Color.rgb(30, 162, 20)));
            operationButton.getStyleClass().remove("button-back");
            DropShadow drop_shadow = new DropShadow(BlurType.ONE_PASS_BOX, Color.rgb(30, 162, 20, 0.41), 0, 0, 6, 6);
            operationButton.setEffect(drop_shadow);
            operationButton.setText("DONE");
            this.origFilesLabel.setText("File: " + e.getDragboard().getFiles().get(0).getName());

            GlobalDataHandler.setFile(e.getDragboard().getFiles().get(0));

        }
    }

}
