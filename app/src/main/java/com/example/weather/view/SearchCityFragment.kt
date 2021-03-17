package com.example.weather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.adapter.RecentSearchAdapter
import com.example.weather.model.City
import com.example.weather.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_city.*
import kotlinx.android.synthetic.main.fragment_search_city.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchCityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SearchCityFragment : Fragment() {
    private lateinit var recentSearchAdapter: RecentSearchAdapter
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
       val view = inflater.inflate(R.layout.fragment_search_city, container, false)
        view.btn_search.setOnClickListener {
            val bundle = bundleOf("city" to edit_text_search.text.toString())
            Navigation.findNavController(view).navigate(R.id.action_searchCityFragment_to_displayWeatherFragment,bundle)
        }


        weatherViewModel.getWeatherData.observe(viewLifecycleOwner, Observer { response->
            initRecyclerView(response as ArrayList<City>)
        })
        return view
    }

    private fun initRecyclerView(data:ArrayList<City>) {
        recentSearchAdapter= RecentSearchAdapter(data)
        recently_searched_recycler_view?.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter=recentSearchAdapter
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchCityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchCityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}