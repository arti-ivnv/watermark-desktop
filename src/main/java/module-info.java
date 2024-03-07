module com.artisoft.watermarkdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.tika.core;
    requires jakarta.xml.bind;
    requires java.desktop;
    //requires these packages
    requires java.sql;


    opens com.artisoft.watermarkdesktop to javafx.fxml;
    exports com.artisoft.watermarkdesktop;
}