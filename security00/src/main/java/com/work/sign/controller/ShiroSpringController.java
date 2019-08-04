package com.work.sign.controller;

import com.work.sign.model.UserCredentials;
import com.work.sign.model.SignCredentials;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.shiro.crypto.hash.SimpleHash;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.work.sign.service.SignService;

@Controller
public class ShiroSpringController {

    private static Logger log = LoggerFactory.getLogger(ShiroSpringController.class);

    @Autowired
    HttpServletRequest req;

    @Autowired
	  SignService signService;

    @GetMapping("/")
    public String index() {
       return "index";
    }

    @RequestMapping( value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(UserCredentials cred, RedirectAttributes attr) {

      if(req.getMethod().equals(RequestMethod.GET.toString())) {
        return "login";
      }
      else {

        Subject currentUser = SecurityUtils.getSubject();

        if(!currentUser.isAuthenticated()) {
          UsernamePasswordToken token = new UsernamePasswordToken(
            cred.getUsername(), cred.getPassword(), cred.isRememberMe());
          try {
            currentUser.login(token);
          } catch (AuthenticationException ae) {
              log.info("登录失败，请检查用户名" + cred.getUsername() + "及口令...");
              attr.addFlashAttribute("error", "Invalid Credentials");
              return "redirect:/login";
          }
        }

        return "redirect:/secure";
      }
    }

    @RequestMapping( value = "/signin", method = {RequestMethod.GET, RequestMethod.POST})
    public String signin(SignCredentials cred, RedirectAttributes attr) {

      if(req.getMethod().equals(RequestMethod.GET.toString())) {
        return "signin";
      }

      else {
        signService.saveUser(cred);

        return "redirect:/";
      }
        
    }


    @GetMapping("/secure")
    public String secure(ModelMap modelMap) {

        Subject currentUser = SecurityUtils.getSubject();
        String role = "", permission = "";

        if(currentUser.hasRole("schwartz")) {
            role = role  + "You are an schwartz.";
        }
        
        if(currentUser.hasRole("goodguy")) {
            role = role + "\nYou are an goodguy.";
        }

        if(currentUser.isPermitted("lightsaber:wield")) {
            permission = permission + "You may use a lightsaber ring. Use it wisely.";
        } else {
            permission = permission + "You are not permitted to compose an article!, ";
        }

        if(currentUser.isPermitted("winnebago:drive:eagle5")) {
            permission = permission + "You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
            "Here are the keys - have fun!";
        } else {
            permission = permission + "\nYou can not save articles, ";
        }

        modelMap.addAttribute("username", currentUser.getPrincipal());
        modelMap.addAttribute("permission", permission);
        modelMap.addAttribute("role", role);

        return "secure";
    }


    @PostMapping("/logout")
    public String logout() {
       Subject subject = SecurityUtils.getSubject();
       subject.logout();
       return "redirect:/";
    }

}
