package com.decipherx.fingerprint.idp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexPageController {

    @RequestMapping(method = RequestMethod.GET, path = "/index")
    public ModelAndView returnIndexPage(){
        return new ModelAndView("index");
    }

}
