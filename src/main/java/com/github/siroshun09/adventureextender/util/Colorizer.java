/*
 *     Copyright 2021 Siroshun09
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.github.siroshun09.adventureextender.util;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * An utility class that colorize the legacy-message.
 */
public final class Colorizer {

    private static final String EMPTY = "";

    private static final char COLOR_MARK = '&';
    private static final String COLOR_MARK_STRING = String.valueOf(COLOR_MARK);
    private static final Pattern COLOR_MARK_PATTERN;

    private static final char COLOR_SECTION = '§';
    private static final String COLOR_SECTION_STRING = String.valueOf(COLOR_SECTION);
    private static final Pattern COLOR_SECTION_PATTERN;

    private static final char HEX_MARK = '#';
    private static final char X = 'x';
    private static final String COLOR_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";

    static {
        COLOR_MARK_PATTERN = Pattern.compile(COLOR_MARK + "((" + HEX_MARK + "[0-9a-fA-F]{6})|([0-9a-fk-orxA-FK-ORX]))");
        COLOR_SECTION_PATTERN = Pattern.compile("(?i)" + COLOR_SECTION + "[0-9A-FK-ORX]");
    }

    private Colorizer() {
        throw new UnsupportedOperationException();
    }

    /**
     * Colorize the string.
     * <p>
     * example: {@code "&aHello, &#123456World"} {@literal ->} {@code "§aHello, §x§1§2§3§4§5§6World"}
     *
     * @param str the string to colorize
     * @return the colorized string
     */
    public static @NotNull String colorize(String str) {
        if (str == null || str.isEmpty()) {
            return EMPTY;
        }

        if (str.contains(COLOR_MARK_STRING)) {
            var builder = new StringBuilder(str);
            return colorize(builder).toString();
        } else {
            return str;
        }
    }

    /**
     * Colorize the string.
     * <p>
     * example: {@code "&aHello, &#123456World"} {@literal ->} {@code "§aHello, §x§1§2§3§4§5§6World"}
     * <p>
     * This method does not support hexadecimal colors represented like {@code #FFF}.
     *
     * @param builder the string builder to colorize
     * @return the colorized string builder
     */
    public static @NotNull StringBuilder colorize(StringBuilder builder) {
        if (builder == null) {
            return new StringBuilder();
        }

        int i = builder.indexOf(COLOR_MARK_STRING);

        if (i == -1) {
            return builder;
        }

        int l = builder.length();

        while (i != -1 && i + 1 < l) {
            if (builder.charAt(i + 1) == HEX_MARK) {
                if (i + 7 < l) {
                    var hex = builder.substring(i + 2, i + 8);

                    try {
                        Integer.parseInt(hex, 16);
                    } catch (NumberFormatException e) {
                        i = builder.indexOf(COLOR_MARK_STRING, i + 1);
                        continue;
                    }

                    builder.setCharAt(i, COLOR_SECTION);
                    builder.setCharAt(i + 1, X);

                    for (int j = i + 7; j != i + 1; j--) {
                        builder.insert(j, COLOR_SECTION_STRING);
                    }

                    l = builder.length();
                }

                i = builder.indexOf(COLOR_MARK_STRING, i + 1);
                continue;
            }

            if (-1 < COLOR_CODES.indexOf(builder.charAt(i + 1))) {
                builder.setCharAt(i, COLOR_SECTION);
            }

            i = builder.indexOf(COLOR_MARK_STRING, i + 1);
        }

        return builder;
    }

    /**
     * Strips the given string of color.
     * <p>
     * Example: {@code "§aHello, §x§1§2§3§4§5§6World"} {@literal ->} {@code "Hello, World"}
     *
     * @param str the string to strip
     * @return stripped string
     */
    public static @NotNull String stripColorCode(String str) {
        return stripColorCode(str, COLOR_SECTION_PATTERN);
    }

    /**
     * Strips the given string of color.
     * <p>
     * Example: {@code "&aHello, &#123456World"} {@literal ->} {@code "Hello, World"}
     *
     * @param str the string to strip
     * @return stripped string
     */
    public static @NotNull String stripMarkedColorCode(String str) {
        return stripColorCode(str, COLOR_MARK_PATTERN);
    }

    private static @NotNull String stripColorCode(String str, @NotNull Pattern pattern) {
        if (str == null || str.isEmpty()) {
            return EMPTY;
        }

        return pattern.matcher(str).replaceAll("");
    }
}
