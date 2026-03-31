package com.example.api_receitas.features.edit.ui

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_receitas.R
import com.example.api_receitas.data.model.receita.requisicao.IngredienteRequisicao
import com.example.api_receitas.data.model.receita.requisicao.PassoRequisicao
import com.example.api_receitas.data.model.receita.requisicao.ReceitaRequisicao
import com.example.api_receitas.data.model.receita.resposta.IngredienteResposta
import com.example.api_receitas.data.model.receita.resposta.PassoResposta
import com.example.api_receitas.features.details.viewmodel.ReceitaViewModel
import com.example.api_receitas.ui.theme.Laranja
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.api_receitas.features.create.ui.uriToBase64

@Composable
fun RecipeEditScreen(
    recipeId: Long,
    viewModel: ReceitaViewModel = viewModel(),
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit,
    onDeleteSucess: () -> Unit
){
    val context = LocalContext.current

    var fotoBase64 by rememberSaveable { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri ->
        val base64 = uriToBase64(context, uri)
        if(base64 != null){
            fotoBase64 = base64
        } else {
            Toast.makeText(context, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show()
        }
    }

    var titulo by rememberSaveable { mutableStateOf("") }
    var descricao by rememberSaveable { mutableStateOf("") }
    var tempo by rememberSaveable { mutableStateOf("") }
    var porcoes by rememberSaveable { mutableStateOf("") }

    val listaIngredientes = remember { mutableStateListOf<IngredienteResposta>() }
    val listaPassos = remember { mutableStateListOf<PassoResposta>() }

    LaunchedEffect(viewModel.mensagemFeedback) {
        if(viewModel.mensagemFeedback.isNotEmpty()){
            Toast.makeText(context, viewModel.mensagemFeedback, Toast.LENGTH_LONG).show()
            viewModel.mensagemFeedback = ""
        }
    }

    LaunchedEffect(recipeId) {
        viewModel.buscaReceitaPorId(recipeId)
    }

    var dadosCarregados by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(viewModel.receita) {
        if(!dadosCarregados){
            viewModel.receita?.let { receita ->
                titulo = receita.nome
                descricao = receita.descricao
                tempo = receita.tempoPreparo.toInt().toString()
                porcoes = receita.porcoes.toInt().toString()
                fotoBase64 = receita.foto

                listaIngredientes.clear()
                listaIngredientes.addAll(receita.ingredientes ?: emptyList())

                listaPassos.clear()
                listaPassos.addAll(receita.passos ?: emptyList())
                dadosCarregados = true
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (viewModel.carregando && viewModel.receita == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Laranja
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    TopImage(
                        fotoBase64 = fotoBase64,
                        onBackClick = onBackClick,
                        onSaveClick = {
                            val quantidadeInvalida = listaIngredientes.any{ ingrediente ->
                                val valorNumerico = ingrediente.quantidade
                                    .takeWhile { it.isDigit() || it == '.' || it == ',' || it == '-' }
                                    .replace(',', '.')
                                    .toDoubleOrNull()
                                valorNumerico != null && valorNumerico <= 0
                            }
                            if(quantidadeInvalida){
                                Toast.makeText(context, "A quantidade dos ingredientes deve ser maior que zero!", Toast.LENGTH_SHORT).show()
                            } else {
                                val tempoFinal = tempo.toDoubleOrNull() ?.takeIf { it > 0 } ?: 1.0
                                val porcoesFinal = porcoes.toDoubleOrNull() ?.takeIf { it > 0 } ?: 1.0

                                val ingredientesRequest = listaIngredientes
                                    .filter { it.nome.isNotBlank() }
                                    .map { ingrediente ->
                                        IngredienteRequisicao(
                                            nome = ingrediente.nome,
                                            quantidade = ingrediente.quantidade.ifBlank { "A gosto" }
                                        )
                                    }
                                val passosRequest = listaPassos
                                    .filter { it.descricao.isNotBlank() }
                                    .mapIndexed { index, passo ->
                                        PassoRequisicao(
                                            descricao = passo.descricao,
                                            ordem = index + 1
                                        )
                                    }
                                val receitaAtualizada = ReceitaRequisicao(
                                    nome = titulo.ifBlank { "Receita Sem Nome" },
                                    descricao = descricao.ifBlank { "Sem Descrição" },
                                    tempoPreparo = tempoFinal,
                                    porcoes = porcoesFinal,
                                    ingredientes = ingredientesRequest,
                                    passos = passosRequest,
                                    foto = fotoBase64
                                )

                                viewModel.atualizarReceita(recipeId, receitaAtualizada) {
                                    onSaveSuccess()
                                }
                            }
                        },
                        onDeleteClick = {
                            viewModel.deletarReceita(recipeId) {
                                onDeleteSucess()
                            }
                        },
                        onEditImageClick = {
                            launcher.launch("image/*")
                        }
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
    }
}

@Composable
fun TopImage(
    fotoBase64: String?,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditImageClick: () -> Unit
){
    val bitmap = remember(fotoBase64){
        if(!fotoBase64.isNullOrEmpty()){
            try{
                val sPura = if(fotoBase64.contains(",")) fotoBase64.substringAfter(",") else fotoBase64
                val imageBytes = Base64.decode(sPura, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            } catch (e: Exception) {

                null
            }
        } else null
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(Color.Gray)
    ) {
        if(bitmap != null){
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Foto da Receita",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        IconButton(
            onClick = onEditImageClick,
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .size(64.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Trocar Foto",
                tint = Color.White
            )
        }
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
            Column {
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
                Spacer(modifier = Modifier.height(15.dp))
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .background(Color.White, CircleShape)
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Deletar receita",
                        tint = Color.Red
                    )
                }
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
            modifier = Modifier.width(50.dp)
        )
        Text("m", fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.width(30.dp))
        
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
            modifier = Modifier.width(50.dp)
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
fun EditIngredientSection(ingredientes: MutableList<IngredienteResposta>){
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
                    value = ingrediente.quantidade,
                    onValueChange = { novaQuantidade ->
                        ingredientes[index] = ingredientes[index].copy(quantidade = novaQuantidade)
                    },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(0.35f),
                    singleLine = false
                )
                Spacer(modifier = Modifier.width(8.dp))

                TransparentTextField(
                    value = ingrediente.nome, 
                    onValueChange = { novoTexto -> 
                        ingredientes[index] = ingredientes[index].copy(nome = novoTexto) 
                    }, 
                    textStyle = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.weight(0.7f)
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
            onClick = { ingredientes.add(IngredienteResposta(0, "", "")) },
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
fun EditaPassoSection(passos: MutableList<PassoResposta>){
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
                        value = passo.descricao, 
                        onValueChange = { novoTexto -> 
                            passos[index] = passos[index].copy(descricao = novoTexto) 
                        }, 
                        textStyle = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false
                    )
                }
            }
        }
        TextButton(
            onClick = { passos.add(PassoResposta(0, passos.size + 1, "")) },
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
