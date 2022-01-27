package space.konovalova.weatherapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import org.jetbrains.anko.doAsync

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

        if(isNetworkAvailable(applicationContext)){
            getDateAndTimeOfLastUpdate()
            getDataByUrl()
        } else {
            Toast.makeText(applicationContext, "Check your Internet connection", Toast.LENGTH_SHORT).show()
        }

        buttonUpdate?.setOnClickListener {
            if(isNetworkAvailable(applicationContext)){
                buttonAnimation(buttonUpdate)
                getDateAndTimeOfLastUpdate()
                getDataByUrl()

            } else {
                buttonUpdate?.isClickable = false
                Toast.makeText(applicationContext, "Check your Internet connection", Toast.LENGTH_SHORT).show()
            }
            buttonUpdate?.isClickable=true
        }
    }

    private fun getDateAndTimeOfLastUpdate(){
        dateNow?.text = currentDateAndTime.getCurrentDate()
        updateInfo?.text = "Last Update: " + currentDateAndTime.getCurrentTime()
    }

    private fun textAnimation(textView: TextView?){
        textView?.startAnimation(loadAnimation(this, R.anim.alpha))
    }

    private fun buttonAnimation(imageButton: ImageButton?){
        imageButton?.startAnimation(loadAnimation(this, R.anim.rotate))
    }
    
    private fun getDataByUrl () {
        doAsync {
            val weather: WeatherModel = weatherApiService.getWeatherForCity("Nizhny Novgorod")

            resultInfo?.text = "${weather.temp} ℃"
            textAnimation(resultInfo)

            min?.text = "Minimal temperature\n${weather.tempMin} ℃"
            textAnimation(min)

            max?.text = "Maximum temperature\n${weather.tempMax} ℃"
            textAnimation(max)

            sunrise?.text = "Sunrise\n${weather.sunrise}"
            textAnimation(sunrise)

            sunset?.text = "Sunset\n${weather.sunset}"
            textAnimation(sunset)

            wind?.text = "Wind speed\n${weather.windSpeed} m/s"
            textAnimation(wind)
        }
    }
    // check the connection
    fun isNetworkAvailable(context: Context): Boolean {
        var cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo: NetworkInfo? = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}