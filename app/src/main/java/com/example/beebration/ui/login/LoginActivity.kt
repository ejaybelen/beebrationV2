package com.example.beebration.ui.login

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.beebration.MainActivity
import com.example.beebration.R
import com.example.beebration.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this , gso)
//
//        findViewById<Button>(R.id.gSignInBtn).setOnClickListener {
//            signInGoogle()
//        }

        binding.btnLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.forgotPass.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view =  layoutInflater.inflate(R.layout.dialog_forgot, null)
            val email = view.findViewById<TextInputEditText>(R.id.userEmail)

            builder.setView(view)
            val dialog = builder.create()

            view.findViewById<Button>(R.id.btnReset).setOnClickListener {
                compareEmail(email)
                dialog.dismiss()
            }

            view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            if (dialog.window != null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()
        }
    }

    private fun compareEmail(email: TextInputEditText){
        if (email.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun signInGoogle(){
//        val signInIntent = googleSignInClient.signInIntent
//        launcher.launch(signInIntent)
//    }

//    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            result ->
//        if (result.resultCode == Activity.RESULT_OK){
//
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            handleResults(task)
//        }
//    }

//    private fun handleResults(task: Task<GoogleSignInAccount>) {
//        if (task.isSuccessful){
//            val account : GoogleSignInAccount? = task.result
//            if (account != null){
//                updateUI(account)
//            }
//        }else{
//            Toast.makeText(this, task.exception.toString() , Toast.LENGTH_SHORT).show()
//        }
//    }

//    private fun updateUI(account: GoogleSignInAccount) {
//        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
//            if (it.isSuccessful){
//                val intent : Intent = Intent(this , MainActivity::class.java)
//                intent.putExtra("email" , account.email)
//                intent.putExtra("name" , account.displayName)
//                startActivity(intent)
//            }else{
//                Toast.makeText(this, it.exception.toString() , Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }
}