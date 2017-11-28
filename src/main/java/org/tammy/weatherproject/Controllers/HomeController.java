package org.tammy.weatherproject.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("")

public class HomeController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model){
        return "weather";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String index(Errors errors, Model model){
        return "weather";
    }

}
