package com.afflyas.pushover.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class HomeViewModel : ViewModel() {

    var apiToken: String = ""

    var userKey: String = ""

    var messageText: String = ""

    var deviceName: String? = null

    var timestamp: Long = 0

    val sendTimeStr = MutableLiveData<String>()

}
