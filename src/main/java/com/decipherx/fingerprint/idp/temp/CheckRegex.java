package com.decipherx.fingerprint.idp.temp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckRegex {

    public static void main(String[] args) {
        String str = "On Fri Aug 25 18:37:21 UTC 2017: You have been signed in to <clientapp.citrix.com>, using Java/1.8.0_25 from IP 162.221.154.11";
        Pattern MY_PATTERN = Pattern.compile("<(\\w+\\.\\w+\\.\\w+)>");

        Matcher m = MY_PATTERN.matcher(str);
        while (m.find()) {
            String s = m.group(1);
            // s now contains "BAR"
            System.out.println(s);
        }

    }
}
