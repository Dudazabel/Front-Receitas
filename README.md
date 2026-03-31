<p align="center">
  <img src="/app/src/main/res/drawable/logo.png" alt="Logo App Receitas" width="400">
</p>

<h1 align="center">
Livro de Receitas App
</h1>

<p align="center">
Aplicativo mobile desenvolvido em <b>Kotlin</b> consumindo a API de receitas 🍲
</p>

<p align="center">
<img loading="lazy" src="http://img.shields.io/static/v1?label=STATUS&message=CONCLUIDO&color=GREEN&style=for-the-badge"/>
</p>

---

## 📌 Sobre o Projeto

O **Livro de Receitas App** é uma aplicação mobile que consome a API de receitas desenvolvida anteriormente, permitindo que os usuários interajam com os dados de forma simples, rápida e intuitiva.

O app funciona como um **livrinho digital**, onde é possível visualizar, pesquisar, criar, atualizar e excluir receitas diretamente pelo celular 📱

---

## 🎯 Objetivo

O principal objetivo do app é aplicar conceitos de desenvolvimento mobile com **Kotlin**, integrando com uma API REST e implementando um **CRUD completo** no front-end.

---

## ⚙️ Funcionalidades

| Funcionalidade | Descrição |
|--------------|----------|
| 📋 Feed de receitas | Listagem de todas as receitas |
| 🔎 Busca por ID | Buscar receita específica |
| ✨ Busca personalizada | Filtros por características |
| ➕ Criar receita | Cadastro de novas receitas |
| ✏️ Atualizar receita | Edição de receitas |
| 🗑️ Excluir receita | Remoção com ícone de lixeira |

---

## 🧠 Conceitos Aplicados

- Consumo de API REST 🌐  
- Arquitetura em camadas (UI / ViewModel / Data)  
- CRUD completo no mobile  
- Navegação entre telas 📲  
- Tratamento de estados (loading, erro, sucesso)  

---

## 🛠️ Tecnologias Utilizadas

<p>
  <img alt="Kotlin" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/kotlin/kotlin-original.svg" width="40" />
  <img alt="Android" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/android/android-original.svg" width="40" />
  <img alt="Material Design" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/materialui/materialui-original.svg" width="40" />
</p>

---

## 💬 Linguagem & Plataforma

- **Kotlin** - Linguagem moderna, segura e concisa para desenvolvimento Android
- **Android** - Plataforma mobile com suporte completo a componentes nativos

---

## 📚 Bibliotecas & Frameworks

- **Retrofit** - Cliente HTTP para consumo de APIs REST de forma eficiente
- **LiveData** - Componente reativo para observar mudanças de dados
- **ViewModel** - Gerenciamento de estado da UI com persistência em rotações
- **Coroutines** - Programação assíncrona simplificada e não-bloqueante
- **Material Design** - Design system para interfaces modernas e acessíveis

---

## 📱 Telas do App

### 🏠 Feed de Receitas
Exibe todas as receitas cadastradas na API.

### 🔍 Pesquisa
Permite buscar receitas por ID e também por filtros personalizados.

### ➕ Criar Receita
Tela para adicionar novas receitas ao sistema.

### ✏️ Atualizar Receita
Permite editar uma receita existente.

✔️ Inclui botão de exclusão com ícone de lixeira.

---

## 🔗 Integração com API

O app consome a API desenvolvida em **Spring Boot**, utilizando requisições HTTP:

- `GET` → listar e buscar  
- `POST` → criar  
- `PUT` → atualizar  
- `DELETE` → remover  

---

## 🚀 Como Executar

### 1️⃣ Clone o repositório
```bash
git clone https://github.com/Dudazabel/Front-Receitas.git
```
### 2️⃣ Abra no Android Studio

### 3️⃣ Configure a URL da API

```kotlin
const val BASE_URL = "https://api-receitas-pb3e.onrender.com/"
```
### 4️⃣ Execute o app em um emulador ou dispositivo físico 📱

---

## 👩‍💻 Autores

<table align="center">
  <tr>
    <td align="center" width="160" height="200" style="border:2px solid #ccc;">
      <img src="https://github.com/Liiiiisssz.png" width="115" height="115"><br>
      <sub><a href="https://github.com/Liiiiisssz">Elis Jasper</a><br><span style="color:#32CD32"><b>⚙️ Backend</b></span></sub>
    </td>
    <td align="center" width="160" height="200" style="border:2px solid #ccc;">
      <img src="https://github.com/KaelLuih.png" width="115" height="115"><br>
      <sub><a href="https://github.com/KaelLuih">Kael Luih de Araujo</a><br><span style="color:#FF69B4"><b>⚙️ Backend</b></span></sub>
    </td>
    <td align="center" width="160" height="200" style="border:2px solid #ccc;">
      <img src="https://github.com/Dudazabel.png" width="115" height="115"><br>
      <sub><a href="https://github.com/Dudazabel">Maria E. Zabel</a><br><span style="color:#32CD32"><b>⚙️ Backend</b></span></sub>
    </td>
  </tr>
</table>
