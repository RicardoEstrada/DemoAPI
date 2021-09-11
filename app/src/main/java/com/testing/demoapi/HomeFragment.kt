package com.testing.demoapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.testing.demoapi.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment: Fragment() {

    private val model: HomeViewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        CoroutineScope(Dispatchers.Main).launch {
            binding.loader.isVisible = true
            binding.loader.animate()
            model.getNextBeers()
        }
    }

    private fun initAdapter() {
        binding.recyclerList.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        binding.recyclerList.adapter = AdapterBeer(ArrayList()) {
            Toast.makeText(context, it.id, Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        binding.layoutEmpty.contentEmpty.isVisible = false

        model.beers.observe(viewLifecycleOwner, { list ->
            binding.loader.isVisible = false

            if (model.wasFirstPage() && list.isEmpty()) {
                binding.layoutEmpty.contentEmpty.isVisible = true
            }

            binding.recyclerList.adapter?.let {
                (binding.recyclerList.adapter as? AdapterBeer)?.updateList(list)
            } ?: initAdapter()
        })
    }

    fun addScrollListener(mScrollView: NestedScrollView) {
        mScrollView.viewTreeObserver.addOnScrollChangedListener {
            val view = mScrollView.getChildAt(mScrollView.childCount - 1) as View

            val diff: Int = view.bottom - (mScrollView.height + mScrollView
                .scrollY)

            if (diff == 0) {
                CoroutineScope(Dispatchers.Default).launch {
                    if (model.canRequest()) {
                        model.getNextBeers()
                    }
                }
            }
        }
    }

}