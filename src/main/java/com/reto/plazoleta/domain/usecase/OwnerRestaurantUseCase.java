package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.api.IOwnerRestaurantServicePort;
import com.reto.plazoleta.domain.exception.DishNotExistsException;
import com.reto.plazoleta.domain.exception.InvalidDataException;
import com.reto.plazoleta.domain.exception.ObjectNotFoundException;
import com.reto.plazoleta.domain.gateways.IUserGateway;
import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.domain.spi.IEmployeeRestaurantPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.infraestructure.configuration.security.jwt.JwtProvider;
import com.reto.plazoleta.infraestructure.drivenadapter.gateways.User;

public class OwnerRestaurantUseCase implements IOwnerRestaurantServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;

    private final IUserGateway userGateway;
    private final JwtProvider jwtProvider;

    public OwnerRestaurantUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort,
                                  ICategoryPersistencePort categoryPersistencePort, IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, IUserGateway userGateway, JwtProvider jwtProvider) {
        this.dishPersistencePort = dishPersistencePort;
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.userGateway = userGateway;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public DishModel saveDish(DishModel dishModel) {
        if(dishModel.getPrice() <= 0) {
            throw new InvalidDataException("Price must be greater than zero");
        }
        RestaurantModel restaurantModel = restaurantPersistencePort.findByIdRestaurant(dishModel.getRestaurantModel().getIdRestaurant());
        if(restaurantModel == null) {
            throw new InvalidDataException("The restaurant does not exist");
        }
        CategoryModel categoryModel = categoryPersistencePort.findById(dishModel.getCategoryModel().getIdCategory());
        if(categoryModel == null) {
            throw new InvalidDataException("The category does not exist");
        }
        dishModel.setRestaurantModel(restaurantModel);
        dishModel.setCategoryModel(categoryModel);
        dishModel.setStateDish(true);
        return dishPersistencePort.saveDish(dishModel);
    }

    @Override
    public DishModel updateDish(DishModel dishModel) {
        DishModel updateDishModel = dishPersistencePort.findById(dishModel.getIdDish());
        if (updateDishModel == null) {
            throw new DishNotExistsException("The dish not exist");
        }
        RestaurantModel restaurantModel = restaurantPersistencePort.findByIdRestaurant(dishModel.getRestaurantModel().getIdRestaurant());
        if (restaurantModel == null) {
            throw new ObjectNotFoundException("The restaurant does not exist");
        }
        if (!updateDishModel.getRestaurantModel().getIdRestaurant().equals(restaurantModel.getIdRestaurant())) {
            throw new InvalidDataException("Only the owner of the restaurant can update the dish");
        }

        updateDishModel.setPrice(dishModel.getPrice());
        updateDishModel.setDescriptionDish(dishModel.getDescriptionDish());

        return dishPersistencePort.updateDish(updateDishModel);
    }

    @Override
    public DishModel updateStateDish(DishModel dishModel) {
        DishModel updateStateDishModel = dishPersistencePort.findById(dishModel.getIdDish());
        if (updateStateDishModel == null) {
            throw new DishNotExistsException("The dish not exist");
        }
        RestaurantModel restaurantModel = restaurantPersistencePort.findByIdRestaurant(dishModel.getRestaurantModel().getIdRestaurant());
        if (restaurantModel == null) {
            throw new ObjectNotFoundException("The restaurant does not exist");
        }
        if (!updateStateDishModel.getRestaurantModel().getIdRestaurant().equals(restaurantModel.getIdRestaurant())) {
            throw new InvalidDataException("Only the owner of the restaurant can update the status of the dish");
        }

        updateStateDishModel.setStateDish(dishModel.getStateDish());

        return dishPersistencePort.updateDish(updateStateDishModel);
    }

    @Override
    public EmployeeRestaurantModel saveEmployeeRestaurant(EmployeeRestaurantModel employeeRestaurantModel, String tokenWithBearerPrefix) {
        String emailFromUserOwnerOfARestaurant = jwtProvider.getAuthentication(tokenWithBearerPrefix.replace("Bearer ", "").trim()).getPrincipal().toString();
        User userOwnerFound = userGateway.getUserByEmailInTheToken(emailFromUserOwnerOfARestaurant, tokenWithBearerPrefix);
        System.out.println("user employee" +userOwnerFound.getIdUser());
        System.out.println("restaurant" +employeeRestaurantModel.getIdRestaurant());
        final RestaurantModel restaurantFoundModelByIdRestaurant = this.restaurantPersistencePort.findByIdRestaurant(employeeRestaurantModel.getIdRestaurant());
        System.out.println("owner" + restaurantFoundModelByIdRestaurant.getIdOwner());
        if(restaurantFoundModelByIdRestaurant == null || !restaurantFoundModelByIdRestaurant.getIdOwner().equals(userOwnerFound.getIdUser())) {
            throw new ObjectNotFoundException("Restaurant not Exist");
        }
        employeeRestaurantModel.setIdRestaurant(restaurantFoundModelByIdRestaurant.getIdRestaurant());
        return this.employeeRestaurantPersistencePort.saveEmployeeRestaurant(employeeRestaurantModel);
    }
}
