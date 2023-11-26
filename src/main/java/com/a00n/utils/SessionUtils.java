/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.utils;

import com.a00n.entities.User;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ay0ub
 */
public class SessionUtils {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static String getUserName() {
        HttpSession session = getSession();
        if (session != null) {
            return ((User) session.getAttribute("user")).getUsername();
        } else {
            return null;
        }
    }

    public static String getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return ((User) session.getAttribute("user")).getId() + "";
        } else {
            return null;
        }
    }
}
