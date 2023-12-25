/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Sunday, 24 Dec, 2023.
 */

package com.sukajee.pointstable.utils

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ExtensionsTest {

    /**
     * Parameterized test for the 'getNumberOfMatches' function.
     * Each row in the @CsvSource represents a set of parameters for testing.
     *
     * Parameters:
     * @param numberOfTeams: The total number of teams in the competition.
     * @param roundRobinTimes: The number of times a round-robin tournament is conducted.
     * @param expectedNumberOfMatches: The expected number of matches calculated for the given inputs.
     *
     * Test Scenario:
     * The 'getNumberOfMatches' function is tested with various inputs to ensure it correctly calculates
     * the number of matches based on the number of teams and the number of times a round-robin tournament
     * is conducted. The calculated number of matches is then compared to the expected value.
     */

    @ParameterizedTest()
    @CsvSource(
        "2, 1, 1",
        "2, 2, 2",
        "3, 1, 3",
        "3, 2, 6",
        "4, 1, 6",
        "4, 2, 12",
        "10, 1, 45",
        "10, 2, 90",
        "12, 1, 66",
        "12, 2, 132",
        "-3, 1, 0",
        "-3, 2, 0",
        "0, 1, 0",
        "0, 2, 0"
    )
    fun `test getNumberOfMatches returns correct numberOfMatches for all possible inputs`(
        numberOfTeams: Int,
        roundRobinTimes: Int,
        expectedNumberOfMatches: Int
    ) {
        val numberOfMatches = numberOfTeams.getNumberOfMatches(roundRobinTimes)
        assert(numberOfMatches == expectedNumberOfMatches)
    }

    @ParameterizedTest
    @CsvSource(
        "2,3,4,8,9;    4; 2,3,4,8,9",
        "2,3,4,8,9;   12; 2,3,4,8,9,12",
        "2,3,4,8,9; 3; 2,3,4,8,9",
        "2,3,4,8,9; 1; 2,3,4,8,9,1",
        delimiter = ';'
    )
    fun `test insertSeriesId saves seriesId correctly`(
        seriesIdList: String,
        newSeriesId: String,
        expectedSeriesIdList: String
    ) {
        val actualSeriesIdList = seriesIdList.insertSeriesId(newSeriesId)
        assert(actualSeriesIdList == expectedSeriesIdList)
    }
}