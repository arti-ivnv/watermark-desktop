package com.artisoft.watermarkdesktop;

import com.artisoft.watermarkdesktop.service.WatermarkService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WatermarkController {

    @FXML
    public Rectangle originalDropBox;
    @FXML
    public Rectangle watermarkDropBox;
    @FXML
    public Button generateBtn;

    private List<File> files = null;
    private File watermarkFile = null;



    @FXML
    protected void handleDragOver(DragEvent e) {
        if (e.getDragboard().hasFiles()){
            e.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    protected void handleDrop(DragEvent e){
        files = e.getDragboard().getFiles();

    }

    @FXML
    protected void handleDropWatermark(DragEvent e){
        if(e.getDragboard().getFiles().size() > 1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Для генерации допускается только одна watermark.", ButtonType.OK);
            alert.showAndWait();
        }
        watermarkFile = e.getDragboard().getFiles().get(0);

    }

    @FXML
    protected void generateWatermark() throws IOException {
        if(files == null || files.isEmpty() || watermarkFile == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Файлы не выбраны", ButtonType.OK);
            alert.showAndWait();
        } else {
            // proceed service
            WatermarkService watermarkService = new WatermarkService();
            for (File f : files){
                System.out.println("original files: " + f.getName());
            }
            System.out.println("watermark: " + watermarkFile.getName());

            watermarkService.createWatermark(files.get(0), watermarkFile);
        }

        // Cleaning up
        files = null;
        watermarkFile = null;
    }
}