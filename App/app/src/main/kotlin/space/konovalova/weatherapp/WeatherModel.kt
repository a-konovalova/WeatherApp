package space.konovalova.weatherapp

class WeatherModel{
    var utcConverterToNormalTime: UtcConverterToNormalTime = UtcConverterToNormalTime()

    var temp: Int? = null
    var tempMin: Int? = null
    var tempMax: Int? = null
    var sunset: String? = null
    var sunrise: String? = null
    var windSpeed: Double? = null

    constructor(temp: Int, tempMin: Int, tempMax: Int, sunset: Int, sunrise: Int, windSpeed: Double){
        this.temp = temp
        this.tempMin = tempMin
        this.tempMax = tempMax
        this.sunset = utcConverterToNormalTime.utcToNormalTime(sunset)
        this.sunrise = utcConverterToNormalTime.utcToNormalTime(sunrise)
        this.windSpeed = windSpeed
    }
}