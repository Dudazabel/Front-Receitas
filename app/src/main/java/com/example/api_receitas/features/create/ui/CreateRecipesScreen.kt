package com.example.api_receitas.features.create.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_receitas.ui.theme.Cinza
import com.example.api_receitas.ui.theme.Laranja
import com.example.api_receitas.features.details.viewmodel.ReceitaViewModel
import com.example.api_receitas.data.model.receita.requisicao.IngredienteRequisicao
import com.example.api_receitas.data.model.receita.requisicao.PassoRequisicao

data class Ingredientes(
    val nome: String,
    val quantidade: String
)

data class Passos(
    val descricao: String
)

@Composable
fun CreateRecipe(
    onBackClick: () -> Unit,
    onRecipeSaved: () -> Unit,
    viewModel: ReceitaViewModel = viewModel()
) {

    var nomeReceita by remember { mutableStateOf("") }
    var descricaoReceita by remember { mutableStateOf("") }
    var tempoPreparoString by remember { mutableStateOf("") }
    var porcoesString by remember { mutableStateOf("") }
    var tempoPreparo by remember { mutableDoubleStateOf(0.0) }
    var porcoes by remember { mutableDoubleStateOf(0.0) }
    var nomeIngredientes by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    val ingredientes = remember { mutableStateListOf<Ingredientes>() }
    var descricaoPassos by remember { mutableStateOf("") }
    val passos = remember { mutableStateListOf<Passos>() }

    LazyColumn(
        modifier = Modifier
            .padding(40.dp)
            .fillMaxWidth()
    ) {

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { onBackClick() },
                    modifier = Modifier
                        .background(Cinza, CircleShape)
                        .size(48.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Voltar"
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "Cadastro de receita",
                    fontSize = 20.sp
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Nome",
                fontSize = 17.sp
            )
            OutlinedTextField(
                value = nomeReceita,
                onValueChange = { nomeReceita = it },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Descrição",
                fontSize = 17.sp
            )
            OutlinedTextField(
                value = descricaoReceita,
                onValueChange = { descricaoReceita = it },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Tempo de preparo (min)",
                fontSize = 17.sp
            )
            OutlinedTextField(
                value = tempoPreparoString,
                onValueChange = {
                    tempoPreparoString = it
                    tempoPreparo = it.toDoubleOrNull() ?: 0.0
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Porções",
                fontSize = 17.sp
            )
            OutlinedTextField(
                value = porcoesString,
                onValueChange = {
                    porcoesString = it
                    porcoes = it.toDoubleOrNull() ?: 0.0
                },
                modifier = Modifier.fillMaxWidth()
            )
        }


        item {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Ingrediente",
                fontSize = 17.sp
            )
            OutlinedTextField(
                value = nomeIngredientes,
                onValueChange = { nomeIngredientes = it },
                label = { Text("Nome:") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = quantidade,
                onValueChange = { quantidade = it },
                label = { Text("Quantidade:") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    if (nomeIngredientes.isNotBlank()
                        && quantidade.isNotBlank()
                    ) {
                        ingredientes.add(Ingredientes(nomeIngredientes, quantidade))
                        nomeIngredientes = ""
                        quantidade = ""
                    }
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Laranja),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text(
                    text = "Adicionar Ingrediente",
                    fontSize = 17.sp
                )
            }
        }

        item {
            if (ingredientes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ingredientes.forEach { ingrediente ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = ingrediente.quantidade,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(30.dp))
                                Text(
                                    text = ingrediente.nome,
                                    modifier = Modifier.weight(2f)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Passos",
                fontSize = 17.sp
            )
            OutlinedTextField(
                value = descricaoPassos,
                onValueChange = { descricaoPassos = it },
                label = { Text("Descrição:") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    if (descricaoPassos.isNotBlank()) {
                        passos.add(Passos(descricaoPassos))
                        descricaoPassos = ""
                    }
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Laranja),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Adicionar Passo",
                    fontSize = 17.sp
                )
            }
        }

        item {
            if (passos.isNotEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        passos.forEachIndexed { index, passo ->
                            Row {
                                Text(text = "${index + 1}. ${passo.descricao}")
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }
        item {
            if (viewModel.mensagemFeedback.isNotEmpty()) {
                Text(
                    text = viewModel.mensagemFeedback,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    val ingredientesReq = ingredientes.map {
                        IngredienteRequisicao(it.nome, it.quantidade)
                    }
                    val passosReq = passos.map {
                        PassoRequisicao(it.descricao)
                    }

                    viewModel.CriarNovaReceita(
                        nome = nomeReceita,
                        descricao = descricaoReceita,
                        tempoPreparo = tempoPreparo,
                        porcoes = porcoes,
                        ingredientes = ingredientesReq,
                        passos = passosReq,
                        onSuccess = {
                            onRecipeSaved()
                        }
                    )
                },
                colors = ButtonDefaults.textButtonColors(containerColor = Laranja),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Criar Receita",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }
    }
}