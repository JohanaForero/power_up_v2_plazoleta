package com.reto.plazoleta.application.mapper.responsemapper;

import com.reto.plazoleta.application.dto.response.CanceledOrderResponseDto;
import com.reto.plazoleta.application.dto.response.CreateOrderResponseDto;
import com.reto.plazoleta.application.dto.response.ResponseOrdersDishesDto;
import com.reto.plazoleta.application.dto.response.ResponseOrdersPaginatedDto;
import com.reto.plazoleta.application.dto.response.RestaurantResponsePaginatedDto;
import com.reto.plazoleta.domain.model.OrderDishModel;
import com.reto.plazoleta.domain.model.OrderModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ICustomerResponseMapper {

    RestaurantResponsePaginatedDto toRestaurantResponse(RestaurantModel restaurantModel);

    CreateOrderResponseDto toCreateOrderResponseDto(OrderModel orderModel);

    @Mapping(target = "ordersByStatus", source = "ordersDishesModel")
    ResponseOrdersPaginatedDto orderModelToOrdersPaginatedResponseDto(OrderModel orderModel);

    @Mapping(target = "idDish", source = "dishModel.idDish")
    @Mapping(target = "name", source = "dishModel.name")
    @Mapping(target = "imageDish", source = "dishModel.imageDish")
    @Mapping(target = "descriptionDish", source = "dishModel.descriptionDish")
    ResponseOrdersDishesDto orderModelToOrdersPaginatedResponseDto(OrderDishModel orderDishModel);

    @Mapping(target = "idCustomer", source = "idUserCustomer")
    CanceledOrderResponseDto orderModelToOrderCanceledResponseDto(OrderModel orderModel);
}