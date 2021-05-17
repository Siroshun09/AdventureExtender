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

package com.github.siroshun09.adventureextender.test.loader;

import com.github.siroshun09.adventureextender.loader.message.MessageLoader;
import com.github.siroshun09.configapi.yaml.YamlConfiguration;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MessageLoaderTest {

    private static final Map<String, String> EXPECTED_MAP;

    static {
        EXPECTED_MAP =
                Map.of("message1", "test1",
                        "messages.message2", "test2",
                        "messages.message3", "test3",
                        "messages.child1.message4", "test4",
                        "messages.child1.message5", "test5",
                        "messages.child2.message4", "test4",
                        "messages.child2.message5", "test5",
                        "number1", "1053",
                        "number2", "14.51"
                );
    }

    @Test
    void testYamlLoader() throws Exception {
        var resourceUrl = getClass().getClassLoader().getResource("example.yml");

        assertNotNull(resourceUrl);

        var path = Path.of(resourceUrl.toURI());
        var config = YamlConfiguration.create(path);
        var loader = MessageLoader.fromFileConfiguration(config);

        loader.load();

        assertEquals(EXPECTED_MAP, loader.getMessageMap());
    }

    @Test
    void testPropertiesLoader() throws Exception {
        var resourceUrl = getClass().getClassLoader().getResource("example.properties");

        assertNotNull(resourceUrl);

        var path = Path.of(resourceUrl.toURI());
        var loader = MessageLoader.fromProperties(path);

        loader.load();

        assertEquals(EXPECTED_MAP, loader.getMessageMap());
    }
}
