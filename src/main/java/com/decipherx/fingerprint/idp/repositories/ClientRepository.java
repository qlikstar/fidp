package com.decipherx.fingerprint.idp.repositories;

import com.decipherx.fingerprint.idp.entities.Client;
import com.decipherx.fingerprint.idp.entities.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>
{
    Client findByClientId(String clientId);

    Client findByFqdn(String fqdn);
}