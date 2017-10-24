package com.decipherx.fingerprint.idp.controllers;

import com.decipherx.fingerprint.idp.Utils.ClientUserXref;
import com.decipherx.fingerprint.idp.Utils.ClientUserXrefHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/xref")
public class ClientUserXrefController {


    @RequestMapping(value="/", method = RequestMethod.GET)
    public Map<String, ClientUserXref> fetchAllXrefValues() {
        return ClientUserXrefHashMap.clientUserXrefMap;
    }

    @RequestMapping(value="/delete", method = RequestMethod.GET)
    public String deleteAllXrefValues() {
        ClientUserXrefHashMap.clientUserXrefMap = new HashMap();
        return "XREF Values cleared";
    }



}
