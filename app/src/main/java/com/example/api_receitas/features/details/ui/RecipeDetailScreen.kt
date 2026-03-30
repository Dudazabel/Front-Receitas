package com.example.api_receitas.features.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api_receitas.R
import com.example.api_receitas.data.model.receita.resposta.IngredienteResposta
import com.example.api_receitas.data.model.receita.resposta.PassoResposta
import com.example.api_receitas.data.model.receita.resposta.ReceitaResposta
import com.example.api_receitas.features.details.viewmodel.ReceitaViewModel

@Composable
fun RecipeDetailScreen(
    receitaId: Long,
    viewModel: ReceitaViewModel = viewModel(),
    onVoltarClick: () -> Unit,
    onEditClick: () -> Unit
){
    LaunchedEffect(receitaId) {
        viewModel.buscaReceitaPorId(receitaId)
    }
    val receita = viewModel.receita

    if (viewModel.carregando) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (viewModel.mensagemFeedback.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = viewModel.mensagemFeedback, color = Color.Red)
        }
    } else if (receita != null) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                TopImage(
                    onVoltarClick = onVoltarClick,
                    onEditClick = onEditClick
                )
            }
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    InfoSection(receita)
                    Spacer(modifier = Modifier.height(24.dp))
                    IngrendientSection(receita.ingredientes)
                    Spacer(modifier = Modifier.height(24.dp))
                    PassoSection(receita.passos)
                }
            }
        }
    }
}

@Composable
fun TopImage(
    onVoltarClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(Color.DarkGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onVoltarClick,
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .size(48.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Voltar")
            }
            IconButton(
                onClick = onEditClick,
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .size(48.dp)
            ) {
                Icon(
                    Icons.Default.Create,
                    contentDescription = "Opções"
                )
            }
        }
    }
}

@Composable
fun InfoSection(receita: ReceitaResposta) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.clock),
            contentDescription = "Tempo de preparo",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "${receita.tempoPreparo.toInt()}m",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(50.dp))

        Icon(
            painter = painterResource(R.drawable.dish),
            contentDescription = "Quantidade de porções",
            modifier =  Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "${receita.porcoes.toInt()}",
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = receita.nome,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Black
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = receita.descricao
    )
}

@Composable
fun IngrendientSection(ingredientes: List<IngredienteResposta>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ingredientes.forEach { ingrediente ->
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(text = "• ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = ingrediente.quantidade, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = ingrediente.nome)
            }
        }
    }
}

@Composable
fun PassoSection(passos: List<PassoResposta>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val passosOrdenados = passos.sortedBy { it.ordem }

        passosOrdenados.forEachIndexed { index, passo ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Passo ${index + 1}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = passo.descricao)
                }
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}