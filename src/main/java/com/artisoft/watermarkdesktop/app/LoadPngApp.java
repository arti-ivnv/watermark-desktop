package com.artisoft.watermarkdesktop.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class LoadPngApp {

    private double x,y = 0;

    public LoadPngApp(URL url) throws IOException {
        start(url);
    }

    private void start(URL url) throws IOException {
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
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
//        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("lol.png")));
        stage.setResizable(false);
        stage.setTitle("Load PNG");
        stage.setScene(scene);
        stage.show();
    }

    public void stop(){
        this.stop();
    }
}
