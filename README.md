Weather Log Application
---

This application is used to get current weather data from [OpenWeather](https://openweathermap.org/) and store it in the database.
Application is implemented in fully asynchronous way using [Spring WebFlux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html) and [Spring Data R2DBC](https://spring.io/projects/spring-data-r2dbc).

### Initial setup

Application uses Java 14 and Maven 3.6.3 which should be installed on system. Current application configuration expects PostgreSQL database `dojo` to be available at `localhost:5432` with user
 `postgres` having password `postgres`, but all this can be changed in `application.yml` configuration file.

### Running application

Application can be started using following command:
>mvn spring-boot:run -Dspring-boot.run.arguments=--weather.apiKey=**YOUR_API_KEY**

Another option is to add `weather.apiKey` to `application.yml` configuration file, in which case running application is much simpler:
>mvn spring-boot:run

### Footnote
It's possible to generate the API key going to the [OpenWeather Sign up](https://openweathermap.org/appid) page.
