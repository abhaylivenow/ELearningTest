package com.me.finaltestapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.HorizontalPager
import com.google.firebase.auth.FirebaseAuth
import com.me.finaltestapp.ui.theme.FinalTestAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent {
            FinalTestAppTheme {
                if(auth.currentUser == null) {
                    OnboardingScreen()
                } else {
                    startActivity(
                        Intent(this, HomeActivity::class.java)
                    )
                    this.finish()
                }
            }
        }
    }
}

@Composable
fun OnboardingScreen() {
    val pagerState = com.google.accompanist.pager.rememberPagerState()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "skip",
                modifier = Modifier
                    .padding(end = 20.dp, top = 20.dp)
                    .clickable {
                        (context as Activity).finish()
                        context.startActivity(
                            Intent(context, LogInActivity::class.java)
                        )
                    },
                style = TextStyle(
                    color = Color.Blue,
                    fontSize = 18.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier.height(400.dp)
        ) { page ->
            when (page) {
                0 -> PageComposable(
                    R.drawable.onboardin_1,
                    "It's not just a learning, It's a promise",
                    "This is the subtext regarding the oboarding of this breautiful app"
                )
                1 -> PageComposable(
                    R.drawable.onboarding_2,
                    "It's not just a learning, It's a promise",
                    "This is the subtext regarding the oboarding of this breautiful app"
                )
                2 -> PageComposable(
                    R.drawable.onboarding_3,
                    "It's not just a learning, It's a promise",
                    "This is the subtext regarding the oboarding of this breautiful app"
                )
            }
        }

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 12.dp else 8.dp)
                        .background(
                            color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }
        
        Button(onClick = {
            context.startActivity(
                Intent(context, SignUpActivity::class.java)
            )
             }) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            context.startActivity(
                Intent(context, LogInActivity::class.java)
            )
        }) {
            Text(text = "Login")
        }

    }
}

@Composable
@Preview
fun pagePreview() {
    PageComposable(
        R.drawable.onboardin_1,
        "It's not just a learning, It's a promise",
        "This is the subtext regarding the oboarding of this breautiful app"
    )
}

@Composable
fun PageComposable(bgImage: Int, text: String, subText: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(painter = painterResource(id = bgImage), contentDescription = "")

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = text,
            style = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = subText,
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
            color = Color.Gray,
        )
    }
}