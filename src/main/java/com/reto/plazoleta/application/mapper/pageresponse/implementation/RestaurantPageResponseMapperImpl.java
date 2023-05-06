package com.reto.plazoleta.application.mapper.pageresponse.implementation;

import com.reto.plazoleta.application.dto.response.RestaurantResponsePageableDto;
import com.reto.plazoleta.application.mapper.pageresponse.IRestaurantPageResponseMapper;
import com.reto.plazoleta.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class RestaurantPageResponseMapperImpl implements IRestaurantPageResponseMapper {

    @Override
    public Page<RestaurantResponsePageableDto> toRestaurantResponsePageable(Page<RestaurantModel> restaurantsModelPage) {
        return restaurantsModelPage.map((RestaurantModel restaurantModel) -> {
            RestaurantResponsePageableDto restaurantResponseDto = new RestaurantResponsePageableDto();
            restaurantResponseDto.setName(restaurantModel.getName());
            restaurantResponseDto.setUrlLogo(restaurantModel.getUrlLogo());
            return restaurantResponseDto;
        });
    }
}
