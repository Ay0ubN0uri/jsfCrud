/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.domain;

import com.a00n.entities.Service;
import com.a00n.service.ServiceService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import org.primefaces.PrimeFaces;

/**
 *
 * @author ay0ub
 */
@Named
@ViewScoped
public class ServiceBean implements Serializable {

    private List<Service> services;

    private Service selectedService;

    private List<Service> selectedServices;

    @Inject
    private ServiceService serviceService;

    @PostConstruct
    public void init() {
        this.services = this.serviceService.getAll();
    }

    public List<Service> getServices() {
        return services;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public List<Service> getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(List<Service> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public void openNew() {
        this.selectedService = new Service();
    }

    public void saveService() {
        serviceService.create(this.selectedService);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Service Added"));
        this.init();

        PrimeFaces.current().executeScript("PF('manageServiceDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-services");
    }

    public void deleteService() {
        serviceService.delete(this.selectedService);
        this.selectedService = null;
        this.init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Service Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-services");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedServices()) {
            int size = this.selectedServices.size();
            return size > 1 ? size + " services selected" : "1 service selected";
        }

        return "Delete";
    }

    public boolean hasSelectedServices() {
        return this.selectedServices != null && !this.selectedServices.isEmpty();
    }

    public void deleteSelectedServices() {
        this.selectedServices.forEach(service -> {
            serviceService.delete(service);
        });
        this.selectedServices = null;
        this.init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Services Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-services");
        PrimeFaces.current().executeScript("PF('dtServices').clearFilters()");
    }
}
