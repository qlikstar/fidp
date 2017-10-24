package com.decipherx.fingerprint.idp.services;

import com.decipherx.fingerprint.idp.DTOs.AndroidNotificationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class NotificationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${firebase.server.key}")
    private String firebaseServerKey;

    @Value("${googleapi.notification.url}")
    private String googleapiNotificationUrl ;

    //private AndroidNotificationDTO androidNotificationDTO;

    private Boolean status;

    public NotificationService() {
    }

    public void notifyAndroidDevice(AndroidNotificationDTO androidNotificationDTO){

        HttpHeaders headers = new HttpHeaders();
        logger.info("Auth Header : "+ firebaseServerKey + "\n"
                +"FCM Token : To " + androidNotificationDTO.getTo() + "\n"
                + androidNotificationDTO.getNotification().getBody() + "\n"
                + androidNotificationDTO.getNotification().getTitle() + "\n");

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "key="+firebaseServerKey);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonInString = mapper.writeValueAsString(androidNotificationDTO);
            System.out.println("JSON String : " + jsonInString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<Object> httpEntity = new HttpEntity<>(androidNotificationDTO, headers);

        try {

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity result = restTemplate.exchange(googleapiNotificationUrl, HttpMethod.POST, httpEntity, AndroidNotificationDTO.class);

            System.out.println(result.getBody().toString());

        } catch (Exception e) {
            logger.error("URL : " + googleapiNotificationUrl + "  HTTP Method : " + HttpMethod.POST + "  Entities : " + androidNotificationDTO.toString() + " : " + e.getMessage());

        }


    }



}
