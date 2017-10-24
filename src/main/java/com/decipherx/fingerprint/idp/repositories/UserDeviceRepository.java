package com.decipherx.fingerprint.idp.repositories;

import com.decipherx.fingerprint.idp.entities.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, String>
{
    UserDevice findByUsername(String username);
}