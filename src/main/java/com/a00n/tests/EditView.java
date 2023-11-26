/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.tests;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author ay0ub
 */
@Named("dtEditView")
@ViewScoped
public class EditView implements Serializable {

    private List<Product> products1;
    private List<Product> products2;
    private List<Product> products3;

    @Inject
    private ProductService service;

    @PostConstruct
    public void init() {
        products1 = service.getClonedProducts(10);
        products2 = service.getClonedProducts(10);
        products3 = service.getClonedProducts(10);
    }

    public List<Product> getProducts1() {
        return products1;
    }

    public List<Product> getProducts2() {
        return products2;
    }

    public List<Product> getProducts3() {
        return products3;
    }

    public InventoryStatus[] getInventoryStatusList() {
        return InventoryStatus.values();
    }

    public void setService(ProductService service) {
        this.service = service;
    }

    public void onRowEdit(RowEditEvent<Product> event) {
        System.out.println("=====================================================");
        System.out.println(event.getObject());
        FacesMessage msg = new FacesMessage("Product Edited", String.valueOf(event.getObject().getCode()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Product> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getCode()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
