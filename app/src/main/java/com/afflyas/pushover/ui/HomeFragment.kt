package com.afflyas.pushover.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.afflyas.pushover.R
import com.afflyas.pushover.api.PushResponse
import com.afflyas.pushover.api.PushoverApiService
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    lateinit var tokenEditText: EditText
    lateinit var userKeyEditText: EditText
    lateinit var messageEditText: EditText
    lateinit var deviceEditText: EditText

    private val apiService = Retrofit.Builder()
            .baseUrl(PushoverApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PushoverApiService::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        tokenEditText = v.tokenEditText
        userKeyEditText = v.userKeyEditText
        messageEditText = v.messageEditText
        deviceEditText = v.deviceEditText

        v.sendButton.setOnClickListener {
            pushMessage()
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        if(viewModel.apiToken.isEmpty()){
            viewModel.apiToken = PushoverApiService.APP_API_TOKEN
            tokenEditText.setText(PushoverApiService.APP_API_TOKEN, TextView.BufferType.EDITABLE)
        }

        if(viewModel.userKey.isEmpty()){
            viewModel.userKey = PushoverApiService.MY_USER_KEY
            userKeyEditText.setText(PushoverApiService.MY_USER_KEY, TextView.BufferType.EDITABLE)
        }

        if(viewModel.deviceName == null){
            viewModel.deviceName = PushoverApiService.MY_DEVICE
            deviceEditText.setText(PushoverApiService.MY_DEVICE, TextView.BufferType.EDITABLE)
        }

        subscribeEditText()
    }

    private fun subscribeEditText(){

        tokenEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.apiToken = s.toString()
            }
        })

        userKeyEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.userKey = s.toString()
            }
        })

        messageEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.messageText = s.toString()
            }
        })

        deviceEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.deviceName = s.toString()
            }
        })
    }


    private fun pushMessage(){

        if(viewModel.apiToken.isEmpty()){
            Toast.makeText(context, "Enter api token", Toast.LENGTH_SHORT).show()
            return
        }

        if(viewModel.userKey.isEmpty()){
            Toast.makeText(context, "Enter user key", Toast.LENGTH_SHORT).show()
            return
        }

        if(viewModel.messageText.isEmpty()){
            Toast.makeText(context, "Enter message", Toast.LENGTH_SHORT).show()
            return
        }

        apiService.pushMessage(
                viewModel.apiToken,
                viewModel.userKey,
                viewModel.deviceName!!,
                viewModel.messageText
        ).enqueue(object : Callback<PushResponse>{
            override fun onFailure(call: Call<PushResponse>, t: Throwable) {
                Toast.makeText(context, "Push failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PushResponse>, response: Response<PushResponse>) {
                if (response.isSuccessful) {
                    val responseStatus = response.body()?.status
                    if(responseStatus == 1){
                        Toast.makeText(context, "Push succeed", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Push failed", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, "Push failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}
