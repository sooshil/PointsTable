package com.sukajee.pointstable.ui.features.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PointsTableScreen(
    navController: NavController,
    viewModel: PointsTableViewModel,
    seriesId: Int?
) {
    LaunchedEffect(key1 = true) {
        seriesId?.let {
            viewModel.getTableData(seriesId = it)
        }
    }

    val state by viewModel.uiState.collectAsState()

    StateLessPointsTableScreen(
        state = state,
        onBackArrowClick = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessPointsTableScreen(
    state: PointsTableUiState,
    onBackArrowClick: () -> Unit
) {

    Scaffold { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(
                    start = it.calculateStartPadding(LayoutDirection.Rtl),
                    end = it.calculateEndPadding(LayoutDirection.Rtl)
                )
                .imePadding()
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Points Table",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.03f
                            ), shape = CircleShape
                        ),
                        onClick = onBackArrowClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
            val headers = mapOf(
                "Teams" to 6,
                "M" to 1,
                "W" to 1,
                "L" to 1,
                "T" to 1,
                "N/R" to 1,
                "PT" to 1,
                "NRR" to 1
            )
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        repeat(headers.toList().size) {
                            Text(
                                modifier = Modifier
                                    .weight(
                                        headers
                                            .toList()
                                            .get(it).second.toFloat() ?: 1f
                                    )
                                    .border(1.dp, Color.Red),
                                text = headers.toList().get(it).first.toString(),
                                textAlign = if (it == 0) TextAlign.Start else TextAlign.Center
                            )
                        }
                    }
                }
                repeat(
                    state.pointTableRows.size
                ) { index ->
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(
                                    headers.toList().get(0).second.toFloat() ?: 1f
                                ),
                                text = state.pointTableRows.get(index).teamName.toString(),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                modifier = Modifier.weight(
                                    headers.toList().get(1).second.toFloat() ?: 1f
                                ),
                                text = state.pointTableRows.get(index).played.toString(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.weight(
                                    headers.toList().get(2).second.toFloat() ?: 1f
                                ),
                                text = state.pointTableRows.get(index).won.toString(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.weight(
                                    headers.toList().get(3).second.toFloat() ?: 1f
                                ),
                                text = state.pointTableRows.get(index).lost.toString(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.weight(
                                    headers.toList().get(4).second.toFloat() ?: 1f
                                ),
                                text = state.pointTableRows.get(index).drawn.toString(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.weight(
                                    headers.toList().get(5).second.toFloat() ?: 1f
                                ),
                                text = state.pointTableRows.get(index).noResult.toString(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.weight(
                                    headers.toList().get(6).second.toFloat() ?: 1f
                                ),
                                text = state.pointTableRows.get(index).points.toString(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.weight(
                                    headers.toList().get(7).second.toFloat() ?: 1f
                                ),
                                text = state.pointTableRows.get(index).netRunRate.toString(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

}