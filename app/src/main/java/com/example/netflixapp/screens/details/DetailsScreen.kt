package com.example.netflixapp.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.netflixapp.model.Movie
import com.example.netflixapp.model.getMovie
import com.example.netflixapp.navigation.MovieScreens
import com.example.netflixapp.screens.home.CreateImages
import com.example.netflixapp.screens.home.MovieRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController,
                  movieId: String?) {
    val movieItemList = getMovie().filter {  movie ->
        movie.id == movieId
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Netflix") },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                navigationIcon = {

                        Icon(imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Arrow back",
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )

                }
            )
        }
    ) {paddingValues->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                MovieRow(movie =  movieItemList.first())
                Divider()
                Text(text = "Images")

                HorizontalScrollableImageView(movieItemList)
            }
        }
    }
}

@Composable
private fun HorizontalScrollableImageView(movieItemList: List<Movie>) {
    LazyRow {
        items(movieItemList[0].images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(250.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                CreateImages(image = image)
            }
        }
    }
}