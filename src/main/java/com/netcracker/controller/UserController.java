package com.netcracker.controller;


import com.netcracker.exeption.MailAdreesAlweysExist;
import com.netcracker.model.User;
import com.netcracker.service.MailSender;
import com.netcracker.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
public class UserController {

    private final Userservice userservice;

    @Autowired
    public UserController(Userservice userservice) {
        this.userservice = userservice;
    }
    @Autowired
    MailSender mailSender;


    @GetMapping("/user-add")
    public String createUserForm(@ModelAttribute("user") User user) {
        return "user-add";
    }
    @PostMapping("/user-add")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            System.out.println("has erors");
            System.out.println(bindingResult.getAllErrors());
            return "user-add";
        }try {
            userservice.saveUser(user);
        }
        catch (MailAdreesAlweysExist e){
            System.out.println(e.getLocalizedMessage());
            bindingResult.rejectValue("mailadress", "error.user", "Mailadress is already exists");
            return "user-add";
        }
        return "redirect:/Succesful";
    }


    @GetMapping("/Succesful")
    public String sucsees(){
        return "Succesful";
    }
    @GetMapping("/notvalid")
    public String notvalid(){
        return "NotValid";
    }

    @GetMapping("/find")
    public String find(@RequestParam("fname") Optional<String> fname, @RequestParam("lname") Optional<String> lname, HttpServletRequest request, Model model){
        List<User> users;
        if(fname.isPresent()&&lname.isPresent()){
            users= userservice.findbynameandlastname(fname.get(),lname.get());
            HttpSession session = request.getSession(true);
            String useragent= request.getHeader("User-Agent");
            if(users.size()!=0){
                model.addAttribute("useragent",useragent);
                model.addAttribute("timeuser",userservice.data());
                model.addAttribute("users",users);
                return "find";
            }else return "redirect:/notvalid";
        }
        return "find";
    }


    @GetMapping("/fileloader")
    public String fileloading(){
        return "FileLoader";
    }
    @PostMapping("/fileloader")
    public String handleloadfile(@RequestParam("file")MultipartFile file,Model model) {
        try {
            if (!file.isEmpty()) {
                userservice.uploadfile(file);
                model.addAttribute("succesful", true);
                return "fileloader";
            } else {
                System.out.println("нет файла  ");
                model.addAttribute("uups", true);
                return "fileloader";
            }
        }catch (IOException e){
            System.out.println(e.getLocalizedMessage());
            System.out.println("проблема с csv");
        }
        return "fileloader";
    }


    @GetMapping("/findandsend")
    public String sendmail(@RequestParam("id") Optional<String> id, @RequestParam("messeg") Optional<String> messeg){
        int finduser= Integer.parseInt(id.get());
        boolean condition=userservice.sendmessegforuser(finduser,messeg.get());
        if(condition) {
            return "redirect:/Succesful";
        }else return "redirect:/notvalid";
    }
}
