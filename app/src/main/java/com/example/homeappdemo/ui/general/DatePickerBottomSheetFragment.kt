package com.example.homeappdemo.ui.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.homeappdemo.databinding.FragmentDatePickerBottomSheetBinding
import com.example.homeappdemo.util.SingleLiveEvent
import com.example.homeappdemo.util.extensions.DateType
import com.example.homeappdemo.util.extensions.DefaultDateConfiguration
import com.example.homeappdemo.util.extensions.getDate
import com.example.homeappdemo.util.extensions.setToTodayDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DatePickerBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDatePickerBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val args: DatePickerBottomSheetFragmentArgs by navArgs()
    private val dateFormat = DefaultDateConfiguration.defaultDateFormat
    private val dateType = DefaultDateConfiguration.dateType
    private val date by lazy { args.date }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDatePickerBottomSheetBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        when (dateType) {
            DateType.MIN_DATE -> dateType.let { binding.datePicker.minDate = date }
            DateType.MAX_DATE -> dateType.let { binding.datePicker.maxDate = date }
        }

        binding.buttonContinue.setOnClickListener {
            onDatePicked.value = binding.datePicker.getDate(dateFormat)
            dialog?.dismiss()
        }

        binding.datePicker.setToTodayDate()
    }

    companion object {
        val onDatePicked = SingleLiveEvent<String>()
    }
}
