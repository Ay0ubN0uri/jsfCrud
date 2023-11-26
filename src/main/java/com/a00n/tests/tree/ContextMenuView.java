/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.tests.tree;

import com.a00n.entities.Node;
import com.a00n.service.EmployeService;
import com.a00n.service.ServiceService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import org.primefaces.model.TreeNode;

/**
 *
 * @author ay0ub
 */
@Named("ttContextMenuView")
@ViewScoped
public class ContextMenuView implements Serializable {

    private TreeNode<Node> root;

    private TreeNode<Node> selectedNode;

    @Inject
    private DocumentService service;
    @Inject
    private EmployeService employeService;
    @Inject
    private ServiceService serviceService;

    @PostConstruct
    public void init() {
//        root = service.createDocuments();
        root = employeService.createNodes();
    }

    public TreeNode<Node> getRoot() {
        return root;
    }

    public void setService(DocumentService service) {
        this.service = service;
    }

    public EmployeService getEmployeService() {
        return employeService;
    }

    public void setEmployeService(EmployeService employeService) {
        this.employeService = employeService;
    }

    public ServiceService getServiceService() {
        return serviceService;
    }

    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    public TreeNode<Node> getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode<Node> selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void deleteNode() {
        selectedNode.getChildren().clear();
        selectedNode.getParent().getChildren().remove(selectedNode);
        selectedNode.setParent(null);

        selectedNode = null;
    }
}
