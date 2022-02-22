package com.example.homeappdemo.ui.fragments.settings.adaptor


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeappdemo.R
import com.example.homeappdemo.util.AdaptorCallback
import com.example.homeappdemo.databinding.SettingsItemLayoutBinding


class SettingsAdaptor(private val adaptorCallback: AdaptorCallback<SettingsItem>, private val context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    data class SettingsItem(
        val itemPosition: Int,
        val itemName:String
    )

    private val listOfEvents =  listOf(
        SettingsItem(itemPosition = 0, itemName = context.getString(R.string.delete_my_account)),
        SettingsItem(itemPosition = 1, itemName = context.getString(R.string.delete_my_devices)),
        SettingsItem(itemPosition = 2, itemName = context.getString(R.string.clear_data)),
        SettingsItem(itemPosition = 3, itemName = context.getString(R.string.change_language))

    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = SettingsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, adaptorCallback)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listOfEvents[position]
        when(holder){
            is ViewHolder -> item.let { holder.bind(it) }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        return listOfEvents.size
    }


    inner class ViewHolder(
        view: SettingsItemLayoutBinding,
        adaptorCallback: AdaptorCallback<SettingsItem>
    ) :
        RecyclerView.ViewHolder(view.root)  {
        private val listener = adaptorCallback
        private var itemBinding: SettingsItemLayoutBinding = view

        private val text : TextView = itemBinding.txtSettingsItem
        private val container : ViewGroup = itemBinding.container

        fun bind(item: SettingsItem) {
            text.text = item.itemName

            container.setOnClickListener{
                listener.onClicked(item)
            }
        }
    }


}