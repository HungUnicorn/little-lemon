package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.littlelemonlogo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Let's get to know you")
        Spacer(modifier = Modifier.height(16.dp))

        val firstNameState = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = firstNameState.value ,
            onValueChange = { firstNameState.value = it },
            label = { Text("First Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        val lastNameState = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = lastNameState.value,
            onValueChange = { lastNameState.value = it },
            label = { Text("Last Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        val emailState = remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email Address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (firstNameState.value.text.isBlank() || lastNameState.value.text.isBlank() || emailState.value.text.isBlank()) {
                Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
            } else {
                saveToSharedPreferences(context, firstNameState.value.text,
                    lastNameState.value.text, emailState.value.text)
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                navController.navigate("Home")
            }

        }) {
            Text("Register")
        }

    }
}

fun saveToSharedPreferences(context: Context, firstName: String, lastName: String, email: String) {
    val sharedPref = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE) ?: return
    with(sharedPref.edit()) {
        putString("firstName", firstName)
        putString("lastName", lastName)
        putString("email", email)
        apply()
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    Onboarding(navController)
}
