package com.decipherx.fingerprint.idp.services;

import com.decipherx.fingerprint.idp.entities.UserDevice;
import com.decipherx.fingerprint.idp.repositories.UserDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDeviceService{

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    public UserDevice getUserDeviceForUsername(String username){
        return userDeviceRepository.findByUsername(username);
    }

    public UserDevice createOrUpdateUserDevice(UserDevice userDevice){
        return userDeviceRepository.save(userDevice);
    }

    public List<UserDevice> getAllUserDevices (){
        return userDeviceRepository.findAll();
    }

}
