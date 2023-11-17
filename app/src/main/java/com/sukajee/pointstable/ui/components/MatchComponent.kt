package com.sukajee.pointstable.ui.components

import android.graphics.Paint.Align
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.pointstable.data.model.Match

@Composable
fun MatchComponent(
    match: Match,
    onCardClick: () -> Unit,
    onTableClick: () -> Unit,
    onEnterDataClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 20.sp,
                color = Color.Black.copy(0.8f),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                text = match.name
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 12.sp,
                color = Color.Gray.copy(0.9f),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                text = match.venue
            )

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = Color.Blue.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(2.dp)
                            )
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            fontSize = 10.sp,
                            color = Color.White.copy(0.9f),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            text = match.numberOfTeams.toString(),
                        )
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        fontSize = 12.sp,
                        color = Color.Blue.copy(0.9f),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        text = "teams",
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = Color.Blue.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(2.dp)
                            )
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            fontSize = 10.sp,
                            color = Color.White.copy(0.9f),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            text = match.numberOfGames.toString(),
                        )
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        fontSize = 12.sp,
                        color = Color.Blue.copy(0.9f),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        text = "games",
                    )
                }
                Text(
                    modifier = Modifier
                        .clickable {
                            onTableClick()
                        },
                    text = "Table",
                    fontSize = 12.sp,
                    color = Color.Blue.copy(0.9f),
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}
