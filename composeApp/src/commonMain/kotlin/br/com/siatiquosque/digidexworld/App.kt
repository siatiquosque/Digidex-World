package br.com.siatiquosque.digidexworld

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import br.com.siatiquosque.digidexworld.presentation.ui.main.MainNav

@Composable
@Preview
fun App() {
    KoinContext {

        MainNav()
//        val digimons = koinInject<DigimonWorld1Database>()
//
//        var digi by remember { mutableStateOf(listOf<Digimon>()) }
//
//
//        LaunchedEffect(Unit) {
//            // Call the suspend function using launch
//            val result = withContext(Dispatchers.IO) {
//                digimons.digimonsDao().searchByName("Agu")
//            }
//            digi = result
//        }
//        MaterialTheme {
//            LazyColumn {
//                items(digi){
//                    Text(it.digimon?.name.toString())
//                }
//            }
//                Text(digi.digimon?.name.toString())
//                digi.to?.let {
//                    LazyColumn {
//                        item {
//                            Text(digi.digimon?.name.toString())
//                        }
//                        items(it) {
//                            Text(it.name.toString())
//                        }
//                    }
//                }
//        }


//        var showContent by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
    }
}