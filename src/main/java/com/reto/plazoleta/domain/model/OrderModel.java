package com.reto.plazoleta.domain.model;

import java.util.Date;
public class OrderModel {
    private Integer idOrder;
    private Date date;
    private int idChef;
    private String name;
    public OrderModel() {
    }
    public OrderModel(Integer idOrder, Date date, int idChef, String name) {
        this.idOrder=idOrder;
        this.date = date;
        this.idChef = idChef;
        this.name =name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getIdOrder() {
        return idOrder;
    }
    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }
    public int getIdChef() {
        return idChef;
    }
    public void setIdChef(int idChef) {
        this.idChef = idChef;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

}
