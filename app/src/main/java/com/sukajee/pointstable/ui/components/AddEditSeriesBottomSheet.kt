@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.sukajee.pointstable.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.pointstable.data.model.Series

@Composable
fun AddEditSeriesBottomSheet(
    inEditMode: Boolean = false,
    series: Series? = null,
    updateBottomSheetVisibility: (shouldShow: Boolean) -> Unit,
    onCreateSeriesClicked: (series: Series) -> Unit,
    onUpdateSeriesClicked: (series: Series) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var seriesName by rememberSaveable {
        if (inEditMode) {
            mutableStateOf(series?.name ?: "")
        } else {
            mutableStateOf("")
        }
    }
    var numberOfTeams by rememberSaveable {
        if (inEditMode) {
            mutableIntStateOf(series?.teamCount ?: 0)
        } else {
            mutableIntStateOf(0)
        }
    }
    var teams by rememberSaveable {
        if (inEditMode) {
            mutableStateOf(series?.improvedTeams ?: emptyList())
        } else {
            mutableStateOf(emptyList())
        }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            updateBottomSheetVisibility(false)
        },
        dragHandle = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomSheetDefaults.DragHandle()
                Text(
                    text = if (inEditMode) "Edit Series" else "Create a Series",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Divider()
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = seriesName,
                    onValueChange = {
                        seriesName = it
                    },
                    maxLines = 1,
                    label = {
                        Text(text = "Series Name")
                    }
                )
            }
            items(numberOfTeams) { index ->
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = teams[index],
                    onValueChange = { team ->
                        val existingTeams = teams.toMutableList()
                        existingTeams[index] = team
                        teams = existingTeams.toList()
                    },
                    maxLines = 1,
                    label = {
                        Text(text = "Series Name")
                    }
                )
            }
        }
    }
}