package com.example.ecommerceproject.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.activities.dashboard.DashBoardActivity
import com.example.ecommerceproject.data.LoginResponse
import com.example.ecommerceproject.data.LoginUser
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.ActivityLoginBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var sPref : SharedPreferences
    private lateinit var registerForActivityResult : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        sPref = EncryptedSharedPreferences.create(
            "login_settings",
            masterKey,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        checkLogin()

        registerForActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if (result.resultCode == Activity.RESULT_OK){
                //do nothing
            }
        }


        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnRegisterNavigate.setOnClickListener {
            registerForActivityResult.launch(Intent(this,RegisterActivity::class.java))
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val loginUser = LoginUser(email, password)
        val callRequest = EcommerceApiAccessObject.retrofitUserEntry.login(loginUser)
        callRequest.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){

                    response.body()?.user?.let {
                        EcommerceDatabase.getInstance(this@LoginActivity).ecommerceDao.saveUser(it)
                    }

                    val edit = sPref.edit()

                    edit.putString("currentUserId","${response.body()?.user?.user_id}")
                    edit.putString("currentUserEmail","${response.body()?.user?.email_id}")
                    edit.putString("currentUserName","${response.body()?.user?.full_name}")
                    edit.putString("currentUserMobile","${response.body()?.user?.mobile_no}")


                    edit.apply()

                    Toast.makeText(this@LoginActivity,"Success Login!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@LoginActivity, DashBoardActivity::class.java))
                    finish()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
    private fun checkLogin(){
        //shared preference will update/track the current user id when user logs in or
        //returns to an already logged in application
        if (!sPref.getString("currentUserId", "-1").equals("-1")) {
            //note: Careful where you're calling this, as getActivity() will return null if
            //the fragment has been detached
            startActivity(Intent(this, DashBoardActivity::class.java))
        }
    }
}