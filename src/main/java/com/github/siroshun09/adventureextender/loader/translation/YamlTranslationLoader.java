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

package com.github.siroshun09.adventureextender.loader.translation;

import com.github.siroshun09.adventureextender.loader.message.MessageLoader;
import com.github.siroshun09.configapi.yaml.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Locale;

/**
 * An abstract implementation of {@link TranslationLoader}.
 */
public abstract class YamlTranslationLoader extends AbstractTranslationLoader {

    /**
     * Constructor.
     *
     * @param directory     the directory path where this loader loads from
     * @param defaultLocale the default locale
     */
    protected YamlTranslationLoader(@NotNull Path directory, @NotNull Locale defaultLocale) {
        super(directory, defaultLocale, ".yml");
    }

    @Override
    protected @NotNull MessageLoader createMessageLoader(@NotNull Path path) {
        var config = YamlConfiguration.create(path);
        return MessageLoader.fromFileConfiguration(config);
    }
}
