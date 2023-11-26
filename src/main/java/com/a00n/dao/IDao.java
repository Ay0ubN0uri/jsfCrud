/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.a00n.dao;

import java.util.List;

/**
 *
 * @author ay0ub
 */
public interface IDao<T> {

    boolean create(T o);

    boolean update(T o);

    boolean delete(T o);

    List<T> getAll();

    T getById(Long id);

}
