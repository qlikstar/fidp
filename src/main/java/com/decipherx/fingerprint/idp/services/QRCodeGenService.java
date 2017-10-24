package com.decipherx.fingerprint.idp.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

@Service
public class QRCodeGenService {

    private String data;

    private final int size = 400;

    private final String fileType = "png";

    private final File qrFile = File.createTempFile("qrcode", ".png");;

    private String qrCodeToPNGContent;

    public void setData(String data) throws IOException {
        this.data = data;
        generateQRCodeFromData();
        encodeFileToBase64Binary();
    }

    public String getQrCodeToPNGContent() {
        return qrCodeToPNGContent;
    }

    public QRCodeGenService() throws IOException {
    }

    private void generateQRCodeFromData() throws IOException {

        try {

            Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
            hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            //System.out.println(this.data);
            BitMatrix byteMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size, size, hintMap);
            int width = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, width);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, qrFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void encodeFileToBase64Binary() throws IOException {
        //String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(qrFile);
            byte[] bytes = new byte[(int) qrFile.length()];
            fileInputStreamReader.read(bytes);
            qrCodeToPNGContent = new String(Base64.encodeBase64(bytes), "UTF-8");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //return encodedfile;
    }

    public static void main(String[] args) throws IOException {
        QRCodeGenService qrCodeGenService = new QRCodeGenService();
        qrCodeGenService.setData("eyJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJzZHNjZCIsInN1YiI6IjU5MTI3MzM2OTUyMjU2OTk3OTEzNzMzMDkyMjcwMzg3IiwiaXNzIjoiaHR0cHM6XC9cL2ZpZHAuY2l0cml4LmNvbSIsImV4cCI6MTUwMzAyNjkxM30.MHWaeUWvPueXzQgxgJrzLnjoPuVJczENEC--NwrFAk_4cwlUjwvmMraFRkIR5QA8FWX8-1PVE9Bl2S6LGtc0iQ3UFQx5_L4dbj5eq2R1qcq3AuGlT5wQ-HIrZ1qDyHBEZv74eTwEstMet7-MhM7VvmBt5zrGd6zeBwVBMNz6zugMUfviZQW52cyiHMUFTKXSe541_MScd_hhipyYAxK3-Adf0JY0Jt1aqAxRGKEGQ-Wy0mapCTUhQPEAptskFu3nSNkpRxyfgDx5A4MaoP5kHA3CSAXn24ZOvTNF8CiCP81jeSQzY9FEJVUwarf2lTmnpBlAE5vRW0wmk4cALHCE9g");

        //System.out.println(qrCodeGenService.getQrCodeToPNGContent());
    }

}
