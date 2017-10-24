package com.decipherx.fingerprint.idp.controllers;

import com.decipherx.fingerprint.idp.DTOs.AndroidNotificationDTO;
import com.decipherx.fingerprint.idp.DTOs.QRCodeRequestDTO;
import com.decipherx.fingerprint.idp.DTOs.QrResponseDTO;
import com.decipherx.fingerprint.idp.DTOs.UserGrantDTO;
import com.decipherx.fingerprint.idp.Utils.ClientUserXref;
import com.decipherx.fingerprint.idp.Utils.ClientUserXrefHashMap;
import com.decipherx.fingerprint.idp.entities.UserDevice;
import com.decipherx.fingerprint.idp.services.*;
import com.nimbusds.jose.JOSEException;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;


@RestController
@RequestMapping()
public class QRCodeController {

    private static String USERNAME_FROM_CLIENT;

    @Autowired
    private QrResponseDTO qrResponseDTO;

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private QRCodeGenService qrCodeGenService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserDeviceService userDeviceService;

    @Autowired
    private NotificationService notificationService;

    @ResponseBody
    @RequestMapping(value="/getusergrant", method = RequestMethod.POST)
    public UserGrantDTO getQRCode(@RequestBody QRCodeRequestDTO qrCodeRequestDTO) throws JOSEException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        USERNAME_FROM_CLIENT = qrCodeRequestDTO.getUsername();
        String clientId = qrCodeRequestDTO.getClientId();
        String clientIpAddress = getClientIp();
        //String  browserDetails  =   request.getHeader("User-Agent");
        String browserDetails = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36";

        UserGrantDTO userGrantDTO = new UserGrantDTO();

        String key = clientId + ":" + USERNAME_FROM_CLIENT;
        ClientUserXref clientUserXref = ClientUserXrefHashMap.clientUserXrefMap.get(key);


        if (null==clientUserXref) {
            String jwtToken = jwtTokenService.createJWTToken(USERNAME_FROM_CLIENT, clientId);
            System.out.println("JWT Token : " + jwtToken);
            //Get QRCode
            qrCodeGenService.setData(jwtToken);
            qrResponseDTO.setQrCode(qrCodeGenService.getQrCodeToPNGContent());
            userGrantDTO.setQrResponseDTO(qrResponseDTO);
            userGrantDTO.setUserGrant(false);
            return userGrantDTO;

        }else {

            Long currentTime = (System.currentTimeMillis() / 1000 );
            UserDevice userDevice = userDeviceService.getUserDeviceForUsername(USERNAME_FROM_CLIENT);

            String fqdn = clientUserXref.getClient().getFqdn();

            System.out.println("username : " + USERNAME_FROM_CLIENT );
            System.out.println("Client ID : " + clientId );
            System.out.println("Client IP Address : " + clientIpAddress );
            System.out.println("Browser : "+ browserDetails);
            System.out.println("fqdn : " + fqdn);

            if(clientUserXref.getValidUntil() < currentTime ) { //validity expired cases

                if (!clientUserXref.getNotificationSent()) {
                    //Send a push message to the device requesting for access
                    System.out.println(userDevice.toString());
                    String tokenToDevice = userDevice.getFcmToken();
                    System.out.println("TokenToDevice : " + tokenToDevice);
                    String notificationTitle = "Fingerprint IDP: Action Required!";
                    String notificationBody = String.format("%s is requesting your authorization",fqdn);

                    AndroidNotificationDTO.notification notification = new AndroidNotificationDTO.notification(notificationTitle, notificationBody);
                    System.out.println("Notification Object : " + notification.toString());

                    String timestamp = String.valueOf(new Date());
                    AndroidNotificationDTO.data notificationData = new AndroidNotificationDTO.data(fqdn, timestamp, browserDetails, clientIpAddress);

                    AndroidNotificationDTO androidNotificationDTO = new AndroidNotificationDTO(tokenToDevice, notification, notificationData);
                    notificationService.notifyAndroidDevice(androidNotificationDTO);
                    clientUserXref.setNotificationSent(true);

                    System.out.println("Notification sent to the user " + USERNAME_FROM_CLIENT);
                }

            }

            userGrantDTO.setUserDevice(userDevice);
            userGrantDTO.setUserGrant(true);
            userGrantDTO.setValidUntil(clientUserXref.getValidUntil());
            return userGrantDTO;
        }
    }

    private String getClientIp() {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
}