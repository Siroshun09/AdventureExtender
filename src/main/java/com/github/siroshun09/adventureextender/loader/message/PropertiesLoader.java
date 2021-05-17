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

package com.github.siroshun09.adventureextender.loader.message;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

class PropertiesLoader extends AbstractMessageLoader {

    PropertiesLoader(@NotNull Path path) {
        super(path);
    }

    @Override
    public void load() throws IOException {
        setLoaded(false);

        var path = getPath();

        if (!Files.isRegularFile(path)) {
            return;
        }

        var properties = new Properties();

        try (var reader = Files.newBufferedReader(getPath())) {
            properties.load(reader);
        }

        var messageMap = getModifiableMessageMap();

        for (var key : properties.keySet()) {
            if (key == null) {
                continue;
            }

            var value = properties.get(key);

            if (value == null) {
                continue;
            }

            var strKey = key instanceof String ? (String) key : key.toString();
            var strValue = value instanceof String ? (String) value : value.toString();

            messageMap.put(strKey, strValue);
        }

        setLoaded(true);
    }
}
