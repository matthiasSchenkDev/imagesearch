package com.example.imagesearch.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.imagesearch.R

@Composable
fun ErrorComponent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.margin_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = stringResource(
                id = R.string.error_hint
            )
        )
        Text(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_medium)),
            text = stringResource(id = R.string.error_hint)
        )
    }
}