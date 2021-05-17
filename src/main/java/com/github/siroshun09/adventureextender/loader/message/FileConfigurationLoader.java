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

import com.github.siroshun09.configapi.common.Configuration;
import com.github.siroshun09.configapi.common.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

class FileConfigurationLoader extends AbstractMessageLoader {

    private final FileConfiguration config;

    FileConfigurationLoader(@NotNull FileConfiguration config) {
        super(config.getPath());
        this.config = config;
    }

    @Override
    public void load() throws IOException {
        setLoaded(false);

        if (!config.isLoaded()) {
            config.load();
        }

        getMessagesFromConfig(config, "");

        setLoaded(true);
    }

    @SuppressWarnings("unchecked")
    private void getMessagesFromConfig(@NotNull Configuration config, @NotNull String keyPrefix) {
        for (String key : config.getKeys()) {
            var object = config.get(key);

            if (object instanceof Map) {
                var map = (Map<String, Object>) object;
                var child = Configuration.create(map);
                var newKeyPrefix = keyPrefix + key + Configuration.KEY_SEPARATOR;

                getMessagesFromConfig(child, newKeyPrefix);

                continue;
            }

            if (object != null) {
                var currentKey = keyPrefix + key;
                var message = object instanceof String ? (String) object : object.toString();

                getModifiableMessageMap().put(currentKey, message);
            }
        }
    }
}
