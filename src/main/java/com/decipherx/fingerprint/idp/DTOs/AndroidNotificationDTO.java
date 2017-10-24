package com.decipherx.fingerprint.idp.DTOs;

public class AndroidNotificationDTO {

    private String to;

    private notification notification;

    private data data;


    public AndroidNotificationDTO(String tokenToDevice, notification notification, data data) {
        this.to = tokenToDevice;
        this.notification = notification;
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public AndroidNotificationDTO.notification getNotification() {
        return notification;
    }

    public void setNotification(AndroidNotificationDTO.notification notification) {
        this.notification = notification;
    }

    public AndroidNotificationDTO.data getData() {
        return data;
    }

    public void setData(AndroidNotificationDTO.data data) {
        this.data = data;
    }

    public static class notification{

        private String title;

        private String body;

        private final String click_action = "fidp.decipherx.citrix.com.fingerprintidp_TARGET_NOTIFICATION";

        private String sound = "default";

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public String getClick_action() {
            return click_action;
        }

        public notification() {
        }

        public notification(String title, String body) {
            this.title = title;
            this.body = body;
        }

        public notification(String title, String body, String sound) {
            this.title = title;
            this.body = body;
            this.sound = sound;
        }

        @Override
        public String toString() {
            return "notification{" +
                    "title='" + title + '\'' +
                    ", body='" + body + '\'' +
                    ", click_action='" + click_action + '\'' +
                    ", sound='" + sound + '\'' +
                    '}';
        }
    }

    public static class data{

        private String fqdn;

        private String timestamp;

        private String browser;

        private String clientIp;

        public String getFqdn() {
            return fqdn;
        }

        public void setFqdn(String fqdn) {
            this.fqdn = fqdn;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getBrowser() {
            return browser;
        }

        public void setBrowser(String browser) {
            this.browser = browser;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }

        public data() {
        }

        public data(String fqdn, String timestamp, String browser, String clientIp) {
            this.fqdn = fqdn;
            this.timestamp = timestamp;
            this.browser = browser;
            this.clientIp = clientIp;
        }
    }

}
