package com.example.netflixapp.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.netflixapp.R
import com.example.netflixapp.model.Movie
import com.example.netflixapp.model.getMovie
import com.example.netflixapp.navigation.MovieScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController : NavController){
    val movie :List<Movie> = getMovie()
    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    title = { Text("Netflix") },
                    colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
                )
            }
        }
    ) {paddingValues ->
        MainContent(movieList = movie, paddingValues = paddingValues, navController = navController)

    }
}




@Composable
fun MainContent(
    movieList: List<Movie>,
    paddingValues: PaddingValues,
    navController: NavController
){
    LazyColumn(modifier = Modifier.padding(paddingValues)){
        items(movieList){
            MovieRow(movie = it){
                movie ->
//                Log.d("Tag", "MainContent: $movie")
                navController.navigate(MovieScreens.DetailsScreen.name+"/${movie}") // actual data passed here
            }
        }
    }
}
@Preview
@Composable
fun MovieRow(
    modifier :Modifier = Modifier,
    movie :Movie = getMovie()[0],
    onMovieClick:(String) -> Unit = {}
){
    var expand by remember{
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, top = 6.dp, bottom = 2.dp)
            .fillMaxWidth()
            .clickable {
                onMovieClick(movie.id)
            },
        shape =  RoundedCornerShape(corner = CornerSize(15)),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                    Surface(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(100.dp)
                            .align(Alignment.CenterVertically),
                        shape = RectangleShape
//                        RoundedCornerShape(corner = CornerSize(5))
                    ) {
                        CreateImages(movie.images[0])

                    }
                    Column(
                        modifier = modifier.padding(10.dp),
                    ) {
                        Text(
                            text = movie.title,
                            modifier = Modifier.padding( 4.dp),
                            fontSize = 25.sp
                        )
                        Text(text = "Director : ${movie.director} ",
                            modifier = Modifier.padding( 4.dp))
                        Text(
                            text = "Released : ${movie.year}",
                            modifier = Modifier.padding( 4.dp)
                        )

                        if(expand){
                            AnimatedVisibility(visible = expand) {
                                Column {
                                    Text(buildAnnotatedString {
                                        withStyle(style = SpanStyle(color = Color.LightGray,
                                            fontSize = 14.sp)){
                                            append("Plot : ")
                                            }
                                        withStyle(style = SpanStyle(color = Color.Black,
                                            fontSize = 16.sp)){
                                            append(movie.plot)
                                        }
                                        }
                                    )
                                    Divider()
                                    Text(text = "Genre : ${movie.genre}")
                                    Text(text = "Actors : ${movie.actors} ")
                                }

                            }


                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                if (!expand) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    expand = !expand
                                }
                            )
                        }

                    }

            }
    }
}

@Composable
fun CreateImages(image : String?) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = "Movie poster",
        contentScale = ContentScale.Crop,
        modifier = Modifier.clip(RectangleShape)
    )
}