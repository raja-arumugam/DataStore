package com.example.datastore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(dataStore: DataStore<Preferences>) {

    var storedValue by remember {
        mutableStateOf("")
    }

    var et_key by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var et_value by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var key by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(value = et_key,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            label = {
                Text(text = "Enter the Key")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Lock, contentDescription = "KeyIcon")
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                focusedLeadingIconColor = Color.Black,
                unfocusedLeadingIconColor = Color.Gray
            ),
            onValueChange = {
                et_key = it
            })


        OutlinedTextField(value = et_value,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            maxLines = 1,
            label = {
                Text(text = "Enter the Value")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Create, contentDescription = "ValueIcon")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                focusedLeadingIconColor = Color.Black,
                unfocusedLeadingIconColor = Color.Gray
            ),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            shape = RoundedCornerShape(15.dp),
            onValueChange = {
                et_value = it
            })

        Spacer(modifier = Modifier.padding(8.dp))


        // STORE DATASTORE VALUES BUTTON
        Button(
            modifier = Modifier
                .padding(16.dp)
                .height(50.dp)
                .width(200.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color.Black),
            elevation = ButtonDefaults.buttonElevation(10.dp),
            onClick = {

                CoroutineScope(Dispatchers.IO).launch {
                    val dataStoreKey = stringPreferencesKey(et_key.toString())
                    dataStore.edit {
                        it[dataStoreKey] = et_value.text
                    }
                }

            }) {
            Text(
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                text = "Submit".uppercase(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                )
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))


        // GET DATASTORE VALUES View's
        OutlinedTextField(value = key,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            label = {
                Text(text = "Enter the Key")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Lock, contentDescription = "KeyIcon")
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                focusedLeadingIconColor = Color.Black,
                unfocusedLeadingIconColor = Color.Gray
            ),
            onValueChange = {
                key = it
            })

        Spacer(modifier = Modifier.padding(8.dp))

        // GET DATASTORE VALUES BUTTON
        Button(
            modifier = Modifier
                .padding(16.dp)
                .height(50.dp)
                .width(200.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color.Black),
            elevation = ButtonDefaults.buttonElevation(10.dp),
            onClick = {

                CoroutineScope(Dispatchers.IO).launch {
                    val dataStoreKey = stringPreferencesKey(key.toString())
                    val preferences = dataStore.data.first()
                    preferences[dataStoreKey].let {
                        it?.let {
                            storedValue = it
                        }
                    }
                }

            }) {
            Text(
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                text = "Show".uppercase(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                )
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            text = storedValue ?: "No Data Available",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
            )
        )
    }
}