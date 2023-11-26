package com.a00n.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ay0ub
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @OneToMany(mappedBy = "service")
    @Builder.Default
    private List<Employe> employes = new ArrayList<>();

    public Employe getManager() {
        for (Employe emp : this.employes) {
            if (emp.getChef() == null) {
                return emp;
            }
        }
        return null;
    }

    public List<Employe> getNoManager() {
        List<Employe> emps = new ArrayList<>();
        for (Employe emp : this.employes) {
            if (emp.getChef() != null) {
                emps.add(emp);
            }
        }
        return emps;
    }

}
