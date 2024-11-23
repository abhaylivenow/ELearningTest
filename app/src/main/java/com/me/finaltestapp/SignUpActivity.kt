package com.me.finaltestapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.me.finaltestapp.ui.theme.FinalTestAppTheme

class SignUpActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalTestAppTheme {
                auth = FirebaseAuth.getInstance()
                SignUpScreen(auth)
            }
        }
    }
}

@Composable
fun SignUpScreen(auth: FirebaseAuth) {
    val context = LocalContext.current
    var actualName by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        StyledTextField(value = actualName, onValueChange = {
            actualName = it
        }, "Name")

        StyledTextField(value = username, onValueChange = {
            username = it
        }, "Username")

        StyledTextField(value = password, onValueChange = {
            password = it
        }, "Password")

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                createUser(auth, username, password,
                    onSuccessful = {
                        context.startActivity(
                            Intent(context, HomeActivity::class.java)
                        )
                        (context as Activity).finish()
                    },
                    onFailed = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    })
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 50.dp, end = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFff66ff),
                contentColor = Color.White
            )
        ) {
            Text(text = "SIGN UP")
        }
    }
}

fun createUser(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSuccessful: () -> Unit,
    onFailed: (String) -> Unit
) {
    if (email.isNotBlank() && password.isNotBlank()) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        auth.app.applicationContext,
                        "User created successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    onSuccessful()
                } else {
                    onFailed("Error: ${task.exception?.message}")
                }
            }
    } else {
        Toast.makeText(
            auth.app.applicationContext,
            "Please enter valid email and password",
            Toast.LENGTH_SHORT
        ).show()
    }
}