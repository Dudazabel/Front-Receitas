package com.example.api_receitas.features.edit.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api_receitas.R
import com.example.api_receitas.ui.theme.Laranja

@Composable
fun RecipeEditScreen(
    recipeId: Long,
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit
){
    var titulo by rememberSaveable {
        mutableStateOf("Título da receita")
    }
    var descricao by rememberSaveable {
        mutableStateOf("Descrição da receita")
    }
    var tempo by rememberSaveable {
        mutableStateOf("30")
    }
    var porcoes by rememberSaveable {
        mutableStateOf("4")
    }

    val listaIngredientes = remember {
        mutableStateListOf("Banana", "Banana","Banana","Banana","Banana")
    }
    val listaPassos = remember {
        mutableStateListOf("um", "dois", "tres")
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            TopImage(
                onBackClick = onBackClick,
                onSaveClick = { onSaveSuccess() }
            )
        }
        item {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                EditInfoSection(
                    titulo = titulo, onTituloChange = { titulo = it },
                    descricao = descricao, onDescricaoChange = { descricao = it },
                    tempo = tempo, onTempoChange = { tempo = it },
                    porcoes = porcoes, onPorcoesChange = { porcoes = it }
                )
                Spacer(modifier = Modifier.height(24.dp))

                EditIngredientSection(listaIngredientes)
                Spacer(modifier = Modifier.height(24.dp))
                EditaPassoSection(listaPassos)
            }
        }
    }
}

@Composable
fun TopImage(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .size(48.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Voltar"
                )
            }
            IconButton(
                onClick = onSaveClick,
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Salvar alterações",
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
}

@Composable
fun EditInfoSection(
    titulo: String, onTituloChange: (String) -> Unit,
    descricao: String, onDescricaoChange: (String) -> Unit,
    tempo: String, onTempoChange: (String) -> Unit,
    porcoes: String, onPorcoesChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.clock),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        TransparentTextField(
            value = tempo,
            onValueChange = onTempoChange,
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.width(40.dp)
        )
        Text("m", fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.width(50.dp))
        
        Icon(
            painter = painterResource(id = R.drawable.dish),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        TransparentTextField(
            value = porcoes, 
            onValueChange = onPorcoesChange, 
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.width(30.dp)
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
    TransparentTextField(
        value = titulo, 
        onValueChange = onTituloChange, 
        textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(12.dp))
    TransparentTextField(
        value = descricao, 
        onValueChange = onDescricaoChange, 
        textStyle = TextStyle(color = Color.Gray),
        modifier = Modifier.fillMaxWidth(),
        singleLine = false
    )
}

@Composable
fun EditIngredientSection(ingredientes: MutableList<String>){
    Column {
        ingredientes.forEachIndexed{ index, ingrediente ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "• ", fontSize = 18.sp)
                TransparentTextField(
                    value = ingrediente, 
                    onValueChange = { novoTexto -> ingredientes[index] = novoTexto }, 
                    textStyle = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { ingredientes.removeAt(index) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Delete, 
                        contentDescription = "Remover",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
        TextButton(
            onClick = { ingredientes.add("") },
            colors = ButtonDefaults.textButtonColors(contentColor = Laranja)
        ) {
            Icon(
                imageVector = Icons.Default.Add, 
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Adicionar Ingrediente")
        }
    }
}

@Composable
fun EditaPassoSection(passos: MutableList<String>){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        passos.forEachIndexed { index, passo ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Passo ${index + 1}",
                            fontSize = 18.sp, 
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { passos.removeAt(index) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                Icons.Default.Delete, 
                                contentDescription = "Remover passo",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    TransparentTextField(
                        value = passo, 
                        onValueChange = { novoTexto -> passos[index] = novoTexto }, 
                        textStyle = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false
                    )
                }
            }
        }
        TextButton(
            onClick = { passos.add("") },
            colors = ButtonDefaults.textButtonColors(contentColor = Laranja)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Adicionar Passo")
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun TransparentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier,
        singleLine = singleLine,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}