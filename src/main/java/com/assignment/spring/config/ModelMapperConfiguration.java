package com.assignment.spring.config;

import com.assignment.spring.dto.UserDto;
import com.assignment.spring.dto.UserRegistrationDto;
import com.assignment.spring.dto.WeatherDto;
import com.assignment.spring.entity.User;
import com.assignment.spring.entity.Weather;
import com.assignment.spring.weather.model.WeatherCondition;
import com.assignment.spring.weather.model.WeatherResponse;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		final ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

		final TypeMap<WeatherResponse, Weather> weatherTypeMap = modelMapper.createTypeMap(WeatherResponse.class, Weather.class);
		weatherTypeMap.addMapping(response -> response.getCityName(), Weather::setCity);
		weatherTypeMap.addMapping(response -> response.getAdditionalData().getCountry(), Weather::setCountry);
		weatherTypeMap.addMapping(response -> response.getMainData().getTemperature(), Weather::setTemperature);
		// there should always be at least one weather condition, from API description this shouldn't be optional field
		weatherTypeMap.addMappings(mapper -> mapper.using(weatherConditionConverter()).map(WeatherResponse::getWeatherConditions, Weather::setWeatherCondition));

		final TypeMap<UserRegistrationDto, User> userRegistrationTypeMap = modelMapper.createTypeMap(UserRegistrationDto.class, User.class);
		userRegistrationTypeMap.addMappings(mapper -> mapper.skip(User::setPermissions));

		modelMapper.createTypeMap(User.class, UserDto.class);
		final TypeMap<UserDto, User> userTypeMap = modelMapper.createTypeMap(UserDto.class, User.class);
		userTypeMap.addMappings(mapper -> mapper.skip(User::setPassword));

		modelMapper.createTypeMap(Weather.class, WeatherDto.class);

		// this will throw exception in case there are mapping errors
		modelMapper.validate();

		return modelMapper;
	}

	private Converter<List<WeatherCondition>, String> weatherConditionConverter() {
		return context -> {
			if (context.getSource().isEmpty()) {
				return null;
			} else {
				return context.getSource().get(0).getDescription();
			}
		};
	}
}
