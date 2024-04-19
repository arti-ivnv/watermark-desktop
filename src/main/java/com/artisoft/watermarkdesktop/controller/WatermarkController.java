package com.artisoft.watermarkdesktop.controller;

import com.artisoft.watermarkdesktop.app.LoadPngApp;
import com.artisoft.watermarkdesktop.app.LoadTextApp;
import com.artisoft.watermarkdesktop.service.WatermarkService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.artisoft.watermarkdesktop.utils.BrowserUtils;
import com.artisoft.watermarkdesktop.utils.GlobalDataHandler;
import com.artisoft.watermarkdesktop.utils.TextWatermark;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Border;
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
    public ImageView exitButton;
    @FXML
    public Rectangle exitButtonFilter;
    @FXML
    public Label filesCounter;
    private List<File> files = new ArrayList<>();
    private List<String> fileNames = new ArrayList<>();
    private File watermarkFile = null;
    private TextWatermark textWatermark = null;


    @FXML
    protected void handleDragOver(DragEvent e) {
        if (e.getDragboard().hasFiles()) {
            e.acceptTransferModes(TransferMode.ANY);
            this.originalDropBox.setFill(Color.GREEN);
        }
    }

    @FXML
    protected void clickExitButton(){
        System.exit(0);
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
        List<File> inputFiles = e.getDragboard().getFiles();
        for (File f : inputFiles) {
            this.files.add(f);
            this.fileNames.add(f.getName());
        }
        this.origFilesLabel.setText("Files: " + this.fileNames.stream().map(Objects::toString).collect(Collectors.joining(", ")));
        this.filesCounter.setText("Amount: " + files.size());
    }


    @FXML
    protected void resetAction() {
        this.files = new ArrayList<>();
        this.fileNames = new ArrayList<>();
        watermarkFile = null;
        this.origFilesLabel.setText("No file(s)");
        watermarkFileLabel.setText("No watermark file");
        filesCounter.setText("Amount: 0");
        GlobalDataHandler.setFile(null);
    }



    @FXML
    protected void openLinkOnClick(Event event) throws MalformedURLException {
        Hyperlink hyperlink = (Hyperlink)event.getSource();
        String linkPressed = hyperlink.getId();
        BrowserUtils.openWebpage(new URL(BrowserUtils.links.get(linkPressed)));
        hyperlink.setBorder(Border.EMPTY);
        hyperlink.setPadding(new Insets(4, 0, 4, 0));
    }

    @FXML
    protected void pngLoader() throws IOException {

        URL url = this.getClass().getResource("/com/artisoft/watermarkdesktop/pngLoadScreen.fxml");
        new LoadPngApp(url);

        watermarkFile = GlobalDataHandler.getFile();
        if(watermarkFile != null)
            watermarkFileLabel.setText("File: " + watermarkFile.getName());
    }

    @FXML
    protected void textLoader() throws IOException {

        URL url = this.getClass().getResource("/com/artisoft/watermarkdesktop/textWatermarkScreen.fxml");
        new LoadTextApp(url);
    }


    @FXML
    protected void generateWatermark() throws IOException {

        checkWatermark();

        if (this.files == null || this.files.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose at least one file!", ButtonType.OK);
            alert.showAndWait();


        } else {

            WatermarkService watermarkService = new WatermarkService();

            for (File f : this.files) {
                if (watermarkFile != null)
                    watermarkService.createPngWatermark(f, watermarkFile);
                else if (textWatermark != null)
                    watermarkService.createTextWatermark(f, textWatermark);

            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Complete! They are located in your Desktop directory!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    protected void checkWatermark(){
        watermarkFile = GlobalDataHandler.getFile();
        textWatermark = GlobalDataHandler.getTextWatermark();
        if (watermarkFile != null) {
            watermarkFileLabel.setText("File: " + watermarkFile.getName());
        }
        else if (textWatermark != null) {
            watermarkFileLabel.setText("File: text watermark");
        }
        else{
            watermarkFileLabel.setText("Error: You have not chose a watermark!");
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