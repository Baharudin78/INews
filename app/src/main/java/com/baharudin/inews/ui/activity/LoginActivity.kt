package com.baharudin.inews.ui.activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.baharudin.inews.R
import com.baharudin.inews.databinding.ActivityLoginBinding
import com.baharudin.inews.utils.Constant.REQUEST_CODE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var mAuth : FirebaseAuth
    private lateinit var googleSignClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val googleSignIn = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignClient = GoogleSignIn.getClient(this, googleSignIn)

        mAuth = FirebaseAuth.getInstance()

        binding.btSignin.setOnClickListener {
            showProgressbar()
            signIn()
        }

    }
    private fun signIn() {
        val signInIntent = googleSignClient.signInIntent
        startActivityForResult(signInIntent,REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    hideProgressbar()
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in failed", e)
                }
            }else {
                Log.w(TAG, exception.toString())
            }

        }
    }
    private fun firebaseAuthWithGoogle(idToken : String) {
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("SignInMethoot", "SigninCredential : Success")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
    private fun showProgressbar() {
        binding.progresBar.visibility = View.VISIBLE
    }
    private fun hideProgressbar() {
        binding.progresBar.visibility = View.GONE
    }
}