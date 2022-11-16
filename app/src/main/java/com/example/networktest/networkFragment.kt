package com.example.networktest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networktest.databinding.FragmentNetworkBinding

public class networkFragment: Fragment() {
    /*private lateinit var binding: FragmentNetworkBinding
    val viewModel by lazy { ViewModelProvider(this).get(NetworkViewModel::class.java) }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_network,container,false)
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentNetworkBinding.inflate(layoutInflater)
        val layoutManager =  LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
       // adapter =
    }

    */
}