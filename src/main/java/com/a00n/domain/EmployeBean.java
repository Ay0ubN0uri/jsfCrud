/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.domain;

import com.a00n.entities.Employe;
import com.a00n.entities.Service;
import com.a00n.service.EmployeService;
import com.a00n.service.ServiceService;
import com.a00n.utils.HibernateUtil;
import com.a00n.utils.PersistenceSessionFactory;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author ay0ub
 */
@Named
//@SessionScoped
@ViewScoped
public class EmployeBean implements Serializable {

    private UploadedFile file;
    private List<Employe> employes;
    private List<Service> services;

    private Employe selectedEmploye;

    private List<Employe> selectedEmployes;

    @Inject
    private EmployeService employeService;
    @Inject
    private ServiceService serviceService;

//    @Inject
//    @PersistenceSessionFactory
//    private SessionFactory sessionFactory;
    private BarChartModel barModel;

//    public EmployeBean() {
//        this.selectedEmploye = new Employe();
//        this.selectedEmploye.setService(new Service());
//        this.selectedEmploye.setChef(new Employe());
//        this.employes = this.employeService.getAll();
//        this.services = this.serviceService.getAll();
//        this.file = null;
//        this.createBarModel();
//    }
    @PostConstruct
    public void init() {
        this.selectedEmploye = new Employe();
        this.selectedEmploye.setService(new Service());
        this.selectedEmploye.setChef(new Employe());
//        System.out.println(sessionFactory);
//        HibernateUtil.getSessionFactory().inSession(session -> {
//            session.createQuery("from Employe", Employe.class).getResultStream().forEach(emp -> {
//                System.out.println(emp.getService().getId());
//            });
//        });
//        if (this.employes == null) {
//        this.employes = HibernateUtil.getSessionFactory().openSession().createQuery("from Employe", Employe.class).list();
        this.employes = this.employeService.getAll();
//        var emp = this.employes.get(5);
//        System.out.println(emp.getService());
//        }
//        if (this.services == null) {
        this.services = this.serviceService.getAll();
//        }
        this.file = null;
        this.createBarModel();
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public Employe getSelectedEmploye() {
        return selectedEmploye;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public void setSelectedEmploye(Employe selectedEmploye) {
        this.selectedEmploye = selectedEmploye;
        this.selectedEmploye.setService(new Service());
        this.selectedEmploye.setChef(new Employe());
    }

    public List<Employe> getSelectedEmployes() {
        return selectedEmployes;
    }

    public void setSelectedEmployes(List<Employe> selectedEmployes) {
        this.selectedEmployes = selectedEmployes;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
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

    public void openNew() {
        this.selectedEmploye = new Employe();
        this.selectedEmploye.setService(new Service());
        this.selectedEmploye.setChef(new Employe());
    }

    private static final String UPLOAD_DIRECTORY = "/home/ay0ub/NetBeansProjects/jsf/target/jsf-1.0/assets/images";

    public void uploadEmployeImage() {
        try {
            String fileName = System.currentTimeMillis() + "_" + this.file.getFileName();
            Path destPath = Path.of(UPLOAD_DIRECTORY, fileName);
            Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
            this.selectedEmploye.setImage(fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void saveEmploye() {
        System.out.println("__________________________________________________________________");
        if (this.file != null) {
            System.out.println(this.file.getFileName());
            this.uploadEmployeImage();
        }
        this.selectedEmploye.setService(serviceService.getById(this.selectedEmploye.getService().getId()));
        this.selectedEmploye.setChef(employeService.getById(this.selectedEmploye.getChef().getId()));
        employeService.create(this.selectedEmploye);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employe Added"));
        this.init();

        PrimeFaces.current().executeScript("PF('manageEmployeDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-employes");
    }

    public void deleteEmploye() {
        employeService.delete(this.selectedEmploye);
        this.selectedEmploye = null;
        this.init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employe Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-employes");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedEmployes()) {
            int size = this.selectedEmployes.size();
            return size > 1 ? size + " employes selected" : "1 employe selected";
        }

        return "Delete";
    }

    public boolean hasSelectedEmployes() {
        return this.selectedEmployes != null && !this.selectedEmployes.isEmpty();
    }

    public void deleteSelectedEmployes() {
        this.selectedEmployes.forEach(employe -> {
            employeService.delete(employe);
        });
        this.selectedEmployes = null;
        this.init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employes Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-employes");
        PrimeFaces.current().executeScript("PF('dtEmployes').clearFilters()");
    }

    public ChartModel initBarModel() {
        CartesianChartModel model = new CartesianChartModel();
        ChartSeries machines = new ChartSeries();
        machines.setLabel("machines");
        model.setAnimate(true);
        for (Object[] m : employeService.nbEmployees()) {
            machines.set(m[1], Integer.parseInt(m[0].toString()));
        }
        model.addSeries(machines);

        return model;
    }

    public void createBarModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Number of employees by service");

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> bgColor = new ArrayList<>();
        List<String> borderColor = new ArrayList<>();
        for (Object[] emp : employeService.nbEmployees()) {
            var s = String.valueOf(emp[0]);
            Number n = Long.valueOf(s);
            values.add(n);
            labels.add(String.valueOf(emp[1]));
            bgColor.add(generateColors(true));
            borderColor.add(generateColors(false));
        }
        barDataSet.setData(values);
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
        data.addChartDataSet(barDataSet);
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
//        options.setMaintainAspectRatio(false);
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Bar Chart");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("italic");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        barModel.setOptions(options);
    }

    public String generateColors(boolean bgColor) {

        Random rand = new Random();

        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        double alpha = 0.2; // You can adjust the alpha value if needed
        if (bgColor) {
            return String.format("rgba(%d, %d, %d, %.1f)", r, g, b, alpha);
        } else {
            return String.format("rgba(%d, %d, %d)", r, g, b);
        }
    }

    public byte[] getChartAsByteArray() {
        try {
            return Files.readAllBytes(Paths.get("/home/ay0ub/Desktop/ayoub nouri.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("a00na00na00na00na00na00na00na00na00na00na00na00na00na00na00na00n " + event.getFile().getFileName());
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void upload() {
        if (file != null) {
            System.out.println(this.file.getFileName());
            System.out.println(this.selectedEmploye);
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void a00n() {
        System.out.println("00000000000000000000000000000000000000000000000000000");
        System.out.println(this.selectedEmploye.getService());
    }
}
