package com.example.homeappdemo.ui.fragments.device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.homeappdemo.databinding.FragmentChooseProductTypeDialogBinding

class ChooseProductTypeDialogFragment : DialogFragment() {
    private var _binding: FragmentChooseProductTypeDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        _binding = FragmentChooseProductTypeDialogBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.hContainer.setOnClickListener {
            val action =
                ChooseProductTypeDialogFragmentDirections.navigateToCreateDeviceFragment(productType = "Heater")
            findNavController().navigate(action)
            dialog?.dismiss()
        }

        binding.lContainer.setOnClickListener {
            val action =
                ChooseProductTypeDialogFragmentDirections.navigateToCreateDeviceFragment(productType = "Light")
            findNavController().navigate(action)
            dialog?.dismiss()
        }

        binding.rContainer.setOnClickListener {
            val action =
                ChooseProductTypeDialogFragmentDirections.navigateToCreateDeviceFragment(productType = "RollerShutter")
            findNavController().navigate(action)
            dialog?.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

    }
}