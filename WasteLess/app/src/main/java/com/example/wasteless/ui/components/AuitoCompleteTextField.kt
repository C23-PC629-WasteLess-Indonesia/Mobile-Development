package com.example.wasteless.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

@Composable
fun AutocompleteTextField(
    placesClient: PlacesClient,
    onPlaceSelected: (AutocompletePrediction) -> Unit,
){
    var searchText by remember {
        mutableStateOf("")
    }
    var predictions by remember {
        mutableStateOf<List<AutocompletePrediction>>(emptyList())
    }

    LaunchedEffect(searchText){
        val request = FindAutocompletePredictionsRequest.builder().setQuery(searchText).build()

        val response = placesClient.findAutocompletePredictions(request)
        if (response.isSuccessful){
            predictions = response.result.autocompletePredictions
            Log.d("ResponseAutofill","Autofill adaa")
        }else{

            Log.d("ResponseAutofill",response.exception?.message.toString())
        }
    }

    OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
        },
        placeholder = {
            Text(text = "lokasi kamu")
        },
        singleLine = true,

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary
        ),
        modifier = Modifier.fillMaxWidth(),
    )

    if (predictions.isNotEmpty()){
        Column {
            predictions.forEach{ prediction ->
                Text(
                    text = prediction.getPrimaryText(null).toString(),
                    modifier = Modifier.clickable {
                        onPlaceSelected(prediction)
                    }
                )
            }
        }
    }
}