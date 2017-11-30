package org.tammy.weatherproject.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


//TODO: fix navbar transparency
//TODO: add placeholder text to directions panel or move dest/origin menu
//TODO: add about section
//TODO: add db for user
//TODO: add login/signup pages
//TODO: store user preferences
//TODO: add custom weather icons

@Controller
@RequestMapping("")

public class HomeController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model){
        return "index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String index(Errors errors, Model model){
        return "index";
    }

}
