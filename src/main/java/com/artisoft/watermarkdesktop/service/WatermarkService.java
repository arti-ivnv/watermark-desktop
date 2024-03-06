package com.artisoft.watermarkdesktop.service;

//import javax.imageio.ImageIO;
//import java.awt.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.List;

import jakarta.xml.bind.DatatypeConverter;
import org.apache.tika.Tika;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class WatermarkService {

    public String generateWatermark(List<File> originalFiles, List<File> watermarkFiles) throws IOException {

        String originalImg ;
        String watermarkImg;
        String text = "";

        String img = null;


        byte[] base64Bytes = DatatypeConverter.parseBase64Binary(img);
        byte[] imgBytes = Base64.getDecoder().decode(img);



        Tika tika = new Tika();
        String fileType = tika.detect(base64Bytes).split("/")[1];


        InputStream is = new ByteArrayInputStream(imgBytes);
        BufferedImage bi = ImageIO.read(is);

        Font f = new Font("TimesRoman", Font.BOLD, 10);


        switch (fileType) {
            case "png":
                Graphics2D g = bi.createGraphics();

                g.setColor(new Color(.128f, .128f, .128f, .3f));
                g.setFont(f);

                boolean shift = false;
                for (int i = 0; i < bi.getHeight(); i += 30){

                    if (shift){
                        for(int j = 100; j < bi.getWidth(); j += 200){
                            g.drawString(text, j, i);
                        }
                        shift = false;
                    } else {
                        for(int j = 0; j < bi.getWidth(); j += 200){
                            g.drawString(text, j, i);
                        }
                        shift = true;
                    }
                }

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(bi, "png", os);

                System.out.println("Width: " + bi.getWidth());
                System.out.println("Heighy: " + bi.getHeight());
                return Base64.getEncoder().encodeToString(os.toByteArray());
            case "jpg":
            case "jpeg":
                System.out.println("Jpeg");
                break;

            default:
                break;
        }

        return null;
    }
}
