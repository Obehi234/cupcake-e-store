package com.example.onlinecakeshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.onlinecakeshop.databinding.FragmentSummaryBinding
import com.example.onlinecakeshop.viewModel.SharedViewModel

class SummaryFragment : Fragment() {
    private var binding: FragmentSummaryBinding ? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.apply{
            quantity.observe(viewLifecycleOwner){quantity ->
                binding?.quantity?.text = quantity.toString()
            }
            flavor.observe(viewLifecycleOwner) {flavor ->
                binding?.flavor?.text = flavor
            }
            date.observe(viewLifecycleOwner) {date ->
                binding?.date?.text = date
            }
            price.observe(viewLifecycleOwner) {price ->
                binding?.total?.text = price
            }
        }

        binding?.cancelButton?.setOnClickListener { cancelOrder() }
        binding?.sendButton?.setOnClickListener {
            Toast.makeText(requireContext(), "Order successfully completed", Toast.LENGTH_LONG).show()
        }
    }

    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_summaryFragment_to_startFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}