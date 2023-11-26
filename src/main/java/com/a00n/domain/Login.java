/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.domain;

import com.a00n.entities.User;
import com.a00n.service.UserService;
import com.a00n.utils.SessionUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author ay0ub
 */
@Named
@SessionScoped
public class Login implements Serializable {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    private User user;

    @Inject
    private UserService userService;

    @PostConstruct
    private void init() {
        this.user = new User();
    }

    public void login() throws IOException {
        System.out.println(user);
        User u = this.userService.userExist(user.getUsername(), user.getPassword());
        if (u != null) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("user", u);
            externalContext.redirect(externalContext.getRequestContextPath());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username and Passowrd", "Please enter correct username and Password"));
        }
    }

    public void logout() throws IOException {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/login.xhtml");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
