package com.me.finaltestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.me.finaltestapp.db.Course
import com.me.finaltestapp.db.CourseDatabase
import com.me.finaltestapp.dbops.CourseRepository
import com.me.finaltestapp.ui.theme.FinalTestAppTheme
import com.me.finaltestapp.viewmodel.CourseViewModel
import com.me.finaltestapp.viewmodel.CourseViewModelFactory

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val courseDatabase by lazy {
            Room.databaseBuilder(applicationContext, CourseDatabase::class.java, "course_database")
                .build()
        }

        val repository by lazy {
            CourseRepository(courseDatabase.courseDao())
        }

        val contactViewModel: CourseViewModel by viewModels {
            CourseViewModelFactory(repository)
        }
        setContent {
            FinalTestAppTheme {
                HomeScreen(viewModel = contactViewModel)
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: CourseViewModel) {
    val courses by viewModel.courseListLD.collectAsState(initial = emptyList())
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(courses) { course ->
            CourseCard(course)
        }
    }
}

@Composable
@Preview
fun PreviewCourseCard() {
    CourseCard(
        course = Course(
            courseName = "Jetpack Compose Basics",
            interestedGeeks = "150",
            rating = "4.8",
            des = "Learn the fundamentals of Jetpack Compose.",
            courseDifficulty = "Beginner",
            courseImage = R.drawable.course_1,
            seatsLeft = "4 seats left"
        )
    )
}

@Composable
fun CourseCard(course: Course) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = course.courseImage),
                contentDescription = "Course Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "7k+ interested Geeks",
                    fontFamily = fontMulish,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Gray),
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp)
                )
                Text(
                    text = "4.5/5",
                    fontFamily = fontMulish,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 12.dp, top = 12.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text(
                    text = "Data Science Classroom Program",
                    fontFamily = fontMulish,
                    fontWeight = FontWeight.ExtraBold,
                    style = TextStyle(color = Color.Black, fontSize = 16.sp),
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = "Beginner to Advance",
                    fontFamily = fontMulish,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Gray),
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp)
                )
                Text(
                    text = "4 seats left",
                    fontFamily = fontMulish,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color(0xFFb33044)),
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFc8e0ce))
                    .padding(top = 16.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(
                            color = Color(0xFFc8e0ce)
                        )
                        .border(
                            BorderStroke(1.dp, Color(0xFF32c256)),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .padding(top = 8.dp, bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Explore",
                        style = TextStyle(
                            color = Color(0xFF32c256),
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = fontMulish
                        )
                    )
                }
            }
        }
    }
}


fun generateSampleCourses(): List<Course> {
    return listOf(
        Course(
            courseName = "Jetpack Compose Basics",
            interestedGeeks = "150",
            rating = "4.8",
            des = "Learn the fundamentals of Jetpack Compose.",
            courseDifficulty = "Beginner",
            courseImage = R.drawable.course_1,
            seatsLeft = "4 seats left"
        ),
        Course(
            courseName = "Kotlin Coroutines",
            interestedGeeks = "200",
            rating = "4.7",
            des = "Master concurrency with Kotlin coroutines.",
            courseDifficulty = "Intermediate",
            courseImage = R.drawable.course_2,
            seatsLeft = "4 seats left"
        ),
        Course(
            courseName = "Android Architecture",
            interestedGeeks = "300",
            rating = "4.9",
            des = "Learn about MVVM, LiveData, and more.",
            courseDifficulty = "Advanced",
            courseImage = R.drawable.course_3,
            seatsLeft = "4 seats left"
        ),
        Course(
            courseName = "Firebase Integration",
            interestedGeeks = "180",
            rating = "4.6",
            des = "Integrate Firebase into your Android apps.",
            courseDifficulty = "Intermediate",
            courseImage = R.drawable.course_4,
            seatsLeft = "4 seats left"
        ),
        Course(
            courseName = "Room Database",
            interestedGeeks = "220",
            rating = "4.5",
            des = "Understand how to use Room in Android.",
            courseDifficulty = "Intermediate",
            courseImage = R.drawable.course_5,
            seatsLeft = "4 seats left"
        ),
        Course(
            courseName = "Dependency Injection with Hilt",
            interestedGeeks = "120",
            rating = "4.4",
            des = "Simplify dependency injection with Hilt.",
            courseDifficulty = "Advanced",
            courseImage = R.drawable.course_6,
            seatsLeft = "4 seats left"
        ),
        Course(
            courseName = "Unit Testing in Android",
            interestedGeeks = "250",
            rating = "4.7",
            des = "Learn how to write unit tests for Android apps.",
            courseDifficulty = "Intermediate",
            courseImage = R.drawable.course_7,
            seatsLeft = "4 seats left"
        ),
        Course(
            courseName = "Navigation in Compose",
            interestedGeeks = "100",
            rating = "4.8",
            des = "Understand navigation in Jetpack Compose.",
            courseDifficulty = "Beginner",
            courseImage = R.drawable.course_8,
            seatsLeft = "4 seats left"
        ),
        Course(
            courseName = "Custom Views in Android",
            interestedGeeks = "140",
            rating = "4.3",
            des = "Create custom views and UI components.",
            courseDifficulty = "Advanced",
            courseImage = R.drawable.course_9,
            seatsLeft = "4 seats left"
        ),
        Course(
            courseName = "State Management in Compose",
            interestedGeeks = "160",
            rating = "4.9",
            des = "Learn state management techniques in Compose.",
            courseDifficulty = "Intermediate",
            courseImage = R.drawable.course_10,
            seatsLeft = "4 seats left"
        )
    )
}

val fontMulish = FontFamily(
    Font(R.font.muslish, FontWeight.Normal)
)
