package com.example.projemanag.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.projemanag.R
import com.example.projemanag.firebase.FirestoreClass
import com.example.projemanag.models.User
import com.example.projemanag.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.btn_sign_up
import kotlinx.android.synthetic.main.activity_sign_up.et_email
import kotlinx.android.synthetic.main.activity_sign_up.et_name
import kotlinx.android.synthetic.main.activity_sign_up.et_password
import kotlinx.android.synthetic.main.activity_sign_up.toolbar_sign_up_activity

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setUpActionBar()
    }

    fun userRegisteredSuccess() {
        Toast.makeText(
            this, "you have " +
                    "successfully registered", Toast.LENGTH_LONG
        ).show()
        hideProgressDialog()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar_sign_up_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }
        btn_sign_up.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }
        if (validateForm(name, email, password)) {
            Utils.countingIdlingResource.increment()
            showProgressDialog(resources.getString(R.string.please_wait))
            Log.d("Progress Dialog", "registerUser")
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        val user = User(firebaseUser.uid, name, registeredEmail)
                        FirestoreClass().registerUser(this, user)
                        Utils.countingIdlingResource.decrement()
                    } else {
                        hideProgressDialog()
                        Toast.makeText(
                            this,
                            "Registration failed", Toast.LENGTH_LONG
                        ).show()
                        Utils.countingIdlingResource.decrement()
                    }
                }
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter an email address")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> {
                true
            }
        }
    }
}
