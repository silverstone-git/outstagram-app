package com.cyto.outstagram.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cyto.outstagram.R
import com.cyto.outstagram.databinding.FragmentHomeBinding
import com.cyto.outstagram.ui.home.model.HomeAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        recyclerView = binding.homeRecyclerView
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HomeAdapter(emptyList())
        recyclerView.adapter = adapter

        // Observe the LiveData from the ViewModel
        homeViewModel.homeItems.observe(viewLifecycleOwner, Observer { items ->
            // Update the adapter with the new data
            adapter.updateData(items)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}