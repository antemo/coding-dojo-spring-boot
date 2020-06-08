package com.assignment.spring.repository;

import com.assignment.spring.entity.Weather;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface WeatherRepository extends R2dbcRepository<Weather, Long> {

}
