package com.example.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.get
import kotlinx.android.synthetic.main.fragment_first.*
import org.json.JSONException
import org.json.JSONObject


class FirstFragment : Fragment(R.layout.fragment_first) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listOfCountries = MainActivity.countriesJson
        fillList(listOfCountries,"countries")

    }

    private fun fillList(json:JSONObject,objName:String){
        val wholeList = getArrays(json,objName)
        val stringList = arrayOfNulls<String>(wholeList.size)
        for (i in 0 until wholeList.size){
            val refill = wholeList[i]
            stringList[i] = refill.name
        }
        val adapter = ArrayAdapter(context!!,android.R.layout.simple_list_item_1,stringList)
        list_view1.adapter = adapter
        list_view1.setOnItemClickListener{
                parent, view, position, id ->
            val element = adapter.getItem(position)
            val secondFragment = SecondFragment()
//            secondFragment.arguments = parent.get(position)
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_main,secondFragment)
            transaction?.addToBackStack(null)
            transaction?.commit()

        }
    }

    private fun getArrays(json:JSONObject,objName:String):ArrayList<Country>{
        val refillsList = ArrayList<Country>()

        try {
            val refills = json.getJSONArray(objName)
            (0 until refills.length()).mapTo(refillsList){
                Country(
                    refills.getJSONObject(it).getString("name"),
                    refills.getJSONObject(it).getString("code")


                )
            }
        }catch (e:JSONException){
            e.printStackTrace()
        }

        return refillsList
    }



}