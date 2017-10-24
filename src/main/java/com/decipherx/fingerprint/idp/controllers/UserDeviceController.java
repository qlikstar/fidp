package com.decipherx.fingerprint.idp.controllers;

import com.decipherx.fingerprint.idp.entities.UserDevice;
import com.decipherx.fingerprint.idp.services.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserDeviceController {

    @Autowired
    UserDeviceService userDeviceService;

    @RequestMapping(path = "/{username}" , method = RequestMethod.GET)
    public UserDevice getUserDetails(@PathVariable("username") String username){
        return userDeviceService.getUserDeviceForUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserDevice createNewUserDevice(@RequestBody UserDevice userDevice){
        return userDeviceService.createOrUpdateUserDevice(userDevice);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDevice> createNewUserDevice(){
        return userDeviceService.getAllUserDevices();
    }

}
