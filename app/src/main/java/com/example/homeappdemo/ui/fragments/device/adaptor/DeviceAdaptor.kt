package com.example.homeappdemo.ui.fragments.device.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeappdemo.R
import com.example.homeappdemo.databinding.DeviceItemLayoutBinding
import com.example.homeappdemo.model.device.DeviceModel
import com.example.homeappdemo.model.device.ProductType
import com.example.homeappdemo.util.AdaptorCallback
import com.example.homeappdemo.util.extensions.hide

class DeviceAdaptor(
    private val adaptorCallback: AdaptorCallback<DeviceModel>,
    private val listOfDevices: MutableList<DeviceModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val binding =
            DeviceItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, adaptorCallback)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listOfDevices[position]
        when (holder) {
            is ViewHolder -> item.let { holder.bind(it) }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        return listOfDevices.size
    }

    fun onNewData(newData: List<DeviceModel>) {
        listOfDevices.clear()
        listOfDevices.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        view: DeviceItemLayoutBinding,
        adaptorCallback: AdaptorCallback<DeviceModel>
    ) :
        RecyclerView.ViewHolder(view.root) {
        private val listener = adaptorCallback
        private var itemBinding: DeviceItemLayoutBinding = view

        private val text: TextView = itemBinding.txtDeviceName
        private val deviceDetail1: TextView = itemBinding.txtDetail1
        private val deviceDetail2: TextView = itemBinding.txtDetail2
        private val icon: ImageView = itemBinding.ivIcon
        private val statusIcon: ImageView = itemBinding.ivIconStatus

        private val container: ViewGroup = itemBinding.container

        fun bind(device: DeviceModel) {

            text.text = device.deviceName

            if (device.mode != "OFF" && !device.mode.isNullOrEmpty()) statusIcon.setImageResource(R.drawable.shade)
            container.setOnClickListener {
                listener.onClicked(device)
            }

            when ((device.getProductTypeCase())) {
                ProductType.LIGHTS -> setLightDetails(device)
                ProductType.ROLLER_SHUTTER -> setShutterDetails(device)
                ProductType.HEATER -> setHeaterDetails(device)
                ProductType.NULL -> {
                }
            }
        }

        private fun setLightDetails(device: DeviceModel?) {
            deviceDetail1.text = context.getString(R.string.intensity) + " ${device?.intensity}"
            deviceDetail2.text = context.getString(R.string.mode) + " " + device?.mode
            icon.setImageResource(R.drawable.light_ic)
        }

        private fun setShutterDetails(device: DeviceModel?) {
            deviceDetail1.text = context.getString(R.string.position) + " ${device?.position}"
            deviceDetail2.hide()
            statusIcon.hide()
            icon.setImageResource(R.drawable.shutter_ic)
        }

        private fun setHeaterDetails(device: DeviceModel?) {
            deviceDetail1.text =
                context.getString(R.string.temperature) + " ${device?.temperature}°"
            deviceDetail2.text = context.getString(R.string.mode) + " " + device?.mode
            icon.setImageResource(R.drawable.heater_ic)
        }
    }
}