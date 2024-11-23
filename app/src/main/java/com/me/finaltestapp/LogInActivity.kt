package com.me.finaltestapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.me.finaltestapp.ui.theme.FinalTestAppTheme

class LogInActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                LoginScreen(auth)
            }
        }
    }
}

@Composable
fun LoginScreen(auth: FirebaseAuth) {
    val context = LocalContext.current
    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    var loading by remember { mutableStateOf(false) }

    if(loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            //CircularProgressIndicator()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(150.dp))

        Spacer(modifier = Modifier.height(40.dp))

        StyledTextField(value = username, onValueChange = {
            username = it
        }, "username")

        StyledTextField(value = password, onValueChange = {
            password = it
        }, "password")

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                loading = true
                loginUser(auth, username, password, onSuccessful = {
                    loading = false
                    context.startActivity(
                        Intent(context, HomeActivity::class.java)
                    )
                    (context as Activity).finish()
                }, onFailed = {
                    loading = false
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
            Text(text = "LOG IN")
        }

        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Don't have an account? Create",
            modifier = Modifier
                .clickable {
                    context.startActivity(
                        Intent(context, SignUpActivity::class.java)
                    )
                },
        )
    }
}

fun loginUser(auth: FirebaseAuth, email: String, password: String, onSuccessful:() -> Unit, onFailed:() -> Unit) {
    if (email.isNotBlank() && password.isNotBlank()) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        auth.app.applicationContext,
                        "Login successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    onSuccessful()
                } else {
                    Toast.makeText(
                        auth.app.applicationContext,
                        "Error: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    onFailed()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Enter text...",
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(30.dp)
            ),
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}