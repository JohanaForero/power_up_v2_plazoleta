package com.reto.plazoleta.application.mapper.pageresponse;

import com.reto.plazoleta.application.dto.response.RestaurantResponsePageableDto;
import com.reto.plazoleta.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;

public interface IRestaurantPageResponseMapper {
    Page<RestaurantResponsePageableDto> toRestaurantResponsePageable(Page<RestaurantModel> restaurantsModelPage);
}
