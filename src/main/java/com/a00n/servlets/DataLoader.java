/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.servlets;

import com.a00n.entities.Employe;
import com.a00n.entities.Service;
import com.a00n.entities.User;
import com.a00n.service.EmployeService;
import com.a00n.service.ServiceService;
import com.a00n.service.UserService;
import com.a00n.tests.InventoryStatus;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import java.text.SimpleDateFormat;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

/**
 *
 * @author ay0ub
 */
@Startup
@Singleton
public class DataLoader {

    @Inject
    private EmployeService employeService;

    @Inject
    private ServiceService serviceService;

    @Inject
    private UserService userService;

    @PostConstruct
    public void initializeData() {
        System.out.println("hello from here=============================================================");
        var f = InventoryStatus.INSTOCK;
        System.out.println(f.name());
        try {
            byte[] image = Files.readAllBytes(Paths.get("/home/ay0ub/Desktop/ayoub nouri.jpg"));
            // Create Services
            Service service1 = Service.builder().nom("Development Department").build();
            Service service2 = Service.builder().nom("Marketing Department").build();

            serviceService.create(service1);
            serviceService.create(service2);

            // Create Managers
            Employe manager1 = Employe.builder()
                    .nom("John")
                    .prenom("Doe")
                    .dateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse("1980-01-15"))
                    .service(service1)
                    .build();

            Employe manager2 = Employe.builder()
                    .nom("Alice")
                    .prenom("Smith")
                    .dateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse("1985-05-20"))
                    .service(service2)
                    .build();

            employeService.create(manager1);
            employeService.create(manager2);

            // Create Employees
            Employe employee1 = Employe.builder()
                    .nom("Bob")
                    .prenom("Johnson")
                    .dateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse("1990-03-25"))
                    .chef(manager1)
                    .service(service1)
                    .build();

            Employe employee2 = Employe.builder()
                    .nom("Eva")
                    .prenom("Williams")
                    .dateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse("1988-11-10"))
                    .chef(manager1)
                    .service(service2)
                    .build();

            Employe employee3 = Employe.builder()
                    .nom("Charlie")
                    .prenom("Brown")
                    .dateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-05"))
                    .chef(manager2)
                    .service(service1)
                    .build();

            Employe employee4 = Employe.builder()
                    .nom("David")
                    .prenom("Miller")
                    .dateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse("1991-04-18"))
                    .chef(manager2)
                    .service(service2)
                    .build();

            employeService.create(employee1);
            employeService.create(employee2);
            employeService.create(employee3);
            employeService.create(employee4);

            // create users
            User user1 = User.builder().username("ayoub").password("ayoubayoub").build();
            User user2 = User.builder().username("kali").password("ayoubayoub").build();

            userService.create(user1);
            userService.create(user2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
