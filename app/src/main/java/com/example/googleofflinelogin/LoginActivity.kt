package com.example.googleofflinelogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        val loginButton = findViewById<Button>(R.id.btnGoogleLogin)

        loginButton.setOnClickListener{
            this.login()
        }
    }

    private fun login() {
        val serverClientId = getString(R.string.server_client_id)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(serverClientId)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val googleSignInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(googleSignInIntent, RC_SIGN_IN)

        Toast.makeText(this, "Test Toast", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("Google_Sign_In_Data", data.toString())

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        if (requestCode == RC_SIGN_IN) {
            try {
                val account = task.getResult(ApiException::class.java)

                Log.d("Google_Sign_In_Ac", account.toString())
            } catch (ex: ApiException) {
                Log.e("Google_Sign_In_Ex", ex.toString())
            }
        }
    }

}
