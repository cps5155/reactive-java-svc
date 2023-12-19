package com.schmitt.reactive.java.src.regex;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class StreakMatchingRegexTest {
    private static final Pattern STREAK_PATTERN  = Pattern.compile("^(W|L)(\\d+)(.*)", Pattern.CASE_INSENSITIVE);
    @ParameterizedTest
    @CsvSource(nullValues = "", value = {
            "true, W, 1,",
            "true, L, 1234,",
            "true, W, 10, T",
            "false,,0,",
            "false, A, 12,",
            "false, Win, 1,",
            "false, AW, 12, any",
            "false,,,",
            "false, WL, 12, *",
            "true, W, 12, L2T12",
            "true, W, 12, llllllz",
            "true, w, 12, nothing1",
            "true, w, 1, .l1.lt"
    })
    void group(boolean expectedMatches,
               @Nullable String winLossString, @Nullable Integer winLossCount,
               @Nullable String extraCharacters) {
        // arrange
        final StringBuilder stringBuilder = new StringBuilder();
        Optional.ofNullable(winLossString).ifPresent(stringBuilder::append);
        Optional.ofNullable(winLossCount).map(String::valueOf).ifPresent(stringBuilder::append);
        Optional.ofNullable(extraCharacters).ifPresent(stringBuilder::append);
        final String stringToTest = stringBuilder.toString();
        final Matcher matcher = STREAK_PATTERN.matcher(stringToTest);

        // act & assert
        final Optional<Streak> result = getStreak(stringToTest);

        if (!expectedMatches) {
            assertThat(matcher.find()).isFalse();
            assertThat(matcher.matches()).isFalse();
            assertThat(result).isEmpty();
        } else {
            assertThat(matcher.find()).isTrue();
            assertThat(matcher.matches()).isTrue();
            assertThat(matcher.groupCount()).isGreaterThanOrEqualTo(2);
            assertThat(matcher.group()).isEqualTo(stringToTest);
            assertThat(matcher.group(1)).isEqualTo(winLossString);
            assertThat(matcher.group(2)).isEqualTo(String.valueOf(winLossCount));
            assertThat(result).hasValueSatisfying(res -> {
                assertThat(res.winLossDesignation).isEqualTo(winLossString.charAt(0));
                assertThat(res.gamesCount).isEqualTo(winLossCount);
            });
        }
    }

    protected Optional<Streak> getStreak(String stringToTest) {
        final Matcher matcher = STREAK_PATTERN.matcher(stringToTest);

        if (matcher.matches()) {
            try {
                final char winLossDesignator = matcher.group(1).charAt(0);
                final int gamesCount = Integer.parseInt(matcher.group(2));

                return Optional.of(new Streak(winLossDesignator, gamesCount));
            } catch (NumberFormatException | IllegalStateException | IndexOutOfBoundsException exception) {
                // log
            }
        }

        return Optional.empty();
    }

    record Streak(char winLossDesignation, int gamesCount){};
}
