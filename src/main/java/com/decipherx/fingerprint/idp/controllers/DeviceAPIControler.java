package com.decipherx.fingerprint.idp.controllers;

import com.decipherx.fingerprint.idp.DTOs.ClientUserRequestDTO;
import com.decipherx.fingerprint.idp.Utils.ClientUserXref;
import com.decipherx.fingerprint.idp.Utils.ClientUserXrefHashMap;
import com.decipherx.fingerprint.idp.entities.Client;
import com.decipherx.fingerprint.idp.entities.UserDevice;
import com.decipherx.fingerprint.idp.services.ClientService;
import com.decipherx.fingerprint.idp.services.JWTTokenService;
import com.decipherx.fingerprint.idp.services.UserDeviceService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.HashMap;

@RestController
@RequestMapping("/device")
public class DeviceAPIControler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDeviceService userDeviceService;

    @Autowired
    ClientService clientService;

    @Autowired
    JWTTokenService jwtTokenService;

    @ResponseBody
    @RequestMapping(path= "/register", method = RequestMethod.POST)
    public HashMap<String, Boolean> createNewUserDevice(@RequestBody ClientUserRequestDTO clientUserRequestDTO){

        //TODO: Verify the authenticity of requestor before proceeding ahead
        Boolean granted = registerOrUnregisterUserForService(clientUserRequestDTO.getClientId(),
                clientUserRequestDTO.getUsername(), clientUserRequestDTO.getAccessGranted(),false);
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("accessGranted", granted );
        return response;

    }

    @RequestMapping(path= "/grant", method = RequestMethod.POST)
    public Boolean grantExistingUserAccess(@RequestBody HashMap grantUserData){

        String username = grantUserData.get("username").toString();
        String fqdn     = grantUserData.get("fqdn").toString();
        Boolean grantAccess = Boolean.valueOf(grantUserData.get("allowAccess").toString());

        Client client = clientService.getClientByFqdn(fqdn);


        return registerOrUnregisterUserForService(client.getClientId(), username, grantAccess, false);

    }

    @ResponseBody
    @RequestMapping(path= "/verify", method = RequestMethod.POST)
    public HashMap verifyToken(@RequestBody HashMap userTokenData) throws ParseException, InvalidKeySpecException, JOSEException, org.json.simple.parser.ParseException {

        HashMap response = new HashMap<>();
        Boolean verified = false;

        String username = userTokenData.get("username").toString();
        String jwtToken = userTokenData.get("jwtToken").toString();
        String fcmToken = userTokenData.get("fcmToken").toString();

        String[] tokenParts =   jwtToken.split("\\.");
        String decodedPayload = new String(Base64.decodeBase64(tokenParts[1].getBytes()));

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(decodedPayload);

        String clientId = (String) json.get("clientId");
        String usernameFromJWT = (String) json.get("aud");

        log.info("Client ID " + clientId);
        Client clientEntity = clientService.getClientByClientId(clientId);
        String publicKeyContent = clientEntity.getPublicKey();
        if (username.equalsIgnoreCase(usernameFromJWT)) {

            //Save FCM Token to the UuserDevice Entity
            UserDevice userDevice = userDeviceService.getUserDeviceForUsername(username);
            userDevice.setFcmToken(fcmToken);
            userDeviceService.createOrUpdateUserDevice(userDevice);
            verified = jwtTokenService.checkIfTokenIsValid(jwtToken, publicKeyContent);
        }
        response.put("verified", verified);
        response.put("fqdn" , clientEntity.getFqdn());

        if(verified){
            registerOrUnregisterUserForService(clientId, username, true, false);
        }

        return response;
    }

    private Boolean registerOrUnregisterUserForService (String clientId, String username, Boolean accessGranted, Boolean notificationSent){

        Client clientEntity         = clientService.getClientByClientId(clientId);
        UserDevice userDeviceEntity = userDeviceService.getUserDeviceForUsername(username);
        String key = clientId +":"+username;

        ClientUserXref clientUserXref = new ClientUserXref(clientEntity, userDeviceEntity, accessGranted, notificationSent);
        if (accessGranted) {
            ClientUserXrefHashMap.clientUserXrefMap.put(key, clientUserXref);
            return true;
        }else{
            ClientUserXrefHashMap.clientUserXrefMap.remove(key);
            return false;
        }
    }

}
