package org.tammy.weatherproject.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("")

public class HomeController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model){
        String url = "https://www.google.com/maps/embed/v1/directions?key=AIzaSyBGr4S6D6vPzukv48fFiicLaKcEurYkCIg&origin=Oslo+Norway&destination=Telemar+Norway&avoid=tolls|highways";
        model.addAttribute("url", url);
        return "index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String index(Model model, @RequestParam String search){
        return "index";
    }

}
