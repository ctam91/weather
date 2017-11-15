package org.tammy.weatherproject.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tammy.weatherproject.Models.Direction;

import javax.validation.Valid;


@Controller
@RequestMapping("")

public class HomeController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("direction", new Direction());
        return "index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String index(@ModelAttribute @Valid Direction direction, Errors errors, Model model){
        model.addAttribute("direction", direction);
        return "index";
    }

}
