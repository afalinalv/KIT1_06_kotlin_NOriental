package com.example.kit1_06_kotlin_noriental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*  // этот импорт дает доступ к полям Layout activity_main.xml
import android.widget.Toast
import android.R.attr.orientation
import android.content.res.Configuration
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText




class MainActivity : AppCompatActivity() {


    val ORIENTATION_PORTRAIT = "Портретный режим"
    val ORIENTATION_LANDSCAPE = "Альбомный режим"

    // определяем изменение ориентации экрана
    var mState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // установим текст по умолчанию

        button.text = ORIENTATION_LANDSCAPE

        // requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //для альбомного режима
        // или
        // setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //для портретного режима
        // Но указанный способ не совсем желателен.
        // Лучше установить нужную ориентацию через манифест,
        // прописав в элементе <activity> параметр android:screenOrientation:
        // android:screenOrientation="portrait"
        // android:screenOrientation="landscape"

        // Кстати, существует ещё один вариант,
        // когда устройство полагается на показания сенсора и некоторые другие:
        // android:screenOrientation="sensor"
        /*
        В Android 4.3 (API 18) появились новые значения (оставлю пока без перевода):

userLandscape - Behaves the same as "sensorLandscape", except if the user disables auto-rotate
    then it locks in the normal landscape orientation and will not flip.
userPortrait - Behaves the same as "sensorPortrait", except if the user disables auto-rotate
    then it locks in the normal portrait orientation and will not flip.
fullUser - Behaves the same as "fullSensor" and allows rotation in all four directions,
    except if the user disables auto-rotate then it locks in the user's preferred orientation.
locked - to lock your app's orientation into the screen's current orientation.

После появления Android 5.0 зашёл на страницу документации и пришёл в ужас.
 Там появились новые значения.
android:screenOrientation=["unspecified" | "behind" |
    "landscape" | "portrait" |
    "reverseLandscape" | "reversePortrait" |
    "sensorLandscape" | "sensorPortrait" |
    "userLandscape" | "userPortrait" |
    "sensor" | "fullSensor" | "nosensor" |
    "user" | "fullUser" | "locked"]
         */

        editTest.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Toast.makeText(this@MainActivity,
                    "onTextChanged: $s", Toast.LENGTH_SHORT).show()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

    }
    //  Запрет на создание новой активности
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Проверяем ориентацию экрана
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        // в манифест: android:configChanges="keyboardHidden|orientation|screenSize"
        // В документации говорится, что данный способ следует избегать.
    }

    fun onClick(view: View) {
        // state FALSE: переключаемся на LANDSCAPE
        if (!mState) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            button.text =ORIENTATION_PORTRAIT
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            button.text =ORIENTATION_LANDSCAPE
        }// state TRUE: переключаемся на PORTRAIT
        // обновляем state на противоположное значение
        mState = !mState
    }

    // Исчезающий текст:
    // как уже говорилось, при смене ориентации активность пересоздаётся.
    // При этом можно наблюдать интересный эффект с пропадающим текстом.
    // Чтобы увидеть эффект, создадим два текстовых поля.
    // Одному из них присвоим идентификатор, а другое поле оставим без него.
    /* Запустите приложение, введите любой текст в обоих полях и смените ориентацию.
     Вы увидите, что у поля с идентификатором текст при повороте сохранится,
      а у поля без идентификатора текст исчезнет. Учитывайте данное обстоятельство.
    К вышесказанному могу добавить,
     что при смене ориентации у поля с идентификатором
      вызывается метод onTextChanged():
    */

}
/* запоминание текста см VORONA

Жизненный цикл при повороте
При повороте активность проходит через цепочку различных состояний. Порядок следующий.

onPause()
onStop()
onDestroy()
onCreate()
onStart()
onResume()
 */