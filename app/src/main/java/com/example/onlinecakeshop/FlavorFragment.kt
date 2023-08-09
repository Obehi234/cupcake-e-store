package com.example.onlinecakeshop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.onlinecakeshop.databinding.FragmentFlavorBinding
import com.example.onlinecakeshop.viewModel.SharedViewModel

class FlavorFragment : Fragment() {
    private var binding: FragmentFlavorBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentFlavorBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.flavorOptions?.setOnCheckedChangeListener { _, checkedId ->
            val flavor = when (checkedId) {
                R.id.vanilla -> getString(R.string.Vanilla)
                R.id.chocolate -> getString(R.string.chocolate)
                R.id.red_velvet -> getString(R.string.red_velvet)
                R.id.salted_caramel -> getString(R.string.salted_caramel)
                R.id.coffee -> getString(R.string.coffee)
                else -> ""
            }
            sharedViewModel.setFlavor(flavor)
        }
        sharedViewModel.setPrice(sharedViewModel.quantity.value ?: 0)
        sharedViewModel.price.observe(viewLifecycleOwner) { formattedPrice ->
            binding?.subtotal?.text = formattedPrice
        }

        binding?.nextButton?.setOnClickListener { goToNextScreen() }
        binding?.cancelButton?.setOnClickListener { cancelOrder() }
    }

    private fun goToNextScreen() {
        findNavController().navigate(R.id.action_flavorFragment_to_pickUpFragment)
    }

    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_flavorFragment_to_startFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}


