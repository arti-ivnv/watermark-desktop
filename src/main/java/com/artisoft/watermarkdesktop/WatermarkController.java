package com.artisoft.watermarkdesktop;

import com.artisoft.watermarkdesktop.service.WatermarkService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WatermarkController {
    @FXML
    public Rectangle originalDropBox;
    @FXML
    public Rectangle watermarkDropBox;
    @FXML
    public Button generateBtn;
    @FXML
    public Label origFilesLabel;
    @FXML
    public Label watermarkFileLabel;
    @FXML
    public Label titleLabel;
    @FXML
    public Button resetBtn;
    @FXML
    public Label originalDropBoxLabel;
    @FXML
    public Label watermarkDropBoxLabel;
    @FXML
    public Label subTitle;
    @FXML
    public TextArea adText;
    private List<File> files = new ArrayList<File>();
    private List<String> fileNames = new ArrayList<String>();
    private File watermarkFile = null;

    @FXML
    protected void handleDragOver(DragEvent e) {
        if (e.getDragboard().hasFiles()) {
            e.acceptTransferModes(TransferMode.ANY);
            this.originalDropBox.setFill(Color.GREEN);
        }
    }

    @FXML
    protected void dragExit(DragEvent e) {
        this.originalDropBox.setFill(Color.GREY);
    }

    @FXML
    protected void dragExitWatermark(DragEvent e) {
        this.watermarkDropBox.setFill(Color.GREY);
    }

    @FXML
    protected void handleDrop(DragEvent e) {
        List<File> inputFiles = e.getDragboard().getFiles();
        for (File f : inputFiles) {
            this.files.add(f);
            this.fileNames.add(f.getName());
        }
        this.origFilesLabel.setText("Files: " + this.fileNames.stream().map(Objects::toString).collect(Collectors.joining(", ")));
    }

    @FXML
    protected void handleDropWatermark(DragEvent e) {
        if (e.getDragboard().getFiles().size() > 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can upload only one watermark image!", ButtonType.OK);
            alert.showAndWait();
        }
        this.watermarkFile = e.getDragboard().getFiles().get(0);
        this.watermarkFileLabel.setText("File: " + this.watermarkFile.getName());
    }

    @FXML
    protected void resetAction() {
        this.files = new ArrayList<File>();
        this.fileNames = new ArrayList<String>();
        this.watermarkFile = null;
        this.origFilesLabel.setText("Some Files");
        this.watermarkFileLabel.setText("Some File");
    }

    @FXML
    protected void generateWatermark() throws IOException {
        if (this.files == null || this.files.isEmpty() || this.watermarkFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose at least one file!", ButtonType.OK);
            alert.showAndWait();
        } else {
            for (File f : this.files) {
                WatermarkService watermarkService = new WatermarkService();
                watermarkService.createWatermark(f, this.watermarkFile);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Complete! They are located in your Desktop directory!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleDragOverWatermark(DragEvent e) {
        if (e.getDragboard().hasFiles()) {
            e.acceptTransferModes(TransferMode.ANY);
            this.watermarkDropBox.setFill(Color.GREEN);
        }
    }
}