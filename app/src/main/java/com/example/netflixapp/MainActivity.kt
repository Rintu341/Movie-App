package com.example.netflixapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.netflixapp.navigation.MovieNavigation
import com.example.netflixapp.ui.theme.NetflixAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MovieNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(content:@Composable () -> Unit){ // Container Function
    NetflixAppTheme {
                content()
        }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApp {
            MovieNavigation()
    }
}