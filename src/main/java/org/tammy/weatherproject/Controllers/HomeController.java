package org.tammy.weatherproject.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tammy.weatherproject.Models.User;
import org.tammy.weatherproject.Models.UserData;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


//TODO: add placeholder text to directions panel or move dest/origin menu
//TODO: update about section
//TODO: store user preferences
//TODO: add custom weather icons

@Controller
@RequestMapping("")

public class HomeController {

    @Autowired
    private UserData userData;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String about(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String addUser(Model model) {
        model.addAttribute("title", "Sign up!");
        model.addAttribute("user", new User());

        return "signup";
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String addUser(@ModelAttribute @Valid User user, Errors errors, Model model, RedirectAttributes ra, HttpSession session) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Sign up!");
            return "signup";
        }

        userData.generateUserList();

        if (userData.checkUserExists(user)) {
            model.addAttribute("title", "Sign up!");
            model.addAttribute("userError", "User name exists. Please select another.");
            return "signup";
        }

        userData.saveNewUser(user);
        session.setAttribute("loggedInUser", user);
        ra.addFlashAttribute("username", "Welcome " + user.getName());
        return "redirect:";
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String logIn(Model model){

        model.addAttribute("user", new User());
        model.addAttribute("title", "Welcome to Weather Report!" );
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String logIn(@ModelAttribute @Valid User user, Errors errors, Model model, RedirectAttributes ra, HttpSession session) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Welcome to Weather Report!" );
            model.addAttribute("user", new User());
            return "login";
        }

        userData.generateUserList();

        if (!userData.checkUserExists(user)) {

            model.addAttribute("title", "Welcome to Weather Report!" );
            model.addAttribute("userError", "User name does not exist. Please sign up!");
            model.addAttribute("user", new User());
            return "login";
        }

        else if (userData.checkUserExists(user) && !userData.verifyUserPassword(user)) {

            model.addAttribute("title", "Welcome to Weather Report!" );
            model.addAttribute("passwordError", "Invalid password!");
            model.addAttribute("user", new User());
            return "login";

        }

        else {

            session.setAttribute("loggedInUser", user);
            ra.addFlashAttribute("username", "Welcome " + user.getName());
            return "redirect:";
        }
    }


}
