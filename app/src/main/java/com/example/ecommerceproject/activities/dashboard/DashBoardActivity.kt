package com.example.ecommerceproject.activities.dashboard

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.R
import com.example.ecommerceproject.activities.LoginActivity
import com.example.ecommerceproject.activities.subcategory.SubCategoryActivity
import com.example.ecommerceproject.adapter.CategoryAdapter
import com.example.ecommerceproject.data.Category
import com.example.ecommerceproject.data.ProductCategoriesResponse
import com.example.ecommerceproject.data.User
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.DashboardLayoutActivityBinding
import com.example.ecommerceproject.factory.DashBoardViewModelFactory
import com.example.ecommerceproject.navigation_drawer.CartFragment
import com.example.ecommerceproject.navigation_drawer.DashBoardFragment
import com.example.ecommerceproject.navigation_drawer.OrdersFragment
import com.example.ecommerceproject.navigation_drawer.ProfileFragment
//import com.example.ecommerceproject.databinding.ActivityDashboardBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import com.example.ecommerceproject.viewmodel.DashBoardViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashBoardActivity : AppCompatActivity() {
    private lateinit var sPref : SharedPreferences
    private lateinit var binding: DashboardLayoutActivityBinding
    private lateinit var masterKeys : String
    private lateinit var currentUser : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardLayoutActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.beginTransaction().replace(R.id.container, DashBoardFragment()).commit()

        initializeSharedPreferences()


        binding.navView.setNavigationItemSelectedListener {
            handleNavigationOperation(it)
            true
        }
    }

    private fun initializeSharedPreferences() {
        masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        sPref = EncryptedSharedPreferences.create(
            "login_settings",
            masterKeys,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val currentUserId = sPref.getString("currentUserId", "")
        val currentUserEmail = sPref.getString("currentUserEmail","")
        val currentUserMobile = sPref.getString("currentUserMobile","")
        val currentUserName = sPref.getString("currentUserName","")
        if (currentUserEmail != null && currentUserId != null && currentUserName != null && currentUserMobile != null){
            currentUser = User(currentUserEmail,currentUserName,currentUserMobile,currentUserId)
            initializeNavigationHeader()
        }
        if (!currentUserId.equals("")) {
            currentUserId?.let {
                currentUser = EcommerceDatabase.getInstance(this)
                    .ecommerceDao
                    .getCurrentUser(it.toInt())
                //TODO: lateinit uninitialized error for currentUser
                // unsure why since thread should be blocking
            }
        }
    }

    private fun initializeNavigationHeader() {
        val headerView = binding.navView.getHeaderView(0)

            val userNameTextView = headerView.findViewById<TextView>(R.id.tv_user_name)
            userNameTextView.text = currentUser.full_name

            val userEmailTextView = headerView.findViewById<TextView>(R.id.tv_user_email)
            userEmailTextView.text = currentUser.email_id
    }

    private fun handleNavigationOperation(menuItem: MenuItem) {
        when(menuItem.itemId) {
            R.id.item_order -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, OrdersFragment()).commit()
            }
            R.id.item_cart -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CartFragment()).commit()
            }
            R.id.item_profile ->{
                supportFragmentManager.beginTransaction().replace(R.id.container, ProfileFragment()).commit()
            }
            R.id.item_logout ->{
                userLogout()
            }
            R.id.item_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.container,DashBoardFragment()).commit()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun userLogout() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes"){
                dialog, position ->
                startActivity(Intent(this, LoginActivity::class.java))
                removeUserPreference()
                finish()
            }
            .setNegativeButton("No"){
                dialog, position ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun removeUserPreference() {

        val editor = sPref.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        const val NUMBER_OF_ROWS = 2
    }

}