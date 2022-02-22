package com.example.homeappdemo.sharedpreference

import android.content.Context
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalStorage {
    @Inject
    constructor(context: Context) {
        Paper.init(context)
        this.context = context
    }

    private lateinit var context: Context

    companion object {

        val HAS_ACCOUNT = "HAS_ACCOUNT"
        val HAS_DEVICE = "HAS_DEVICE"
        val USER_NAME = "USERNAME"
    }

}

fun LocalStorage.savePref(key: String, value: Any) {
    when (value) {
        is String -> {
            key.saveStringKey(value)
        }
        is Boolean -> {
            key.saveBooleanKey(value)
        }
    }
}


fun LocalStorage.getStringPref(key: String): String {
    return key.getStringKey(key)
}

fun LocalStorage.getBooleanKey(key: String, defaultValue: Boolean): Boolean {
    return key.getBooleanKey(defaultValue)
}

private fun String.saveStringKey(value: String) = saveStringToKey(this, value)
private fun saveStringToKey(key: String, value: String) {
    runBlocking {
        withContext(Dispatchers.IO) {
            Paper.book().write(key, value)
        }
    }
}

fun String.getStringKey(default: String): String = getStringFromPref(this, default)
private fun getStringFromPref(key: String, deafult: String): String {
    return runBlocking {
        async(Dispatchers.IO) {
            Paper.book().read(key, deafult)
        }.await()
    }
}

private fun String.saveBooleanKey(value: Boolean) = saveBooleanToKey(this, value)
private fun saveBooleanToKey(key: String, value: Boolean) {
    runBlocking {
        withContext(Dispatchers.IO) {
            Paper.book().write(key, value)
        }
    }
}

fun String.getBooleanKey(default: Boolean): Boolean = getBooleanFromKey(this, default)
private fun getBooleanFromKey(key: String, defaultvalue: Boolean): Boolean {
    return runBlocking {
        async(Dispatchers.IO) {
            Paper.book().read(key, defaultvalue)
        }.await()
    }
}


fun LocalStorage.getUserName(): String {
    return getStringFromPref(LocalStorage.USER_NAME, "")
}

fun LocalStorage.setUserName(firstName: String, lastName: String) {
    saveStringToKey(LocalStorage.USER_NAME, "$firstName $lastName")
}

fun LocalStorage.checkAccountStatus(): Boolean {
    return getBooleanKey(LocalStorage.HAS_ACCOUNT, false)
}

fun LocalStorage.setAccountStatus(status: Boolean) {
    saveBooleanToKey(LocalStorage.HAS_ACCOUNT, status)
}

fun LocalStorage.checkDeviceStatus(): Boolean {
    return getBooleanKey(LocalStorage.HAS_DEVICE, false)
}

fun LocalStorage.setDeviceStatus(status: Boolean) {
    saveBooleanToKey(LocalStorage.HAS_DEVICE, status)
}
