package com.artisoft.watermarkdesktop.service;

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
    public void createWatermark(File originalFile, File watermarkFile) throws IOException {
        String text = "Hello";
        byte[] fileContent = this.imageToBytes(originalFile);
        String originalImg = Base64.getEncoder().encodeToString(fileContent);
        fileContent = this.imageToBytes(watermarkFile);
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
        ByteArrayInputStream is = new ByteArrayInputStream(imgBytes);
        BufferedImage bi = ImageIO.read(is);
        ByteArrayInputStream watrermarkIs = new ByteArrayInputStream(watermarkImgBytes);
        BufferedImage watermarkBi = ImageIO.read(watrermarkIs);
        Font f = new Font("TimesRoman", 1, 10);
        BufferedImage watermarkImage = watermarkBi;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        boolean shift = false;
        switch (fileType) {
            case "png": {
                Graphics2D g = bi.createGraphics();
                g.setColor(new Color(0.128f, 0.128f, 0.128f, 0.3f));
                g.setFont(f);
                g.setComposite(AlphaComposite.getInstance(3, 0.2f));
                g.drawImage(watermarkImage, bi.getWidth() / 2 - 200, bi.getHeight() / 2 - 20, 400, 200, null);
                ImageIO.write((RenderedImage) bi, "jpeg", os);
                this.saveFile(bi, originalFile);
                break;
            }
            case "jpg":
            case "jpeg": {
                System.out.println("Yo, I am here");
                Graphics2D g = bi.createGraphics();
                g.setColor(new Color(0.128f, 0.128f, 0.128f, 0.3f));
                g.setFont(f);
                g.setComposite(AlphaComposite.getInstance(3, 0.2f));
                g.drawImage(watermarkImage, bi.getWidth() / 2 - 200, bi.getHeight() / 2 - 20, 400, 200, null);
                ImageIO.write((RenderedImage) bi, "jpeg", os);
                this.saveFile(bi, originalFile);
            }
        }
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
            ImageIO.write((RenderedImage) bi, "jpeg", resultFile);
            System.out.println("result path: " + String.valueOf(resultFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}