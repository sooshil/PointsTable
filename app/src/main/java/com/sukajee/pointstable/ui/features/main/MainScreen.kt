package com.sukajee.pointstable.ui.features.main

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sukajee.pointstable.data.model.Series
import com.sukajee.pointstable.data.model.Team
import com.sukajee.pointstable.ui.components.SeriesComponent
import kotlinx.coroutines.launch
import kotlin.random.Random


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val state by viewModel.uiState.collectAsState()
    StateLessMainScreen(
        state = state,
        onEvent = {
            viewModel.onEvent(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessMainScreen(
    state: MainScreenUiState,
    onEvent: (MainScreenUiEvents) -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(
                    start = it.calculateStartPadding(LayoutDirection.Rtl),
                    end = it.calculateEndPadding(LayoutDirection.Rtl)
                )
        ) {
            var showMenu by remember {
                mutableStateOf(false)
            }
            val scope = rememberCoroutineScope()
            var sheetState = rememberModalBottomSheetState()
            var shouldShowBottomSheet by rememberSaveable {
                mutableStateOf(false)
            }

            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Games",
                        fontSize = 24.sp,
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
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            shouldShowBottomSheet = true
//                            onEvent(
//                                MainScreenUiEvents.OnInsertSeriesClick(
//                                    series = Series(
//                                        name = "Series number ${Random.nextInt(100, 500)}",
//                                        startDate = System.currentTimeMillis().toString(),
//                                        teamIds = List(Random.nextInt(3, 9)) {
//                                            (1..1000).shuffled().first()
//                                        },
//                                        roundRobinTimes = Random.nextInt(1, 3),
//                                        completed = false,
//                                        hidden = false
//                                    )
//                                )
//                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Create game"
                        )
                    }
                    IconButton(
                        onClick = {
                            showMenu = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Create game"
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = {
                            showMenu = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = "Sort by created date")
                            },
                            onClick = {

                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                items(items = state.series) { series ->
                    SeriesComponent(
                        series = series,
                        onCardClick = {},
                        onTableClick = {},
                        onEnterDataClick = {}
                    )
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
            if (shouldShowBottomSheet) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        shouldShowBottomSheet = false
                    },
                    dragHandle = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BottomSheetDefaults.DragHandle()
                            Spacer(Modifier.height(8.dp))
                            Text("Create a Series")
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
        }
    }
}