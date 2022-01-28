package com.example.apiapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.apiapplication.databinding.ActivitySignUpBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

private var client = OkHttpClient()
private lateinit var request: Request
private lateinit var formBody: FormBody
private lateinit var gson: Gson
private lateinit var binding: ActivitySignUpBinding
class Sign_up : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = "http://cinema.areas.su/auth/register"
        binding.btnRegister.setOnClickListener {
            var email = binding.Email.text.toString()
            var password = binding.Password.text.toString()
            var firstName = binding.fistName.text.toString()
            var lastName = binding.lastName.text.toString()
            formBody = FormBody.Builder()
                .add("email", email)
                .add("firstName", firstName)
                .add("password", password)
                .add("lastName", lastName)
                .build()

            request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object: Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.i("tag", "onFailure: ")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.code == 200) {
                        runOnUiThread {
                            Toast.makeText(this, "Успешная регистрация", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            })
        }


        }
    }
