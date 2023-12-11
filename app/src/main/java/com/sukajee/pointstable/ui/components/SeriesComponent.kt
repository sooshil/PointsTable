package com.sukajee.pointstable.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.pointstable.data.model.Series
import com.sukajee.pointstable.ui.features.main.DismissBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesComponent(
    series: Series,
    onDeleteSeries: (Series) -> Unit,
    onCardClick: () -> Unit,
    onTableClick: () -> Unit,
    onEnterDataClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentItem by rememberUpdatedState(series)
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart /*|| it == DismissValue.DismissedToEnd*/) {
                onDeleteSeries(currentItem)
                true
            } else false
        },
        positionalThreshold = { 250.dp.toPx() }
    )
    SwipeToDismiss(
        state = dismissState,
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
        background = {
            DismissBackground(dismissState)
        },
        dismissContent = {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        onCardClick()
                    },
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFFE1FDEC))
                        .fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = 20.sp,
                        color = Color.Black.copy(0.8f),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        text = series.seriesName
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = 12.sp,
                        color = Color.Black.copy(0.6f),
                        textAlign = TextAlign.Start,
                        text = "${series.teamCount} teams | ${series.numberOfGames} games"
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(49.5f)
                                .background(color = Color.Green.copy(0.3f))
                                .clickable { onTableClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    vertical = 16.dp,
                                    horizontal = 8.dp
                                ),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                fontSize = 16.sp,
                                text = "Table",
                                color = Color.Black.copy(0.9f)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(color = Color.Red)
                                .fillMaxHeight()
                        )
                        Box(
                            modifier = Modifier
                                .weight(49.5f)
                                .background(color = Color.Green.copy(0.3f))
                                .clickable { onEnterDataClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    vertical = 16.dp,
                                    horizontal = 8.dp
                                ),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                fontSize = 16.sp,
                                text = "Enter data",
                                color = Color.Black.copy(0.9f)
                            )
                        }
                    }
                }
            }
        }
    )
}