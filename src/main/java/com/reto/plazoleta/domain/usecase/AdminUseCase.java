package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.api.IAdminUseCasePort;
import com.reto.plazoleta.domain.exception.EmptyFieldsException;
import com.reto.plazoleta.domain.exception.InvalidDataException;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;

public class AdminUseCase implements IAdminUseCasePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    public AdminUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        validateRestaurantFieldsEmpty(restaurantModel);
        validateRestaurantPhone(restaurantModel.getPhone());
        if(isContainsRestaurantNameOnlynumbers(restaurantModel.getName())) {
            throw new InvalidDataException("The name of the restaurant cannot only contain numbers");
        }
        restaurantPersistencePort.saveRestaurant(restaurantModel);
    }

    private void validateRestaurantFieldsEmpty(RestaurantModel restaurantModel) {
        if( restaurantModel.getName().replaceAll(" ", "").isEmpty() || restaurantModel.getUrlLogo().replaceAll(" ", "").isEmpty() ||
                restaurantModel.getAddress().replaceAll(" ", "").isEmpty() || restaurantModel.getPhone().replaceAll(" ", "").isEmpty() ||
                restaurantModel.getNit() == null || restaurantModel.getIdOwner() == null ) {
            throw new EmptyFieldsException("The field must not be empty");
        }
    }


    private void validateRestaurantPhone(String phoneRestaurant) {
        String phoneRestaurantNoSpaces = phoneRestaurant.replaceAll(" ", "");
        if(phoneRestaurantNoSpaces.startsWith("+")){
            if(!phoneRestaurantNoSpaces.matches("\\+\\d+") ||
                    phoneRestaurantNoSpaces.length() > 13 ){
                throw new InvalidDataException("The cell phone format is wrong");
            }
        } else {
            if (phoneRestaurantNoSpaces.length() > 10 || !phoneRestaurantNoSpaces.matches("[0-9]+")) {
                throw new InvalidDataException("The cell phone format is wrong");
            }
        }
    }


    private boolean isContainsRestaurantNameOnlynumbers(String nameRestaurant) {
        if(nameRestaurant.matches("\\d+")) {
            return true;
        }
        return false;
    }
}
