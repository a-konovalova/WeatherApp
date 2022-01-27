package space.konovalova.weatherapp

import org.junit.Test
import org.junit.Assert.*

class WeatherApiServiceUnitTestsForFields {
    @Test
    fun tempFieldsAreNotNull(){
        var weatherApiService: WeatherApiService = WeatherApiService()

        assertFalse(weatherApiService.getWeatherForCity("Нижний Новгород").temp == null)
        assertFalse(weatherApiService.getWeatherForCity("Нижний Новгород").tempMax == null)
        assertTrue(weatherApiService.getWeatherForCity("Нижний Новгород").tempMin != null)
    }

    @Test
    fun timeFieldsAreNotNull(){
        var weatherApiService: WeatherApiService = WeatherApiService()

        assertFalse(weatherApiService.getWeatherForCity("Нижний Новгород").sunrise == null)
        assertTrue(WeatherApiService().getWeatherForCity("Нижний Новгород").sunset != null)
    }

    @Test
    fun windFieldIsNotNull(){
        assertTrue(WeatherApiService().getWeatherForCity("Нижний Новгород").windSpeed != null)
    }
}