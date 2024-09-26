package com.example.myapplication.loginpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun LoginScreen() {
    var emailAddress by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.login_image),
            contentDescription = "login image",
            modifier = Modifier.size(width = 240.dp, height = 240.dp)
            )
        Text(text = "Login",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            )

        Spacer(modifier = Modifier.height(24.dp))

        val commonModifier = Modifier
            .fillMaxWidth(0.9f)

        OutlinedTextField(
            value = emailAddress,
            onValueChange = {emailAddress = it},
            modifier = commonModifier,
            label = {
                Text(text = "Email address")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            modifier = commonModifier,
            label = {
                Text(text = "Password")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        phone(commonModifier)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {}) {
            Text("Submit",
                fontSize = 18.sp
                )
        }

    }
}

@Composable
fun phone(commonModifier: Modifier) {
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var selectedCountryCode by remember { mutableStateOf("+1") }
    val countryCodes = listOf("+1", "+44", "+91", "+81", "+49", "+44", "+91", "+81", "+49", "+44", "+91", "+81", "+49")

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountryCodeDropdown(
            selectedCountryCode = selectedCountryCode,
            onCountryCodeSelected = { selectedCountryCode = it },
            countryCodes = countryCodes
        )
        Spacer(modifier = Modifier.width(4.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = commonModifier,
        )
    }
}

@Composable
fun CountryCodeDropdown(
    selectedCountryCode: String,
    onCountryCodeSelected: (String) -> Unit,
    countryCodes: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedButton(onClick = { expanded = !expanded }) {
            Text(text = selectedCountryCode)
        }

        if (expanded) {
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .width(100.dp) // Set a fixed width for the dropdown
                    .padding(top = 4.dp) // Add padding to separate from the button
            ) {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 180.dp) // Set a maximum height
                ) {
                    items(countryCodes) { code ->
                        Text(
                            text = code,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onCountryCodeSelected(code)
                                    expanded = false
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen(){
    LoginScreen()
}