package com.example.api_receitas.features.confirmation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api_receitas.ui.theme.AzulFundo
import com.example.api_receitas.ui.theme.Laranja

@Composable
fun TelaConfimacao(
    mensagem: String,
    onOkClick: () -> Unit
){
    Column (modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Laranja,
            modifier = Modifier
                .size(140.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = mensagem,
            fontSize = 22.sp,
            color = AzulFundo)

        Spacer(modifier = Modifier.height(15.dp))

    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Button(onClick = { onOkClick() },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Laranja)) {

            Text(text = "Continuar",
                fontSize = 17.sp,
                color = Color.White)
        }
    }
}