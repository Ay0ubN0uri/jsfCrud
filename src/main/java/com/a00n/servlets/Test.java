/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.servlets;

import com.a00n.utils.HibernateUtil;

/**
 *
 * @author ay0ub
 */
public class Test {

    public static void main(String[] args) {
        HibernateUtil.getSessionFactory().inSession(session -> {
            System.out.println(session);
        });
    }
}
