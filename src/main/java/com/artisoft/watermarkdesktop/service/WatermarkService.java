package com.artisoft.watermarkdesktop.service;

import com.artisoft.watermarkdesktop.controller.WatermarkController;
import jakarta.xml.bind.DatatypeConverter;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import org.apache.tika.Tika;

public class WatermarkService {


    public void createPngWatermark(File originalFile, File watermarkFile) throws IOException {

        // Convert original image to byte string
        byte[] fileContent = this.imageToBytes(originalFile);
        byte[] imgBytes = fileContent;
        String originalImg = Base64.getEncoder().encodeToString(fileContent);

        // Convert watermark image to byte string
        fileContent = this.imageToBytes(watermarkFile);
        byte[] watermarkImgBytes = fileContent;
        String watermarkImg = Base64.getEncoder().encodeToString(fileContent);

        //
        byte[] originalBase64Bytes = DatatypeConverter.parseBase64Binary(originalImg);
        byte[] watermarkBase64Bytes = DatatypeConverter.parseBase64Binary(watermarkImg);

        // get the type of the original document
        Tika tika = new Tika();

        String fileType = tika.detect(originalBase64Bytes).split("/")[1];
        System.out.println("fileType: " + fileType);

        String watermarkFileType = tika.detect(watermarkBase64Bytes).split("/")[1];
        System.out.println("fileType: " + watermarkFileType);

        ByteArrayInputStream is = new ByteArrayInputStream(imgBytes);
        BufferedImage bi = ImageIO.read(is);

        ByteArrayInputStream watrermarkIs = new ByteArrayInputStream(watermarkImgBytes);
        BufferedImage watermarkBi = ImageIO.read(watrermarkIs);

        Font f = new Font("TimesRoman", 1, 10);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        boolean shift = false;
        Graphics2D g = bi.createGraphics();
        g.setComposite(AlphaComposite.getInstance(3, 0.2f));
        switch (fileType) {
            case "png":
//                g.setColor(new Color(0.128f, 0.128f, 0.128f, 0.3f));
//                g.setFont(f);
                g.drawImage(watermarkBi, bi.getWidth() / 2 - 200, bi.getHeight() / 2 - 20, 400, 200, null);
                ImageIO.write(bi, "png", os);
                this.saveFile(bi, originalFile);
                break;
            case "jpg":
            case "jpeg":
//                g.setColor(new Color(0.128f, 0.128f, 0.128f, 0.3f));
//                g.setFont(f);
                g.drawImage(watermarkBi, bi.getWidth() / 2 - 200, bi.getHeight() / 2 - 20, 400, 200, null);
                ImageIO.write(bi, "jpeg", os);
                this.saveFile(bi, originalFile);
                break;
        }

        return;
    }

    private byte[] imageToBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            System.out.println("File not found");
            return null;
        }
    }

    private void saveFile(BufferedImage bi, File file) {
        FileSystemView view = FileSystemView.getFileSystemView();
        File deskFile = view.getHomeDirectory();
        String desktopPath = deskFile.getPath();
        File resultFile = new File(desktopPath + "/result-" + file.getName());
        try {
            // TODO: add more formats (jpg, etc)
            ImageIO.write(bi, "png", resultFile);
            System.out.println("result path: " + String.valueOf(resultFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}