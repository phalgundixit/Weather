package com.example.weather.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_display_weather.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import kotlinx.android.synthetic.main.layout_weather_additional_info.*
import kotlinx.android.synthetic.main.layout_weather_basic_info.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DisplayWeatherFragment : Fragment()  {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_display_weather, container, false)
        var city: String = arguments?.getString("city")?:"Delhi"

        if(isInternetAvailable(context)) {

            weatherViewModel.getCityData()
            weatherViewModel.setSearchQuery(city)
            weatherViewModel.weatherResponse.observe(viewLifecycleOwner, Observer { response ->

                output_group.visibility = View.VISIBLE
                tv_error_message.visibility = View.GONE

                tv_date_time?.text = response.weather[0].description
                tv_temperature?.text = (response.main.temp - 273.15).toInt().toString()
                tv_city_country?.text = response.name


                tv_humidity_value?.text = response.main.humidity.toString()
                tv_pressure_value?.text = response.wind.speed.toString()
                tv_visibility_value?.text = response.wind.deg.toString()


            })
        }else{
            weatherViewModel.getWeatherData.observe(viewLifecycleOwner, Observer { response->
                output_group.visibility = View.VISIBLE
                tv_error_message.visibility = View.GONE

                tv_date_time?.text = response[0].weather[0].description
                tv_temperature?.text = (response[0].main.temp - 273.15).toInt().toString()
                tv_city_country?.text = response[0].name


                tv_humidity_value?.text = response[0].main.humidity.toString()
                tv_pressure_value?.text = response[0].wind.speed.toString()
                tv_visibility_value?.text = response[0].wind.deg.toString()
            })
        }






        view.btn_refresh.setOnClickListener {
            weatherViewModel.getCityData()
        }
        view.btn_star.setOnClickListener {

            weatherViewModel.insertWeatherData(weatherViewModel.weatherResponse.value)
        }



        return view
    }

    fun isInternetAvailable(context: Context?): Boolean {
        var result = false
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DisplayWeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DisplayWeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}