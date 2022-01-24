package space.konovalova.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

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

        getDataURL()
        dateTime()

        buttonUpdate?.setOnClickListener {
            getDataURL()
            dateTime()
            buttonAnimation(buttonUpdate)
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun dateTime(){
        val dateObj = Date()
        val formatForTime = SimpleDateFormat("k:mm ")
        val formatForDate = SimpleDateFormat("dd.MM.yyyy")
        
        dateNow?.text = "Последнее обновление: " + formatForTime.format(dateObj)
        updateInfo?.text = formatForDate.format(dateObj)
    }

    private fun textAnimation(textView: TextView?){
        var anim: Animation? = null
        anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        textView?.startAnimation(anim)
    }

    private fun buttonAnimation(imageButton: ImageButton?){
        var anim: Animation? = null
        anim = AnimationUtils.loadAnimation(this, R.anim.rotate)
        imageButton?.startAnimation(anim)
    }

    @SuppressLint("SetTextI18n")
    private fun getDataURL (){
        doAsync {

            /*val dotenv = dotenv {
                directory = "/assets"
                filename = "env.env"
            }
            dotenv["apikey"]*/
            //val key = dotenv.get("apikey")

            val key = "9d5086635e15fe649921a5746f53a41f"

            val city = "Nizhny Novgorod"
            val url =
                "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"
            val apiResponse = URL(url).readText()

            val main = JSONObject(apiResponse).getJSONObject("main")
            val temp = main.getDouble("temp").roundToInt()
            resultInfo?.text = "$temp ℃"
            //resultInfo?.text = dotenv["apikey"]
            textAnimation(resultInfo)

            val tempMin = main.getDouble("temp_min").roundToInt()
            min?.text = "Минимальная температура\n$tempMin ℃"
            textAnimation(min)

            val tempMax = main.getDouble("temp_max").roundToInt()
            max?.text = "Максимальная температура\n$tempMax ℃"
            textAnimation(max)

            val sys = JSONObject(apiResponse).getJSONObject("sys")
            val sunriseT = sys.getInt("sunrise")
            val sunriseTime = dateTime(sunriseT, 3)
            sunrise?.text = "Восход\n$sunriseTime"
            textAnimation(sunrise)

            val sunsetT = sys.getInt("sunset")
            val sunsetTime = dateTime(sunsetT, 3)
            sunset?.text = "Закат\n$sunsetTime"
            textAnimation(sunset)

            val windS = JSONObject(apiResponse).getJSONObject("wind")
            val windSpeed = windS.getDouble("speed")
            wind?.text = "Скорость ветра\n$windSpeed м/с"
            textAnimation(wind)
        }
    }

    @SuppressLint("NewApi")
    fun dateTime(time: Int, offset: Int, format: String = "k:mm"): String {
        // parse the time zone
        val zoneOffset = ZoneOffset.ofHours(offset)
        // create a moment in time from the given timestamp (in seconds!)
        val instant = Instant.ofEpochSecond(time.toLong())
        // define a formatter using the given pattern and a Locale
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
        // then make the moment in time consider the zone and return the formatted String
        return instant.atOffset(zoneOffset).format(formatter)
    }
}