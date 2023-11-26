/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.service;

import com.a00n.dao.IDao;
import com.a00n.entities.Employe;
import com.a00n.entities.Node;
import com.a00n.entities.Service;
import com.a00n.utils.HibernateUtil;
import com.a00n.utils.PersistenceSessionFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author ay0ub
 */
@Named
@ApplicationScoped
public class EmployeService implements IDao<Employe> {

    private Session session;

    public EmployeService() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public boolean create(Employe o) {
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Employe o) {
        session.beginTransaction();
        session.merge(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(Employe o) {
        session.beginTransaction();
        session.remove(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Employe getById(Long id) {
        Employe employe = null;
        session.beginTransaction();
        employe = (Employe) session.get(Employe.class, id);
        session.getTransaction().commit();
        return employe;
    }

    @Override
    public List<Employe> getAll() {

        List<Employe> employes = null;
//        session.beginTransaction();
        employes = session.createQuery("from Employe", Employe.class).list();
//        session.getTransaction().commit();
        return employes;
    }

    public TreeNode<Node> createNodes() {
        List<Service> services = session.createSelectionQuery("from Service", Service.class).list();
        TreeNode<Node> root = new DefaultTreeNode<>(new Node("Tree"));
//        System.out.println("========================================================================================================");
//        var service = services.get(0);
//        TreeNode<Node> serviceNode = new DefaultTreeNode<>(new Node(service.getNom()), root);
//        var chef = service.getManager();
//        TreeNode<Node> chefNode = new DefaultTreeNode<>(new Node(chef.getNom() + " " + chef.getPrenom()), serviceNode);
//        service.getNoManager().forEach(employe -> {
//            TreeNode<Node> empNode = new DefaultTreeNode<>(new Node(employe.getNom() + " " + employe.getPrenom()), chefNode);
//        });
//
//        var service2 = services.get(1);
//        TreeNode<Node> serviceNode2 = new DefaultTreeNode<>(new Node(service2.getNom()), root);
//        var chef2 = service2.getManager();
//        TreeNode<Node> chefNode2 = new DefaultTreeNode<>(new Node(chef2.getNom() + " " + chef2.getPrenom()), serviceNode2);
//        service2.getNoManager().forEach(employe -> {
//            TreeNode<Node> empNode = new DefaultTreeNode<>(new Node(employe.getNom() + " " + employe.getPrenom()), chefNode2);
//        });

        for (Service service : services) {
            System.out.println(service.getNom());
            TreeNode<Node> serviceNode = new DefaultTreeNode<>(new Node(service.getNom() + " (Service)"), root);
            Employe chef = service.getManager();
            System.out.println(chef);
            if (chef != null) {
                TreeNode<Node> chefNode = new DefaultTreeNode<>(new Node(chef.getNom() + " " + chef.getPrenom() + " (Manager)"), serviceNode);
                service.getNoManager().forEach(employe -> {
                    TreeNode<Node> empNode = new DefaultTreeNode<>(new Node(employe.getNom() + " " + employe.getPrenom() + " (Employe)"), chefNode);
                });
            } else {
                service.getEmployes().forEach(employe -> {
                    TreeNode<Node> empNode = new DefaultTreeNode<>(new Node(employe.getNom() + " " + employe.getPrenom() + " (Employe)"), serviceNode);
                });
            }
        }
        return root;
    }

    public List<Object[]> nbEmployees() {
        List<Object[]> employees = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employees = session.createQuery("select count(*),s.nom from Employe e, Service s where e.service.id=s.id group by s.nom").list();
        session.getTransaction().commit();
        return employees;
    }

}
