package com.example.ecommerceproject.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecommerceproject.data.RegisterUser
import com.example.ecommerceproject.data.RegistrationResponse
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.ActivityRegisterBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject.retrofitUserEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var newUser : RegisterUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {

        val fullName = binding.etFullName.text.toString()
        val mobileNo = binding.etMobileNo.text.toString()
        val email = binding.etEmailId.text.toString()
        val password = binding.etPasswordRegister.text.toString()

         newUser = RegisterUser(fullName, mobileNo, email, password)

        val callRequest : Call<RegistrationResponse> = retrofitUserEntry.register(newUser)

        callRequest.enqueue(object : Callback<RegistrationResponse>{
            override fun onResponse(
                call: Call<RegistrationResponse>,
                response: Response<RegistrationResponse>
            ) {
                if (response.isSuccessful){
                    val builder = AlertDialog.Builder(this@RegisterActivity)
                        .setTitle("Success!")
                        .setMessage("click okay to login")
                        .setPositiveButton("ok"){
                                dialog, which ->
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            setResult(Activity.RESULT_OK,Intent())
                            finish()
                        }
                    val alertDialog = builder.create()
                    alertDialog.show()
                }
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


}