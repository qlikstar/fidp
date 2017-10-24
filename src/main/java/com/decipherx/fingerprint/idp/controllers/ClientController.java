package com.decipherx.fingerprint.idp.controllers;

import com.decipherx.fingerprint.idp.DTOs.ClientRequestDTO;
import com.decipherx.fingerprint.idp.DTOs.QRCodeRequestDTO;
import com.decipherx.fingerprint.idp.DTOs.QrResponseDTO;
import com.decipherx.fingerprint.idp.entities.Client;
import com.decipherx.fingerprint.idp.entities.UserDevice;
import com.decipherx.fingerprint.idp.repositories.ClientRepository;
import com.decipherx.fingerprint.idp.services.ClientService;
import com.decipherx.fingerprint.idp.services.KeyGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

//    @Autowired
//    private KeyGeneratorService keyGeneratorService;

    @Resource
    private ClientService clientService;

    @ResponseBody
    @RequestMapping(value="", method = RequestMethod.GET)
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }


    @ResponseBody
    @RequestMapping(value="", method = RequestMethod.POST)
    public Client createNewClient(@RequestBody ClientRequestDTO clientRequestDTO){
        Client client = new Client();
        client.setClientId(UUID.randomUUID().toString());
        client.setAppName(clientRequestDTO.getAppname());
        client.setFqdn(clientRequestDTO.getFqdn());
        KeyGeneratorService keyGeneratorService = new KeyGeneratorService();
        client.setPublicKey(keyGeneratorService.getPublicKeyPem());
        client.setPrivateKey(keyGeneratorService.getPrivateKeyPem());
        clientService.createNewClient(client);
        return client;
    }

//    @RequestMapping(path = "/publickey/{clientId}" , method = RequestMethod.GET)
//    public String getUserDetails(@PathVariable("clientId") String clientId){
//        return clientService.getClientByClientId(clientId).getPublicKey();
//    }


}
