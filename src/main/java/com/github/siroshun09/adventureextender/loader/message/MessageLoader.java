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

import com.github.siroshun09.configapi.api.file.FileConfiguration;
import com.github.siroshun09.configapi.api.file.PropertiesFileConfiguration;
import com.github.siroshun09.configapi.yaml.YamlConfiguration;
import net.kyori.adventure.translation.TranslationRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

/**
 * An interface to load message file.
 */
public interface MessageLoader {

    /**
     * Creates {@link MessageLoader} that loads from {@link java.util.Properties}.
     *
     * @param path the path to load
     * @return the {@link MessageLoader}
     */
    @Contract("_ -> new")
    static @NotNull MessageLoader fromProperties(@NotNull Path path) {
        return new FileConfigurationLoader(PropertiesFileConfiguration.create(path));
    }

    @Contract("_ -> new")
    static @NotNull MessageLoader fromYaml(@NotNull Path path) {
        return new FileConfigurationLoader(YamlConfiguration.create(path));
    }

    /**
     * Creates {@link MessageLoader} that loads from {@link FileConfiguration}
     *
     * @param config the {@link FileConfiguration} to load
     * @return the {@link MessageLoader}
     */
    @Contract("_ -> new")
    static @NotNull MessageLoader fromFileConfiguration(@NotNull FileConfiguration config) {
        return new FileConfigurationLoader(config);
    }

    /**
     * Loads from file.
     *
     * @throws IOException if I/O error occurred
     */
    void load() throws IOException;

    /**
     * Re-loads from file.
     *
     * @throws IOException if I/O error occurred
     */
    void reload() throws IOException;

    /**
     * Returns whether this loader has succeeded in loading from message file.
     *
     * @return true if successful, false otherwise
     */
    boolean isLoaded();

    /**
     * Registers to {@link TranslationRegistry}.
     * <p>
     * If {@link MessageLoader#getLocale()} is null or {@link MessageLoader#isLoaded()} is false,
     * this method will not register to registry.
     *
     * @param registry the {@link TranslationRegistry} to registry
     * @return true if the registration was successful, false otherwise
     */
    boolean register(@NotNull TranslationRegistry registry);

    /**
     * Registers to {@link TranslationRegistry}.
     * <p>
     * If  {@link MessageLoader#isLoaded()} is false, this method will not register to registry.
     *
     * @param registry the {@link TranslationRegistry} to registry
     * @param locale   the locale of messages
     * @return true if the registration was successful, false otherwise
     */
    boolean register(@NotNull TranslationRegistry registry, @NotNull Locale locale);

    /**
     * Gets the message map.
     *
     * @return the message map
     */
    @NotNull @UnmodifiableView Map<String, String> getMessageMap();

    /**
     * Gets the path of the message file.
     *
     * @return the path of the message file
     */
    @NotNull Path getPath();

    /**
     * Gets the locale of the messages.
     *
     * @return the locale of the messages
     */
    @Nullable Locale getLocale();
}
