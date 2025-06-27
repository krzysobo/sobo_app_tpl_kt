package com.krzysobo.soboapptpl.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun PageHeader(textHeader: String) {
    Text(
        textHeader,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 15.dp)
    )
}


@Composable
fun PageHeaderTight(textHeader: String) {
    Text(
        textHeader,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 5.dp, top = 0.dp)
    )
}
