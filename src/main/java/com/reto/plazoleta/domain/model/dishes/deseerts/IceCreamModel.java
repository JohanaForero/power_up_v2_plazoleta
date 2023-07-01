package com.reto.plazoleta.domain.model.dishes.deseerts;

public class IceCreamModel extends Desserts{

    private String accompaniment;


    public IceCreamModel(String dessertType) {
        super(dessertType);
    }

    public String getAccompaniment() {
        return accompaniment;
    }

    public void setAccompaniment(String accompaniment) {
        this.accompaniment = accompaniment;
    }
}
