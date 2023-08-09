package com.example.onlinecakeshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.onlinecakeshop.databinding.FragmentPickUpBinding
import com.example.onlinecakeshop.viewModel.SharedViewModel

class PickUpFragment : Fragment() {

    private var binding: FragmentPickUpBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentPickUpBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.dateOptions?.setOnCheckedChangeListener { _, checkedId ->
            val date = when (checkedId) {
                R.id.Thursday -> getString(R.string.Thursday)
                R.id.Friday -> getString(R.string.Friday)
                R.id.Saturday -> getString(R.string.Saturday)
                R.id.Sunday -> getString(R.string.Sunday)
                else -> ""
            }
            sharedViewModel.setDate(date)
            sharedViewModel.updatePrice()
        }

        sharedViewModel.price.observe(viewLifecycleOwner) { newPrice ->
            binding?.subtotal?.text = newPrice
        }
        binding?.nextButton?.setOnClickListener { goToNextScreen() }
        binding?.cancelButton?.setOnClickListener { cancelOrder() }
    }

    private fun goToNextScreen() {
        findNavController().navigate(R.id.action_pickUpFragment_to_summaryFragment)
    }

    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_pickUpFragment_to_startFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}