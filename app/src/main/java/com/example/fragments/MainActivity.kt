package com.example.fragments

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_main, firstFragment)
            addToBackStack(null)
            commit()

        }
        countriesJson  = getRefillsFromFile("data.json",this)



    }
    private fun getRefillsFromFile(fileName:String,context: Context): JSONObject {
        lateinit var refillsJSON: JSONObject

        try {

            val jsonString = loadJsonFromAssets(fileName,context)
            val json = JSONObject(jsonString)
            refillsJSON = json

        }catch (e: JSONException){
            e.printStackTrace()
        }

        return refillsJSON
    }

    private fun loadJsonFromAssets(fileName: String,context: Context): String?{

        var json: String? = null
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)

        }catch (ex:java.io.IOException){
            ex.printStackTrace()
            return null
        }
        return json
    }

    companion object{
        lateinit var countriesJson: JSONObject

    }
}