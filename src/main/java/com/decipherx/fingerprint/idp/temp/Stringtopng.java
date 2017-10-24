package com.decipherx.fingerprint.idp.temp;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Stringtopng {

    public static void main(String[] args) throws IOException {

        File f = new File("src/main/resources/static/qrcodes/qrcode.png");
        String encodstring = encodeFileToBase64Binary(f);
        System.out.println( "File : " +encodstring);
    }

    private static String encodeFileToBase64Binary(File file) throws IOException {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }
}

