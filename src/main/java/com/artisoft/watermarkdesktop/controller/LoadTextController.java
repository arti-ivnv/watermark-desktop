package com.artisoft.watermarkdesktop.controller;

import com.artisoft.watermarkdesktop.service.WatermarkService;
import com.artisoft.watermarkdesktop.utils.GlobalDataHandler;
import com.artisoft.watermarkdesktop.utils.TextWatermark;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;


public class LoadTextController implements Initializable {
    @FXML
    public Button operationButton;
    @FXML
    public Rectangle exitButtonFilter;
    @FXML
    public ImageView exitButton;
    @FXML
    public TextField watermarkTextField;
    @FXML
    public ComboBox<String> watermarkPatternComboBox;
    @FXML
    public ColorPicker watermarkColorPicker;
    @FXML
    public Spinner<Integer> fontCounter;


    @FXML
    protected void clickExitButton(MouseEvent e){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void clickOperationButton(MouseEvent e){
        java.awt.Color color = new java.awt.Color((float)watermarkColorPicker.getValue().getRed(),
                (float)watermarkColorPicker.getValue().getGreen(),
                (float)watermarkColorPicker.getValue().getBlue(),
                (float)watermarkColorPicker.getValue().getOpacity()
        );


        GlobalDataHandler.setTextWatermark(new TextWatermark(watermarkTextField.getText(), fontCounter.getValue(), color, watermarkPatternComboBox.getValue()));
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

            GlobalDataHandler.setFile(e.getDragboard().getFiles().getFirst());

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fontCounter.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100));
        watermarkPatternComboBox.setItems(FXCollections.observableArrayList(GlobalDataHandler.getPatterns()));
    }
}
