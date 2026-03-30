package com.example.api_receitas.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api_receitas.R
import com.example.api_receitas.data.model.receita.resposta.ReceitaResposta
import com.example.api_receitas.features.details.viewmodel.ReceitaViewModel
import com.example.api_receitas.ui.theme.AzulClaro
import com.example.api_receitas.ui.theme.Laranja
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    nomeUsuario: String,
    viewModel: ReceitaViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onRecipeClick: (Long) -> Unit,
    onAddRecipeClick: () -> Unit
){
    LaunchedEffect(Unit) {
        viewModel.buscarTodasAsReceitas()
    }

    val listState = rememberLazyListState()
    val focusRequester = remember {
        FocusRequester()
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { Header(nome = nomeUsuario) },
        bottomBar = {
            BottomNavBar(
                onAddClick = onAddRecipeClick,
                onSearchClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(1)
                        focusRequester.requestFocus()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if(viewModel.carregando && viewModel.listaReceitas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }else{
                Conteudo(
                    receitas = viewModel.listaReceitas, 
                    viewModel = viewModel,
                    onRecipeClick = onRecipeClick
                    listState = listState,
                    focusRequester = focusRequester
                )
            }
        }
    }
}



@Composable
fun Conteudo(
    receitas: List<ReceitaResposta>,
    viewModel: ReceitaViewModel,
    onRecipeClick: (Long) -> Unit,
    listState: LazyListState,
    focusRequester: FocusRequester
){
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item{ SearchSection(focusRequester = focusRequester) }
        item{ CategoriesSection() }
        item{ RecipeFilter(viewModel = viewModel) }

        items(receitas){ receita ->
            RecipeCard(
                receita = receita,
                onClick = { onRecipeClick(receita.id) }
            )
        }
        item{ Spacer(modifier = Modifier.height(80.dp))}
    }
}

@Composable
fun BottomNavBar(
    onAddClick: () -> Unit,
    onSearchClick: () -> Unit
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.book),
                    contentDescription = "Tela incial",
                    modifier = Modifier.size(26.dp)
                )
            }
            IconButton(onClick = onAddClick) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(AzulClaro),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Adicionar receita",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            IconButton(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Pesquisar receita",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun Header(nome: String = "Usuário"){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .padding(top = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = buildAnnotatedString {
                append("Olá $nome, ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Boa Noite!")
                }
            },
            fontSize = 18.sp,
            color = Color.Black
        )
    }

}

@Composable
fun SearchSection(focusRequester: FocusRequester){
    var searchText by remember { mutableStateOf("")}

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .focusRequester(focusRequester),
        placeholder = {
            Text("Busque pratos ou ingredientes",
                color = Color.Gray)
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Busque pratos",
                tint = Color.Gray
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF0F0F0),
            unfocusedContainerColor = Color(0xFFF0F0F0),
            disabledContainerColor = Color(0xFFF0F0F0),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun CategoriesSection(){
    Column {
        SectionTitle(title = "Todos os Ingredientes", actionText = "Veja Todos")
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item{
                Row(
                    modifier = Modifier
                        .background(Laranja, RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Todos",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeCard(
    receita: ReceitaResposta,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(bottom = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
        ) {
        }
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = receita.nome,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        val resumoIngredientes = if (receita.ingredientes.isNotEmpty()){
            receita.ingredientes.take(3).joinToString(" - ") { it.nome }
        } else {
            "Sem ingredientes listados"
        }

        Text(
            text = resumoIngredientes,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.padding(horizontal =  8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.clock),
                contentDescription = "Tempo de preparo",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("${receita.tempoPreparo.toInt()}m", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                painter = painterResource(R.drawable.dish),
                contentDescription = "Quantidade de porções",
                modifier =  Modifier.size(17.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("${receita.porcoes.toInt()}", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RecipeFilter(viewModel: ReceitaViewModel){
    var menuExpandido by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Suas Receitas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Box{
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { menuExpandido = true }
            ) {
                Text(
                    text = if(viewModel.filtroAtual == "Todos") "Filtrar" else viewModel.filtroAtual,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.AutoMirrored.Filled.List,
                    contentDescription = "Filtro",
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
            }
            DropdownMenu(
                expanded = menuExpandido,
                onDismissRequest = { menuExpandido = false }
            ) {
                Text(
                    text = "Tempo de Preparo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
                DropdownMenuItem(
                    text = { Text("5-10 min") },
                    onClick = {
                        viewModel.filtroAtual = "5-10 min"
                        menuExpandido = false
                        viewModel.filtrarReceitasPorTempo(5.0,10.0)
                    }
                )
                DropdownMenuItem(
                    text = { Text("10-20 min") },
                    onClick = {
                        viewModel.filtroAtual = "10-20 min"
                        menuExpandido = false
                        viewModel.filtrarReceitasPorTempo(10.0, 20.0)
                    }
                )
                DropdownMenuItem(
                    text = { Text("20-40 min") },
                    onClick = {
                        viewModel.filtroAtual = "20-40 min"
                        menuExpandido = false
                        viewModel.filtrarReceitasPorTempo(20.0, 40.0)
                    }
                )
                DropdownMenuItem(
                    text = { Text("+50 min") },
                    onClick = {
                        viewModel.filtroAtual = "+50 min"
                        menuExpandido = false
                        viewModel.filtrarReceitasPorTempo(50.0, 11120.0)
                    }
                )

                HorizontalDivider()

                Text(
                    text = "Porções",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
                DropdownMenuItem(
                    text = { Text("1-3 porções") },
                    onClick = {
                        viewModel.filtroAtual = "1-3 porções"
                        menuExpandido = false
                        viewModel.filtrarPorPorcoes(1.0,3.0)

                    }
                )
                DropdownMenuItem(
                    text = { Text("4-10 porções") },
                    onClick = {
                        viewModel.filtroAtual = "4-10 porções"
                        menuExpandido = false
                        viewModel.filtrarPorPorcoes(4.0,10.0)

                    }
                )
                DropdownMenuItem(
                    text = { Text("10-15 porções") },
                    onClick = {
                        viewModel.filtroAtual = "10-15 porções"
                        menuExpandido = false
                        viewModel.filtrarPorPorcoes(10.0,15.0)

                    }
                )
                DropdownMenuItem(
                    text = { Text("+15 porções") },
                    onClick = {
                        viewModel.filtroAtual = "+15 porções"
                        menuExpandido = false
                        viewModel.filtrarPorPorcoes(15.0,990.0)
                    }
                )

                HorizontalDivider()
                DropdownMenuItem(
                    text = {
                        Text("Mostrar Todas",
                            color = Laranja,
                            fontWeight = FontWeight.Bold)
                    },
                    onClick = {
                        viewModel.filtroAtual = "Todos"
                        menuExpandido = false
                        viewModel.buscarTodasAsReceitas()
                    }
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, actionText: String){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = actionText,
                color = Color.Gray,
                fontSize = 14.sp
            )
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}