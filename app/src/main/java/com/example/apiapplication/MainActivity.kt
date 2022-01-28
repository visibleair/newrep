package com.example.apiapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.apiapplication.data.Login
import com.example.apiapplication.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

private var client = OkHttpClient()
private lateinit var request: Request
private lateinit var gson: Gson
private lateinit var formBody: FormBody
private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = "http://cinema.areas.su/auth/login"
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, Sign_up::class.java))
        }
        binding.btnLogin.setOnClickListener {
            var email = binding.Email.text.toString()
            var password = binding.Password.text.toString()
            formBody = FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build()
            request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.i("tag", "onFailure: ")
                }

                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        // Log.i("tag", "onResponse: ${response.body!!.string()}")
                        gsonBuilder(response.body!!.string())
                    }
                }

            })
            gson = Gson()
        }



    }
    private lateinit var token: Login
    private fun gsonBuilder(str: String){
        token = gson.fromJson(str, Login::class.java)
        Log.i("tag", "gsonBuilder: ${token.token}")
    }
}