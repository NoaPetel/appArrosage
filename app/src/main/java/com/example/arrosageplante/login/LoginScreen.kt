package com.example.arrosageplante.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

val auth: FirebaseAuth by lazy {
    FirebaseAuth.getInstance()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignIn: () -> Unit,
    onNavigateToMenu: () -> Unit
){


    Scaffold { paddingValues ->
        LoginContent(
            modifier = modifier.padding(paddingValues),
            onNavigateToSignIn = onNavigateToSignIn,
            onNavigateToMenu = onNavigateToMenu
        )
    }
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    onNavigateToSignIn: () -> Unit,
    onNavigateToMenu: () -> Unit
){
    var mail by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        // horizontalAlignment = Alignment.CenterHorizontally
    ){

        TextField(
            value = mail,
            onValueChange = {newText ->
                mail = newText
            },
            label = { Text("Email") },
            modifier = modifier.fillMaxWidth(),
        )

        TextField(
            value = password,
            onValueChange = {newText ->
                password = newText
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = modifier.fillMaxWidth()
            )

        val onFailure: (Exception) -> Unit = {}
        Button(
            modifier = modifier
                .fillMaxWidth(),
            onClick = {
                signIn(email = mail, password = password, onSuccess = onNavigateToMenu, onFailure = onFailure)
                Log.d("ViewLogin", "Connexion cliqué")
            }
        ) {
            Text(text = "Connexion")
        }

        Text(
            text = "Mot de passe oublié ?",
            modifier = modifier
                .clickable(){
                    Log.d("LoginScreen", "Mot de passe oublié cliqué")

                }
        )


        HorizontalDivider(
            color = Color.Gray,
            thickness = 1.dp
        )


        Button(
            modifier = modifier
                .fillMaxWidth(),
            onClick = {
                onNavigateToSignIn();
                Log.d("ViewLogin", "Créer compte cliqué")
            }
        ) {
            Text(text = "Créer un compte")
        }
    }
}

fun signIn(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                task.exception?.let { onFailure(it) }
            }
        }
}