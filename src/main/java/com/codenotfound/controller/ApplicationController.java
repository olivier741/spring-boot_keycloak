/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codenotfound.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author olivier.tatsinkou
 */
@Validated
@Controller
public class ApplicationController {

    private final HttpServletRequest request;

    @Autowired
    public ApplicationController(HttpServletRequest request) {
        this.request = request;
    }

    @GetMapping("/home")
    public String greeting(@NotNull Model model, @NotNull Authentication auth) {
        String name = ((SimpleKeycloakAccount) auth.getDetails())
                .getKeycloakSecurityContext()
                .getToken().getName();

        model.addAttribute("name", name);
        return "home";

    }

//    @RequestMapping("/error")
//    public String handleError(HttpServletRequest request) throws Throwable {
//        if (request.getAttribute("javax.servlet.error.exception") != null) {
//            throw (Throwable) request.getAttribute("javax.servlet.error.exception");
//        }
//        return "index";
//    }

    @GetMapping(value = "/")
    public String getHome() {
        return "index";
    }

    private void configCommonAttributes(Model model) {
        model.addAttribute("firstname", getKeycloakSecurityContext().getIdToken().getGivenName());
        model.addAttribute("lastname", getKeycloakSecurityContext().getIdToken().getFamilyName());
        model.addAttribute("email", getKeycloakSecurityContext().getIdToken().getEmail());
    }

    private KeycloakSecurityContext getKeycloakSecurityContext() {
        return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    }

}
