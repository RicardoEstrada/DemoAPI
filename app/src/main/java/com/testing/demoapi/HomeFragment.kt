package com.testing.demoapi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.testing.demoapi.databinding.FragmentHomeBinding
import com.testing.wsconnector.models.BeerData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment: Fragment() {

    private val model: HomeViewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        CoroutineScope(Dispatchers.Main).launch {
            binding.loader.isVisible = true
            binding.loader.animate()
            model.getBeers()
        }
    }

    private fun init() {
        model.beers.observe(viewLifecycleOwner, Observer {
            binding.loader.isVisible = false
            if (it.size > 0) {
                binding.layoutEmpty.contentEmpty.isVisible = false
                binding.recyclerList.layoutManager = LinearLayoutManager(this.context)
                binding.recyclerList.adapter = AdapterBeer(it) {
                    Toast.makeText(context, "${it.id}", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                binding.layoutEmpty.contentEmpty.isVisible = true
            }
        })
    }
}