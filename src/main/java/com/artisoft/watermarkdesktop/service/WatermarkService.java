package com.artisoft.watermarkdesktop.service;

//import javax.imageio.ImageIO;
//import java.awt.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

import jakarta.xml.bind.DatatypeConverter;
import org.apache.tika.Tika;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.filechooser.FileSystemView;

// Get the files and convert them to base64bytes
//

public class WatermarkService {


    public void createWatermark(File originalFile, File watermarkFile) throws IOException {

        String text = "Hello";

        byte[] fileContent = imageToBytes(originalFile);
        String originalImg = Base64.getEncoder().encodeToString(fileContent);

        fileContent = imageToBytes(watermarkFile);
        String watermarkImg = Base64.getEncoder().encodeToString(fileContent);

        byte[] base64Bytes = DatatypeConverter.parseBase64Binary(originalImg);
        byte[] imgBytes = Base64.getDecoder().decode(originalImg);

        byte[] watermarkBase64Bytes = DatatypeConverter.parseBase64Binary(watermarkImg);
        byte[] watermarkImgBytes = Base64.getDecoder().decode(watermarkImg);


        Tika tika = new Tika();
        String fileType = tika.detect(base64Bytes).split("/")[1];
        System.out.println("fileType: " + fileType);

        String watermarkFileType = tika.detect(watermarkBase64Bytes).split("/")[1];
        System.out.println("fileType: " + watermarkFileType);


        InputStream is = new ByteArrayInputStream(imgBytes);
        BufferedImage bi = ImageIO.read(is);

        InputStream watrermarkIs = new ByteArrayInputStream(watermarkImgBytes);
        BufferedImage watermarkBi = ImageIO.read(watrermarkIs);

        Font f = new Font("TimesRoman", Font.BOLD, 10);
        Graphics2D g;
        Image watermarkImage = watermarkBi;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        boolean shift = false;

        switch (fileType) {
            case "png":
               break;
            case "jpg":
            case "jpeg":
                g = bi.createGraphics();
                g.setColor(new Color(.128f, .128f, .128f, .3f));
                g.setFont(f);

//                for (int i = 0; i < bi.getHeight(); i += 30){
//
//                    if (shift){
//                        for(int j = 100; j < bi.getWidth(); j += 200){
//                            g.drawString(text, j, i);
//                        }
//                        shift = false;
//                    } else {
//                        for(int j = 0; j < bi.getWidth(); j += 200){
//                            g.drawString(text, j, i);
//                        }
//                        shift = true;
//                    }
//                }
                // Setting watermark opacity
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

//                for (int i = 0; i < bi.getHeight(); i += (30 + 50)){
//
//                    if (shift){
//                        for(int j = 100; j < bi.getWidth(); j += (200 + 50)){
//                            g.drawImage(watermarkImage, i, j, 50, 50, null);
//                        }
//                        shift = false;
//                    } else {
//                        for(int j = 0; j < bi.getWidth(); j += (200 + 50)){
//                            g.drawImage(watermarkImage, i, j, 50, 50, null);
//                        }
//                        shift = true;
//                    }
//                }

                g.drawImage(watermarkImage, 20, (bi.getHeight() / 2) + (watermarkBi.getHeight() / 2), bi.getWidth() - 100, 200, null);


                ImageIO.write(bi, "jpeg", os);
                saveFile(bi, originalFile);
            default:
                break;
        }
    }

    private byte[] imageToBytes(File file){
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            System.out.println("File not found");;
        }
        return null;
    }

    private void saveFile(BufferedImage bi, File file){
        FileSystemView view = FileSystemView.getFileSystemView();
        File deskFile = view.getHomeDirectory();
        String desktopPath = deskFile.getPath();

        File resultFile = new File(desktopPath + "/result-" + file.getName());
        try {
            ImageIO.write(bi, "jpeg", resultFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
