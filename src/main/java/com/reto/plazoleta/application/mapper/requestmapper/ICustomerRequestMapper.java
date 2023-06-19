package com.reto.plazoleta.application.mapper.requestmapper;

import com.reto.plazoleta.application.dto.request.CreateOrderRequestDto;
import com.reto.plazoleta.application.dto.request.DishFromOrderRequestDto;
import com.reto.plazoleta.application.dto.response.CanceledOrderResponseDto;
import com.reto.plazoleta.domain.model.OrderDishModel;
import com.reto.plazoleta.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICustomerRequestMapper {

    @Mapping(target = "restaurantModel.idRestaurant", source = "idRestaurant")
    OrderModel toOrderModel(CreateOrderRequestDto createOrderRequestDto);

    @Mapping(target = "dishModel.idDish", source = "idDish")
    @Mapping(target = "dishModel.name", source = "name")
    OrderDishModel toOrderDishModel(DishFromOrderRequestDto dishFromOrderAndAmountRequestDto);

    @Mapping(target = "idCustomer", source = "idUserCustomer")
    CanceledOrderResponseDto orderModelToOrderCanceledResponseDto(OrderModel orderModel);
}