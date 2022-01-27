package space.konovalova.weatherapp

data class WeatherModel(
    var temp: Int?,
    var tempMin: Int?,
    var tempMax: Int?,
    var sunset: String?,
    var sunrise: String? ,
    var windSpeed: Double? ,
)