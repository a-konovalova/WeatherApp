package space.konovalova.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageButton
import android.widget.TextView
import org.jetbrains.anko.doAsync
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var resultInfo: TextView? = null
    private var min: TextView? = null
    private var max: TextView? = null
    private var sunset: TextView? = null
    private var sunrise: TextView? = null
    private var wind: TextView? = null
    private var updateInfo: TextView? = null
    private var buttonUpdate: ImageButton? = null
    private var dateNow: TextView? = null
    private var weatherApiService: WeatherApiService = WeatherApiService()
    private var currentDateAndTime: CurrentDateAndTime = CurrentDateAndTime()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultInfo = findViewById(R.id.result_info)
        min = findViewById(R.id.min)
        max = findViewById(R.id.max)
        sunset = findViewById(R.id.sunset)
        sunrise = findViewById(R.id.sunrise)
        wind = findViewById(R.id.wind)
        updateInfo = findViewById(R.id.update_info)
        buttonUpdate = findViewById(R.id.button_update)
        dateNow = findViewById(R.id.dateNow)

        getDataByUrl()
        dateTime()

        buttonUpdate?.setOnClickListener {
            getDataByUrl()
            dateTime()
            buttonAnimation(buttonUpdate)
        }
    }

    private fun dateTime(){
        dateNow?.text = "Последнее обновление: " + currentDateAndTime.getCurrentDate()
        updateInfo?.text = currentDateAndTime.getCurrentTime()
    }

    private fun textAnimation(textView: TextView?){
        var anim: Animation?
        anim = loadAnimation(this, R.anim.alpha)
        textView?.startAnimation(anim)
    }

    private fun buttonAnimation(imageButton: ImageButton?){
        var anim: Animation?
        anim = loadAnimation(this, R.anim.rotate)
        imageButton?.startAnimation(anim)
    }

    @SuppressLint("SetTextI18n")
    private fun getDataByUrl (){
        doAsync {
            var weather = weatherApiService.getWeatherForCity("Нижний Новгород")
            /*val dotenv = dotenv {
                directory = "/assets"
                filename = "env.env"
            }
            dotenv["apikey"]*/
            //val key = dotenv.get("apikey")

            resultInfo?.text = "${weather.temp} ℃"
            //resultInfo?.text = dotenv["apikey"]
            textAnimation(resultInfo)

            min?.text = "Минимальная температура\n${weather.tempMin} ℃"
            textAnimation(min)

            max?.text = "Максимальная температура\n${weather.tempMax} ℃"
            textAnimation(max)

            sunrise?.text = "Восход\n${weather.sunrise}"
            textAnimation(sunrise)

            sunset?.text = "Закат\n${weather.sunset}"
            textAnimation(sunset)

            wind?.text = "Скорость ветра\n${weather.windSpeed} м/с"
            textAnimation(wind)
        }
    }
}