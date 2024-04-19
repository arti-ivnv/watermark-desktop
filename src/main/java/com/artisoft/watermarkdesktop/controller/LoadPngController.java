package com.artisoft.watermarkdesktop.controller;

import com.artisoft.watermarkdesktop.service.WatermarkService;
import com.artisoft.watermarkdesktop.utils.GlobalDataHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


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

    @FXML
    protected void handleDragOver(DragEvent e) {
        if (e.getDragboard().hasFiles()) {
            e.acceptTransferModes(TransferMode.ANY);
            this.originalDropBox.setFill(Color.GREEN);
        }
    }

    @FXML
    protected void clickExitButton(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void clickOperationButton(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }




    @FXML
    protected void mouseOverExit(){
        exitButtonFilter.setVisible(true);
    }

    @FXML
    protected void mouseOutExit(){
        exitButtonFilter.setVisible(false);
    }

    @FXML
    protected void dragExit() {
        originalDropBox.setFill(Color.rgb(72,72,72));
    }

    @FXML
    protected void handleDrop(DragEvent e) {
        WatermarkService watermarkService = new WatermarkService();
        if (e.getDragboard().getFiles().size() > 1) {
            this.origFilesLabel.setText("ERROR: You can upload only one watermark image!");
        } else if (!watermarkService.isWatermarkPng(e.getDragboard().getFiles().getFirst())){
            this.origFilesLabel.setText("ERROR: Watermark can only be a PNG picture!");
        }else {
            operationButton.setBackground(Background.fill(Color.rgb(30, 162, 20)));
            operationButton.getStyleClass().remove("button-back");
            DropShadow drop_shadow = new DropShadow(BlurType.ONE_PASS_BOX, Color.rgb(30, 162, 20, 0.41), 0, 0, 6, 6);
            operationButton.setEffect(drop_shadow);
            operationButton.setText("DONE");
            this.origFilesLabel.setText("File: " + e.getDragboard().getFiles().getFirst().getName());

            GlobalDataHandler.setFile(e.getDragboard().getFiles().getFirst());

        }
    }

}
