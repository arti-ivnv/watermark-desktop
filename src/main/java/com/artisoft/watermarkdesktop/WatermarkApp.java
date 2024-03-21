package com.artisoft.watermarkdesktop;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WatermarkApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Importing visuals
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);

        // Setting styles
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Window settings
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
