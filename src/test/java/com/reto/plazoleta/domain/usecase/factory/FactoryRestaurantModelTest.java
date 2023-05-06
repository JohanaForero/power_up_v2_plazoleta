package com.reto.plazoleta.domain.usecase.factory;

import com.reto.plazoleta.domain.model.RestaurantModel;

public class FactoryRestaurantModelTest {

    public static RestaurantModel restaurantModel() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setIdRestaurant(1L);
        restaurantModel.setName("Sabroson17");
        restaurantModel.setUrlLogo("http://sabroson.img");
        restaurantModel.setAddress("Cra 10");
        restaurantModel.setPhone("3018452367");
        restaurantModel.setNit(843775l);
        restaurantModel.setIdOwner(5l);
        return restaurantModel;
    }

    public static RestaurantModel restaurantModelEmptyFields() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setName(" ");
        restaurantModel.setUrlLogo(" ");
        restaurantModel.setAddress(" ");
        restaurantModel.setPhone(" ");
        restaurantModel.setNit(843775l);
        restaurantModel.setIdOwner(5l);
        return restaurantModel;
    }

    public static RestaurantModel restaurantModelWrongPhone() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setName("Sabroson17");
        restaurantModel.setUrlLogo("http://sabroson.img");
        restaurantModel.setAddress("Cra 10");
        restaurantModel.setPhone("4563018452367");
        restaurantModel.setNit(843775l);
        restaurantModel.setIdOwner(5l);
        return restaurantModel;
    }

    public static RestaurantModel restaurantModelWhereNameIsJustNumbers() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setName("17645676");
        restaurantModel.setUrlLogo("http://sabroson.img");
        restaurantModel.setAddress("Cra 10");
        restaurantModel.setPhone("3018452367");
        restaurantModel.setNit(843775l);
        restaurantModel.setIdOwner(5l);
        return restaurantModel;
    }

}
