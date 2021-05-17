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

import com.github.siroshun09.adventureextender.util.Colorizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ColorizerTest {

    private static final String TEST_1 = "test message";
    private static final String TEST_2 = "&1test &2message";
    private static final String TEST_2_COLORIZED = "§1test §2message";
    private static final String TEST_3 = "&#123456test message";
    private static final String TEST_3_COLORIZED = "§x§1§2§3§4§5§6test message";
    private static final String TEST_4 = "&5test &#1f5A54message";
    private static final String TEST_4_COLORIZED = "§5test §x§1§f§5§A§5§4message";
    private static final String TEST_5 = "&test &1message";
    private static final String TEST_5_COLORIZED = "&test §1message";
    private static final String TEST_5_ORIGINAL = "&test message";

    @Test
    void colorizeTest() {
        Assertions.assertEquals(TEST_1, Colorizer.colorize(TEST_1));
        Assertions.assertEquals(TEST_2_COLORIZED, Colorizer.colorize(TEST_2));
        Assertions.assertEquals(TEST_3_COLORIZED, Colorizer.colorize(TEST_3));
        Assertions.assertEquals(TEST_4_COLORIZED, Colorizer.colorize(TEST_4));
        Assertions.assertEquals(TEST_5_COLORIZED, Colorizer.colorize(TEST_5));
    }

    @Test
    void stripColorCodeTest() {
        Assertions.assertEquals(TEST_1, Colorizer.stripColorCode(TEST_1));
        Assertions.assertEquals(TEST_1, Colorizer.stripColorCode(TEST_2_COLORIZED));
        Assertions.assertEquals(TEST_1, Colorizer.stripColorCode(TEST_3_COLORIZED));
        Assertions.assertEquals(TEST_1, Colorizer.stripColorCode(TEST_4_COLORIZED));
        Assertions.assertEquals(TEST_5_ORIGINAL, Colorizer.stripColorCode(TEST_5_COLORIZED));
    }

    @Test
    void stripMarkedColorCodeTest() {
        Assertions.assertEquals(TEST_1, Colorizer.stripMarkedColorCode(TEST_1));
        Assertions.assertEquals(TEST_1, Colorizer.stripMarkedColorCode(TEST_2));
        Assertions.assertEquals(TEST_1, Colorizer.stripMarkedColorCode(TEST_3));
        Assertions.assertEquals(TEST_1, Colorizer.stripMarkedColorCode(TEST_4));
        Assertions.assertEquals(TEST_5_ORIGINAL, Colorizer.stripMarkedColorCode(TEST_5));
    }
}
