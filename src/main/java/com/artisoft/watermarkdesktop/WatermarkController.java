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
            Alert alert = new Alert(Alert.AlertType.ERROR, "\u0414\u043b\u044f \u0433\u0435\u043d\u0435\u0440\u0430\u0446\u0438\u0438 \u0434\u043e\u043f\u0443\u0441\u043a\u0430\u0435\u0442\u0441\u044f \u0442\u043e\u043b\u044c\u043a\u043e \u043e\u0434\u043d\u0430 watermark.", ButtonType.OK);
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
            Alert alert = new Alert(Alert.AlertType.ERROR, "\u0424\u0430\u0439\u043b\u044b \u043d\u0435 \u0432\u044b\u0431\u0440\u0430\u043d\u044b", ButtonType.OK);
            alert.showAndWait();
        } else {
            for (File f : this.files) {
                WatermarkService watermarkService = new WatermarkService();
                watermarkService.createWatermark(f, this.watermarkFile);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "\u0424\u0430\u0439\u043b \u0431\u044b\u043b \u0441\u0433\u0435\u043d\u0435\u0440\u0438\u0440\u043e\u0432\u0430\u043d \u0438 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u043d\u0430 \u0440\u0430\u0431\u043e\u0447\u0435\u043c \u0441\u0442\u043e\u043b\u0435!", ButtonType.OK);
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