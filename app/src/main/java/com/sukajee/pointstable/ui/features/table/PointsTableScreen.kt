package com.sukajee.pointstable.ui.features.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.sukajee.pointstable.data.model.PointTableHeader
import com.sukajee.pointstable.ui.components.DropDown

@Composable
fun PointsTableScreen(
    navController: NavController,
    viewModel: PointsTableViewModel,
    seriesId: Int?
) {
    var shouldFetchTableData by rememberSaveable {
        mutableStateOf(true)
    }
    if (shouldFetchTableData) {
        LaunchedEffect(key1 = true) {
            seriesId?.let { id ->
                viewModel.onEvent(
                    PointsTableUiEvents.GetTableData(
                        seriesId = id
                    )
                )
            }
        }
        shouldFetchTableData = false
    }

    val state by viewModel.uiState.collectAsState()

    StateLessPointsTableScreen(
        state = state,
        onBackArrowClick = {
            navController.popBackStack()
        },
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessPointsTableScreen(
    state: PointsTableUiState,
    onEvent: (PointsTableUiEvents) -> Unit,
    onBackArrowClick: () -> Unit
) {
    val listOfHeaders = listOf(
        PointTableHeader(name = "Teams", description = null, columnWidth = 4.0),
        PointTableHeader(name = "M", description = "The number of match played", columnWidth = 0.9),
        PointTableHeader(name = "W", description = "The number of matches won", columnWidth = 0.9),
        PointTableHeader(name = "L", description = "The number of matches lost", columnWidth = 0.9),
        PointTableHeader(name = "T", description = "The number of matches tied", columnWidth = 0.9),
        PointTableHeader(
            name = "N/R",
            description = "The number of matches abandoned",
            columnWidth = 0.9
        ),
        PointTableHeader(name = "PT", description = "Number of points awarded", columnWidth = 0.9),
        PointTableHeader(name = "NRR", description = "Net run rate", columnWidth = 1.5)
    )
    val headers by remember {
        mutableStateOf(listOfHeaders)
    }
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
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            if (state.isMatchDataEmpty && !state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Card {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "No match data is available for this series to display points table. Please make sure to enter match data for the series.",
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
            if (!state.isLoading && !state.isMatchDataEmpty) {
                DropDown(
                    defaultText = state.seriesList.firstOrNull { it.id == state.currentSeriesId }?.seriesName ?: "",
                    itemList = state.seriesList,
                ) { selectedSeriesId ->
                    onEvent(
                        PointsTableUiEvents.GetTableData(
                            seriesId = selectedSeriesId
                        )
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .border(0.5.dp, MaterialTheme.colorScheme.onSurface)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .background(Color(0x798DCEFF)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(headers.toList().size) {
                                Text(
                                    modifier = Modifier
                                        .weight(headers[it].columnWidth.toFloat())
                                        .border(0.3.dp, MaterialTheme.colorScheme.onSurface),
                                    text = headers[it].name,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
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
                                    .align(Alignment.CenterHorizontally)
                                    .background(
                                        color = if (index % 2 == 0) Color.Transparent else Color(
                                            0x79D2FCE8
                                        )
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier
                                        .weight(headers[0].columnWidth.toFloat())
                                        .border(0.3.dp, MaterialTheme.colorScheme.onSurface)
                                        .padding(start = 8.dp, top = 2.dp, bottom = 2.dp),
                                    text = state.pointTableRows[index].teamName,
                                    textAlign = TextAlign.Start,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    modifier = Modifier
                                        .weight(headers[1].columnWidth.toFloat())
                                        .border(0.3.dp, MaterialTheme.colorScheme.onSurface)
                                        .padding(vertical = 2.dp),
                                    text = state.pointTableRows[index].played.toString(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier
                                        .weight(headers[2].columnWidth.toFloat())
                                        .border(0.3.dp, MaterialTheme.colorScheme.onSurface)
                                        .padding(vertical = 2.dp),
                                    text = state.pointTableRows[index].won.toString(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier
                                        .weight(headers[3].columnWidth.toFloat())
                                        .border(0.3.dp, MaterialTheme.colorScheme.onSurface)
                                        .padding(vertical = 2.dp),
                                    text = state.pointTableRows[index].lost.toString(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier
                                        .weight(headers[4].columnWidth.toFloat())
                                        .border(0.3.dp, MaterialTheme.colorScheme.onSurface)
                                        .padding(vertical = 2.dp),
                                    text = state.pointTableRows[index].drawn.toString(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier
                                        .weight(headers[5].columnWidth.toFloat())
                                        .border(0.3.dp, MaterialTheme.colorScheme.onSurface)
                                        .padding(vertical = 2.dp),
                                    text = state.pointTableRows[index].noResult.toString(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier
                                        .weight(headers[6].columnWidth.toFloat())
                                        .border(0.3.dp, MaterialTheme.colorScheme.onSurface)
                                        .padding(vertical = 2.dp),
                                    text = state.pointTableRows[index].points.toString(),
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier
                                        .weight(headers[7].columnWidth.toFloat())
                                        .border(0.3.dp, MaterialTheme.colorScheme.onSurface)
                                        .padding(vertical = 2.dp),
                                    text = String.format(
                                        "%.3f",
                                        state.pointTableRows[index].netRunRate
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .scrollable(
                            rememberScrollState(),
                            orientation = Orientation.Vertical
                        )
                        .padding(8.dp)
                ) {
                    repeat(headers.size - 1) {
                        Text(
                            text = "${headers[it + 1].name} - ${headers[it + 1].description}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}