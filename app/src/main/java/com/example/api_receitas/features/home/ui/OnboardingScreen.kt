@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
package com.example.api_receitas.features.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api_receitas.R
import com.example.api_receitas.ui.theme.AzulClaro
import com.example.api_receitas.ui.theme.Laranja
import kotlinx.coroutines.launch

data class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String,
    val imageHeight: Dp = 250.dp,
    val imageFraction: Float = 0.8f
)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            image = R.drawable.livro,
            title = "Cadastre suas receitas",
            description = "Organize suas criações culinárias em um só lugar de forma rápida e fácil."
        ),
        OnboardingPage(
            image = R.drawable.vo,
            title = "Consulte sempre que precisar",
            description = "Tenha seus ingredientes e modo de preparo na palma da mão, onde quer que esteja.",
            imageHeight = 320.dp,
            imageFraction = 0.9f
        ),
        OnboardingPage(
            image = R.drawable.comida,
            title = "Cozinhe no seu tempo",
            description = "Está com pressa? Filtre as receitas pelo tempo de preparação e escolha a mais rápida.",
            imageHeight = 300.dp,
            imageFraction = 0.7f
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { position ->
            PagerScreen(onBoardingPage = pages[position])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            val isLastPage = pagerState.currentPage == pages.size - 1

            Button(
                onClick = {
                    if (isLastPage) {
                        onFinishOnboarding()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Laranja,
                    contentColor = Color.White
                )



            ) {
                Text(text = if (isLastPage) "Começar" else "Próximo")
            }
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Imagem Onboarding",
            modifier = Modifier
                .fillMaxWidth(onBoardingPage.imageFraction)
                .height(onBoardingPage.imageHeight)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = onBoardingPage.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = onBoardingPage.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}