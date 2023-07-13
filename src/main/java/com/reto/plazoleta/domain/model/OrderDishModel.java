package com.reto.plazoleta.domain.model;

import com.reto.plazoleta.domain.model.dishes.DishModel;

public class OrderDishModel {

    private Long idOrderDish;
    private OrderModel orderModel;
    private DishModel dishModel;
    private Integer amount;
    private Integer grams;
    private String accompaniment;
    private String flavor;
    public OrderDishModel() {
    }

    public OrderDishModel(Long idOrdersDishes, OrderModel orderModel, DishModel dishModel, Integer amount, Integer grams, String accompaniment, String flavor) {
        this.idOrderDish = idOrdersDishes;
        this.orderModel = orderModel;
        this.dishModel = dishModel;
        this.amount = amount;
        this.grams = grams;
        this.accompaniment = accompaniment;
        this.flavor = flavor;
    }

    public Long getIdOrderDish() {
        return idOrderDish;
    }

    public void setIdOrderDish(Long idOrderDish) {
        this.idOrderDish = idOrderDish;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public DishModel getDishModel() {
        return dishModel;
    }

    public void setDishModel(DishModel dishModel) {
        this.dishModel = dishModel;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getGrams() {
        return grams;
    }

    public void setGrams(Integer grams) {
        this.grams = grams;
    }

    public String getAccompaniment() {
        return accompaniment;
    }

    public void setAccompaniment(String accompaniment) {
        this.accompaniment = accompaniment;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

}