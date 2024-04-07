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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
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
    protected void clickExitButton(){
        System.exit(0);
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
        this.files = new ArrayList<File>();
        this.fileNames = new ArrayList<String>();
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
        if(watermarkFile != null) {
            watermarkFile = GlobalDataHandler.getFile();

            if (this.files == null || this.files.isEmpty() || watermarkFile == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose at least one file!", ButtonType.OK);
                alert.showAndWait();
            } else {
                for (File f : this.files) {
                    WatermarkService watermarkService = new WatermarkService();
                    watermarkService.createWatermark(f, watermarkFile);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Complete! They are located in your Desktop directory!", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    @FXML
    protected void checkWatermark(){
//        System.out.println(GlobalDataHandler.getFile().getName());
        watermarkFile = GlobalDataHandler.getFile();
        if(watermarkFile != null)
            watermarkFileLabel.setText("File: " + watermarkFile.getName());
    }



    @FXML
    public void handleDragOverWatermark(DragEvent e) {
        if (e.getDragboard().hasFiles()) {
            e.acceptTransferModes(TransferMode.ANY);
            this.watermarkDropBox.setFill(Color.GREEN);
        }
    }
}