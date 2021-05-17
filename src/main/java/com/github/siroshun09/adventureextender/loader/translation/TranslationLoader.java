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

import net.kyori.adventure.translation.Translator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Set;

/**
 * An interface to load message files from directory.
 */
public interface TranslationLoader {

    /**
     * Loads message files from directory.
     *
     * @throws IOException if I/O error occurred
     */
    void load() throws IOException;

    /**
     * Re-loads message files from directory.
     *
     * @throws IOException if I/O error occurred
     */
    void reload() throws IOException;

    /**
     * Registers the loaded messages to {@link net.kyori.adventure.translation.GlobalTranslator}.
     */
    void register();

    /**
     * Registers the loaded messages to {@link Translator}.
     *
     * @param translator the {@link Translator} to register
     */
    void register(@NotNull Translator translator);

    /**
     * Gets the loaded locales.
     *
     * @return the loaded locales
     */
    @NotNull @Unmodifiable Set<Locale> getLoadedLocales();

    /**
     * Gets the directory path where this loader loads from.
     *
     * @return the directory path
     */
    @NotNull Path getDirectory();
}
