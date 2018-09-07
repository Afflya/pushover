package com.afflyas.pushover.ui

import androidx.lifecycle.ViewModel;

class HomeViewModel : ViewModel() {

    var apiToken = ""

    var userKey = ""

    var messageText = ""

    var deviceName: String? = null

}
