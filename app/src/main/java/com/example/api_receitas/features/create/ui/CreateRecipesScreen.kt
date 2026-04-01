package com.example.api_receitas.features.create.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.api_receitas.ui.theme.Cinza
import com.example.api_receitas.ui.theme.Laranja
import com.example.api_receitas.data.viewmodel.ReceitaViewModel
import com.example.api_receitas.data.model.receita.requisicao.IngredienteRequisicao
import com.example.api_receitas.data.model.receita.requisicao.PassoRequisicao
import java.io.ByteArrayOutputStream
import java.lang.Exception

data class Ingredientes(
    val nome: String,
    val quantidade: String
)

data class Passos(
    val descricao: String
)

fun uriToBase64(context: Context, uri: Uri?): String? {
    if(uri == null) return null
    return try {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        val byteArray = outputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.NO_WRAP)
    } catch (e: Exception){
        e.printStackTrace()
        null
    }
}

@Composable
fun CreateRecipe(
    onBackClick: () -> Unit,
    onRecipeSaved: () -> Unit,
    viewModel: ReceitaViewModel = viewModel()
) {
    val context = LocalContext.current

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

    var isSubmitting by remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            imageUri = uri
        }
    )

    LaunchedEffect(viewModel.mensagemFeedback) {
        if (viewModel.mensagemFeedback.isNotEmpty()){
            isSubmitting = false
        }
    }

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
                        .size(40.dp)
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
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Cinza)
                    .clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if(imageUri == null){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adicionar foto",
                            modifier = Modifier.size(40.dp),
                            tint = Color.Gray
                        )
                        Text("Adicionar foto", color = Color.Gray)
                    }
                } else {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Foto da receita",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
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
                    isSubmitting = true
                    viewModel.mensagemFeedback = ""

                    val ingredientesReq = ingredientes.map {
                        IngredienteRequisicao(
                            it.nome,
                            it.quantidade
                        )
                    }
                    val passosReq = passos.mapIndexed { index, passo ->
                        PassoRequisicao(
                            descricao = passo.descricao,
                            ordem = index + 1
                        )
                    }

                    val base64Image = uriToBase64(context, imageUri)

                    viewModel.CriarNovaReceita(
                        nome = nomeReceita,
                        descricao = descricaoReceita,
                        tempoPreparo = tempoPreparo,
                        porcoes = porcoes,
                        ingredientes = ingredientesReq,
                        passos = passosReq,
                        foto = base64Image,
                        onSuccess = {
                            isSubmitting = false
                            onRecipeSaved()
                        }
                    )
                },
                enabled = !isSubmitting,

                colors = ButtonDefaults.textButtonColors(
                    containerColor = Laranja,
                    disabledContentColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isSubmitting) "Criando..." else "Criar Receita",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }
    }
}