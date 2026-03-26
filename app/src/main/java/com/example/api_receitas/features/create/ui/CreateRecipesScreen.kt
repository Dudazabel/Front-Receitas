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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.api_receitas.ui.theme.Cinza
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

data class Ingredientes(
    val nome: String,
    val quantidade: String
)

data class Passos(
    val descricao: String
)

@Composable
fun createRecipe(modifier: Modifier = Modifier) {

    var nomeReceita by remember { mutableStateOf("") }
    var descricaoReceita by remember { mutableStateOf("") }
    var tempoPreparoString by remember { mutableStateOf("") }
    var tempoPreparo by remember { mutableStateOf(0.0) }
    var nomeIngredientes by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    val ingredientes = remember { mutableStateListOf<Ingredientes>() }
    var descricaoPassos by remember { mutableStateOf("") }
    val passos = remember { mutableStateListOf<Passos>() }

    LazyColumn (modifier = Modifier
        .padding(40.dp)
        .fillMaxWidth()){

        item {
            Row (verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = { },
                    modifier = Modifier
                        .background(Cinza, CircleShape)
                        .size(48.dp)
                ) {
                    Icon(
                        Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Voltar")
                }

                Spacer(modifier = Modifier.width(20.dp))

                Text(text = "Cadastro de receita",
                    fontSize = 20.sp)
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Nome")
            OutlinedTextField(
                value = nomeReceita,
                onValueChange = {nomeReceita = it},
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Descrição")
            OutlinedTextField(
                value = descricaoReceita,
                onValueChange = {descricaoReceita = it},
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Tempo de preparo")
            OutlinedTextField(
                value = tempoPreparoString,
                onValueChange = {tempoPreparoString = it
                    tempoPreparo = it.toDoubleOrNull() ?: 0.0},
                modifier = Modifier.fillMaxWidth()
            )
        }


        item {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Ingrediente")
            OutlinedTextField(
                value = nomeIngredientes,
                onValueChange = {nomeIngredientes = it},
                label = {Text("Nome:")},
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = quantidade,
                onValueChange = {quantidade = it},
                label = {Text("Quantidade:")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            
            Button(onClick = {
                if (nomeIngredientes.isNotBlank()
                    && quantidade.isNotBlank()) {
                    ingredientes.add(Ingredientes(nomeIngredientes, quantidade))
                    nomeIngredientes = ""
                    quantidade = ""
                }
            }) {
                Text(text = "Adicionar Ingrediente")
            }
        }

        item {
            Card {
                Column {
                    ingredientes.forEach {ingrediente ->
                        Row {
                            Text(text = ingrediente.quantidade)
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = ingrediente.nome)
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Passos")
            OutlinedTextField(
                value = descricaoPassos,
                onValueChange = {descricaoPassos = it},
                label = {Text("Descrição:")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                if (descricaoPassos.isNotBlank()){
                    passos.add(Passos(descricaoPassos))
                    descricaoPassos = ""
                }
            }) {
                Text(text = "Adicionar Passo")
            }
        }

        item {
            Card {
                Column {
                    passos.forEach {passo ->
                        Row {
                            Text(text = passo.descricao)
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                    }
                }
            }
        }
        
        item { 
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Criar Receita")
            }
        }


    }
}