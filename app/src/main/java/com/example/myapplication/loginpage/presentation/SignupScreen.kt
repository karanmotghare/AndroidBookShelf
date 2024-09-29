package com.example.myapplication.loginpage.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.*
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.R
import com.example.myapplication.loginpage.data.models.CountryListDataModel
import com.example.myapplication.loginpage.data.models.UserIpLocationDataModel
import com.example.myapplication.loginpage.viewmodels.AuthState
import com.example.myapplication.loginpage.viewmodels.AuthViewModel
import com.example.myapplication.loginpage.viewmodels.CountryListViewModel
import com.example.myapplication.loginpage.viewmodels.UserIpLocationViewModel

@Composable
fun SignupScreen(navController: NavController, authViewModel: AuthViewModel, countryListViewModel: CountryListViewModel, userIpLocationViewModel: UserIpLocationViewModel) {
    var emailAddress by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
    val countryListState by countryListViewModel.countryListData.observeAsState()
    val userIpState by userIpLocationViewModel.ipLocationData.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).msg,Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        countryListViewModel.getCountryListData()
        userIpLocationViewModel.getIpLocationData()
    }

    when(val result = countryListState){
        is LibraryResultEvent.OnFailure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Something went wrong")
            }
        }
        LibraryResultEvent.OnLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is LibraryResultEvent.OnSuccess -> {
            Log.d("SignupScreen","data : ${result.data}")

            when(val userIp = userIpState){
                is LibraryResultEvent.OnFailure -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Something went wrong")
                    }
                }
                LibraryResultEvent.OnLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is LibraryResultEvent.OnSuccess -> {
                    UiScreen(
                        emailAddress = emailAddress,
                        onEmailChange = {emailAddress = it},
                        password = password,
                        onPasswordChange = {password = it},
                        authViewModel = authViewModel,
                        navController = navController,
                        countries = result.data,
                        defaultCountry = userIp.data
                    )
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Please wait...")
                    }
                }
            }

        }
        else ->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Please wait...")
            }
        }
    }


}

@Composable
fun UiScreen(
    emailAddress: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit,
    password: TextFieldValue,
    onPasswordChange: (TextFieldValue) -> Unit,
    authViewModel: AuthViewModel,
    navController: NavController,
    countries: List<CountryListDataModel>,
    defaultCountry: UserIpLocationDataModel
) {
    var selectedCountry by remember { mutableStateOf(defaultCountry.country ?: "Select Country") }
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$".toRegex()
    val passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*()])(?=.*[a-z])(?=.*[A-Z]).{8,}$".toRegex()
    val context = LocalContext.current

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
        Text(text = "Create account",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Spacer(modifier = Modifier.height(24.dp))

        val commonModifier = Modifier
            .fillMaxWidth(0.9f)

        OutlinedTextField(
            value = emailAddress,
            onValueChange = onEmailChange,
            modifier = commonModifier,
            label = {
                Text(text = "Email address")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = commonModifier,
            visualTransformation = PasswordVisualTransformation(),
            label = {
                Text(text = "Password")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CountryDropDown(commonModifier = commonModifier,
            countries = countries,
            country = defaultCountry.country,
            onCountrySelected = {selectedCountry = it})

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if(emailRegex.matches(emailAddress.text) && passwordRegex.matches(password.text)){
                authViewModel.signUp(emailAddress.text, password.text, selectedCountry)
            }else if (!emailRegex.matches(emailAddress.text) && passwordRegex.matches(password.text)){
                Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            }else if(emailRegex.matches(emailAddress.text) && !passwordRegex.matches(password.text)){
                Toast.makeText(context, "Please enter a valid password", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Please enter a valid email address and password", Toast.LENGTH_SHORT).show()
            }

        }) {
            Text("Sign up",
                fontSize = 18.sp
            )
        }

        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(text = "Have an account, Login")
        }
    }
}

@Composable
fun CountryDropDown(
    commonModifier: Modifier,
    countries: List<CountryListDataModel>,
    country: String,
    onCountrySelected: (String) -> Unit
) {
//    val countries = listOf("United States", "Canada", "Germany", "India", "Australia", "Japan", "France")
    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf(country ?: "Select Country") }

    Column {
        OutlinedTextField(
            value = selectedCountry,
            onValueChange = { },
            readOnly = true,
            label = { Text("Country") },
            modifier = commonModifier.clickable { expanded = true },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            countries.forEach { country ->
                DropdownMenuItem(
                    text = {Text(country.country)},
                    onClick = {
                        selectedCountry = country.country
                        expanded = false
                        onCountrySelected(country.country)
                    }
                )
            }
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

//@Preview
//@Composable
//fun PreviewSignupScreen(){
//    SignupScreen()
//}