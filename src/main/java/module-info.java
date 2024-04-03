module com.artisoft.watermarkdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.tika.core;
    requires jakarta.xml.bind;
    requires java.desktop;
    requires java.sql;


    opens com.artisoft.watermarkdesktop to javafx.fxml;
    exports com.artisoft.watermarkdesktop;
    exports com.artisoft.watermarkdesktop.app;
    opens com.artisoft.watermarkdesktop.app to javafx.fxml;

    opens com.artisoft.watermarkdesktop.controller to javafx.fxml;
    exports com.artisoft.watermarkdesktop.controller;

}