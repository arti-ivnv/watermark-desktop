package com.artisoft.watermarkdesktop;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WatermarkApp extends Application {
    double x,y = 0;

    @Override
    public void start(Stage stage) throws IOException {
//        // Importing visuals
        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("mainScreen.fxml")));
        stage.initStyle(StageStyle.UNDECORATED);


        // Move window
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });


        Scene scene = new Scene(root);

//        // Window settings
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("lol.png")));
        stage.setResizable(false);
        stage.setTitle("Watermark App");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        WatermarkApp.launch(args);
    }
}
