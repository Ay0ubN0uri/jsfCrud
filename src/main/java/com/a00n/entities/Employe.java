package com.a00n.entities;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.*;

/**
 *
 * @author ay0ub
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    private Employe chef;//= new Employe();

    @OneToMany(mappedBy = "chef")
    @Builder.Default
    private List<Employe> employees = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
//    @Builder.Default
    private Service service;// = new Service();

    @PostPersist
    public void afterInsert() {
        System.out.println("))))))))))))))))))))))))))))))))))))))))))))))))))))))))");
        System.out.println(this);
        if (this.image == null) {
            this.image = "user.jpeg";
        }
    }
}
