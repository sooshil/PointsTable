/*
 * Copyright (c) 2023-2024, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Monday, 01 Jan, 2024.
 */

package com.sukajee.pointstable.ui.features.dataentryhelp

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sukajee.pointstable.R
import com.sukajee.pointstable.data.model.ParagraphItem
import com.sukajee.pointstable.ui.components.IndentedParagraph
import com.sukajee.pointstable.utils.BulletStyle
import com.sukajee.pointstable.utils.IndentLevel

@Composable
fun DataEntryHelpScreen(
    navController: NavController,
    viewModel: DataEntryHelpViewModel
) {
    var isDataSet by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    if (isDataSet.not()) {
        LaunchedEffect(key1 = true) {
            isDataSet = true
            viewModel.updateParagraphs(
                getHelpParagraphs(context = context)
            )
        }
    }
    val state by viewModel.uiState.collectAsState()

    StateLessDataEntryHelpScreen(
        state = state,
        onBackArrowClick = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessDataEntryHelpScreen(
    state: DataEntryHelpUiState,
    onBackArrowClick: () -> Unit,
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
                .imePadding()
                .paint(
                    painterResource(id = R.drawable.player),
                    contentScale = ContentScale.FillWidth,
                    alpha = 0.07f
                )
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Data Entry Help",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
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
                            contentDescription = "Navigate back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                items(
                    items = state.paragraphs
                ) { paragraphItem ->
                    IndentedParagraph(
                        text = paragraphItem.text,
                        isBold = paragraphItem.isBold,
                        itemNumber = paragraphItem.itemNumber,
                        bulletStyle = paragraphItem.bulletStyle,
                        indentLevel = paragraphItem.indentLevel
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

fun getHelpParagraphs(context: Context) =
    listOf(
        ParagraphItem(
            text = "To ensure the accuracy of the points table in a cricket tournament, please consider the following while entering each match data:",
            itemNumber = null,
            bulletStyle = BulletStyle.None,
            indentLevel = IndentLevel.None,
        ),
        ParagraphItem(
            text =
            "Entering the runs:",
            itemNumber = "1",
            isBold = true,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.None,
        ),
        ParagraphItem(
            text = "If D/L Method is not applied, enter the runs scored by each team in the correct run entry field.",
            itemNumber = "a",
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First,
        ),
        ParagraphItem(
            text = "If the D/L Method is applied then the runs that should be entered for each team may be different from the runs they actually scored. If the D/L target is set for the team batting second (Team B), then the first team’s run needs to be entered one less run than the D/L target, regardless of the runs team A has already scored.",
            itemNumber = "b",
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First,
        ),
        ParagraphItem(
            text = "For example, if Team A scored 350 runs in 50 overs and for some reason D/L method needs to be applied to set the target for Team B. And from the D/L method, it is determined that the target for Team B would be 200 runs in 40 overs. In this case, please enter 199 runs as Team A’s run score, even though team A has scored 350 runs.",
            itemNumber = null,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First,
        ),
        ParagraphItem(
            text = "Entering the overs:",
            itemNumber = "2",
            isBold = true,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.None,
        ),
        ParagraphItem(
            text = "While entering the overs, please enter the whole over to the Overs field and balls to the Balls field. For example, if a team faced 45 overs and 3 balls, even though it calculates 45.5 in decimal, please enter 45 in the Overs and 3 in the Balls field and not 45.5 overs.",
            itemNumber = "a",
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First,
        ),
        ParagraphItem(
            text = "If a team gets bowled out then the full quota of the overs needs to be entered for that team. For example, in a 50 overs match, if Team A gets bowled out scoring 350 runs in 40 overs 3 balls, then while entering the data, we need to enter 350 runs and 50 overs. If the second team chased the target (350) in 30 overs, then for team B, we only need to enter 30 overs.",
            itemNumber = "b",
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First,
        ),
        ParagraphItem(
            text = "If the D/L Method is applied then the overs that should be entered for each team may be different from the overs they actually faced or bowled. If the D/L target is set for the team batting second (Team B), then the first team’s overs needs to be entered the same as the overs allowed for Team B to get the revised target.",
            itemNumber = "c",
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First,
        ),
        ParagraphItem(
            text = "For example, if Team A scored 350 runs in 50 overs and for some reason D/L method needs to be applied to set the target for Team B. And from the D/L method, it is determined that the target for Team B would be 200 runs in 40 overs. In this case, please enter 199 runs and 40 overs as Team A’s score, even though team A has scored 350 runs and faced 50 overs.",
            itemNumber = null,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First,
        ),
        ParagraphItem(
            text = "Match ending in tie/Super Over scenario:",
            itemNumber = "3",
            isBold = true,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.None
        ),
        ParagraphItem(
            text = "Usually, the result of the limited overs games won’t be a tie. If the scores of the two teams are equal, then there will usually be a super over to break the tie. While entering the data for such a match, please enter the runs and overs for each team and don’t forget to mark the winner in the super over. Super over runs/overs/wickets will not be considered for the points table calculations.",
            itemNumber = "a",
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First
        ),
        ParagraphItem(
            text = "Match abandoned scenario:",
            itemNumber = "4",
            isBold = true,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.None
        ),
        ParagraphItem(
            text = "If a match had no result due to reasons like rain or poor light etc, then any runs collected or overs bowled before the match was abandoned will not be accounted for. So, if a match ended with a no-result, please check the checkbox for No Result else the point table may not be accurate.",
            itemNumber = "a",
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First
        )
    )

