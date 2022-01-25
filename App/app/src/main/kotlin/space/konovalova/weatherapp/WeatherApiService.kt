package space.konovalova.weatherapp

import org.json.JSONObject
import java.net.URL
import kotlin.math.roundToInt

class WeatherApiService {
    private var baseUrl = "https://api.openweathermap.org/data/2.5/weather"
    private var apiKey = "9d5086635e15fe649921a5746f53a41f"

    fun getWeatherForCity(city: String): WeatherModel{
        //var city = "Nizhny Novgorod"
        val apiUrl: String = "${baseUrl}?q=$city&appid=${apiKey}&units=metric&lang=ru"
        val apiResponse: String = URL(apiUrl).readText()

        val tempInfo: JSONObject = JSONObject(apiResponse).getJSONObject("main")
        val sunsetAndSunriseInfo: JSONObject = JSONObject(apiResponse).getJSONObject("sys")
        val windInfo: JSONObject = JSONObject(apiResponse).getJSONObject("wind")

        return WeatherModel(tempInfo.getDouble("temp").roundToInt(),
            tempInfo.getDouble("temp_min").roundToInt(),
            tempInfo.getDouble("temp_max").roundToInt(),
            sunsetAndSunriseInfo.getInt("sunset"),
            sunsetAndSunriseInfo.getInt("sunrise"),
            windInfo.getDouble("speed")
        )
    }
}