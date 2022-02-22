package com.example.homeappdemo.datasource.local.database

import androidx.room.TypeConverter
import com.example.homeappdemo.model.user.Address
import org.json.JSONObject

object AddressConverter {
    @TypeConverter
    fun fromAddress(address: Address): String {
        return JSONObject().apply {
            put("city", address.city)
            put("country", address.country)
            put("postalCode", address.postalCode)
            put("streetCode", address.streetCode)
            put("street", address.street)
        }.toString()
    }

    @TypeConverter
    fun toSource(source: String): Address {
        val json = JSONObject(source)
        return Address(
            city = json.getString("city"),
            country = json.getString("country"),
            postalCode = json.getInt("postalCode"),
            streetCode = json.getString("streetCode"),
            street = json.getString("street")
        )
    }

}