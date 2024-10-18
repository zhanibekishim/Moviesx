package com.jax.movies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.data.OnBoardingPage
import com.jax.movies.data.onboardingPages
import com.jax.movies.ui.theme.MoviesTheme
import kotlinx.coroutines.launch



@ExperimentalFoundationApi
@Composable
fun OnBoardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState { onboardingPages.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Skillcinema",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < onboardingPages.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            onFinish()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    "Пропустить",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->
            OnBoardingPageContent(onboardingPages[page])
        }

        DotsIndicator(
            totalDots = onboardingPages.size,
            selectedIndex = pagerState.currentPage
        )
    }
}

@Composable
fun OnBoardingPageContent(page: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.imageId),
            contentDescription = "Page 1",
            modifier = Modifier.size(360.dp, 270.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = page.title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
@Composable
fun DotsIndicator(totalDots: Int, selectedIndex: Int) {
    Row(
        modifier = Modifier.padding(bottom = 90.dp)
    ) {
        for (i in 0 until totalDots) {
            val color = if (i == selectedIndex) Color.Black else Color.Gray
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .padding(4.dp)
                    .background(color = color, shape = CircleShape)
            )
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun OnBoardingPreview() {
    MoviesTheme {
        OnBoardingScreen(onFinish = {})
    }
}
