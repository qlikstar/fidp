package com.decipherx.fingerprint.idp.services;

import com.decipherx.fingerprint.idp.entities.Client;
import com.decipherx.fingerprint.idp.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClientService {

    @Resource
    private ClientRepository clientRepository;

    public Client getClientByClientId(String clientId){
        return clientRepository.findByClientId(clientId);
    }

    public Client getClientByFqdn(String fqdn) {return clientRepository.findByFqdn(fqdn);}

    public Client createNewClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }


}
