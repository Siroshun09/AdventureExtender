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

package com.github.siroshun09.adventureextender.test.util;

import com.github.siroshun09.adventureextender.util.LocaleParser;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

class LocaleParserTest {

    @Test
    void testParser() {
        test(Locale.JAPANESE); // language only
        test(Locale.JAPAN); // language + country
    }

    private void test(@NotNull Locale locale) {
        Assertions.assertEquals(locale, LocaleParser.parse(locale.toString()));
    }
}
