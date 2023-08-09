package com.example.onlinecakeshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.onlinecakeshop.databinding.FragmentStartBinding
import com.example.onlinecakeshop.viewModel.SharedViewModel


class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        fragmentBinding.orderOneCupcake.setOnClickListener { orderCupcake(1) }
        fragmentBinding.orderSixCupcakes.setOnClickListener { orderCupcake(6) }
        fragmentBinding.orderTwelveCupcakes.setOnClickListener { orderCupcake(12) }
        return fragmentBinding.root
    }

    private fun orderCupcake(quantity: Int) {
        sharedViewModel.orderCupcakes(quantity)
        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}