package com.compfest16.sea_salon.features.presentation.component.widget

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.compfest16.sea_salon.R
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.list.ListDialog
import com.maxkeppeler.sheets.list.models.ListOption
import com.maxkeppeler.sheets.list.models.ListSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectType(closeSelection: (String) -> Unit, currentSelection: String) {
    val options = listOf(
        ListOption(
            titleText = "Haircuts and styling",
            selected = if (currentSelection == "Haircuts and styling") true else false
        ),
        ListOption(
            titleText = "Manicure and pedicure",
            selected = if (currentSelection == "Manicure and pedicure") true else false
        ),
        ListOption(
            titleText = "Facial treatments",
            selected = if (currentSelection == "Facial treatments") true else false
        )
    )

    ListDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = {  }),
        selection = ListSelection.Single(
            showRadioButtons = true,
            options = options
        ) { index, option ->
            closeSelection(option.titleText)
        }
    )
}