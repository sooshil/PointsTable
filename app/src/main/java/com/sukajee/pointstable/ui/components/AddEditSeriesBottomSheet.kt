@file:OptIn(ExperimentalMaterial3Api::class)

package com.sukajee.pointstable.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.pointstable.data.model.Series

@Composable
fun AddEditSeriesBottomSheet(
    updateBottomSheetVisibility: (shouldShow: Boolean) -> Unit,
    inEditMode: Boolean = false,
    series: Series? = null
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

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
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(50) {
                ListItem(
                    headlineContent = {
                        Text("This is the headline content")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    },
                    supportingContent = {
                        Text("This is supporting content")
                    },
                    overlineContent = {
                        Text("This is overline content")
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add"
                        )
                    }
                )
            }
        }
    }
}