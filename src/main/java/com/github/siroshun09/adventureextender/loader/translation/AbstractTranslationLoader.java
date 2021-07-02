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
import com.github.siroshun09.configapi.api.util.FileUtils;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An abstract implementation of {@link TranslationLoader}.
 */
public abstract class AbstractTranslationLoader implements TranslationLoader {

    private final Path directory;
    private final Locale defaultLocale;
    private final String fileExtension;
    private final Set<Locale> loadedLocales = new HashSet<>();

    private TranslationRegistry registry;

    /**
     * Constructor.
     *
     * @param directory     the directory path where this loader loads from
     * @param defaultLocale the default locale
     * @param fileExtension the file extension to filter files
     */
    protected AbstractTranslationLoader(@NotNull Path directory,
                                        @NotNull Locale defaultLocale, @NotNull String fileExtension) {
        this.directory = directory;
        this.defaultLocale = defaultLocale;
        this.fileExtension = fileExtension;
    }

    @Override
    public void load() throws IOException {
        registry = createRegistry();

        if (!loadedLocales.isEmpty()) {
            loadedLocales.clear();
        }

        FileUtils.createDirectoriesIfNotExists(directory);

        saveDefaultIfNotExists();

        loadDefault();
        loadCustom();

        GlobalTranslator.get().addSource(registry);
    }

    @Override
    public void reload() throws IOException {
        if (registry != null) {
            GlobalTranslator.get().removeSource(registry);
        }

        load();
    }

    @Override
    public void unload() {
        if (registry != null) {
            GlobalTranslator.get().removeSource(registry);
            loadedLocales.clear();
            registry = null;
        }
    }

    @NotNull
    @Override
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    @Override
    public @NotNull @Unmodifiable Set<Locale> getLoadedLocales() {
        return Collections.unmodifiableSet(loadedLocales);
    }

    @Override
    public @NotNull Path getDirectory() {
        return directory;
    }

    /**
     * Saves default message files if not exists.
     *
     * @throws IOException if I/O error occurred
     */
    protected abstract void saveDefaultIfNotExists() throws IOException;

    /**
     * Creates {@link TranslationRegistry}.
     *
     * @return new {@link TranslationRegistry}
     */
    protected abstract @NotNull TranslationRegistry createRegistry();

    /**
     * Creates {@link MessageLoader} that matches the format of the file to load.
     *
     * @param path the filepath
     * @return new {@link MessageLoader}
     */
    protected abstract @NotNull MessageLoader createMessageLoader(@NotNull Path path);

    private void loadDefault() throws IOException {
        var defaultFileName = defaultLocale + fileExtension;
        var defaultFile = directory.resolve(defaultFileName);

        loadFile(defaultFile);
    }

    private void loadCustom() throws IOException {
        try (var files = Files.list(directory)) {
            var languageFiles =
                    files.filter(Files::isRegularFile)
                            .filter(p -> !p.toString().endsWith(defaultLocale + fileExtension))
                            .collect(Collectors.toUnmodifiableSet());

            for (var file : languageFiles) {
                loadFile(file);
            }
        }
    }

    private void loadFile(@NotNull Path path) throws IOException {
        var loader = createMessageLoader(path);

        var locale = loader.getLocale();

        if (locale != null) {
            loader.load();
            loader.register(registry, locale);

            loadedLocales.add(locale);
        }
    }
}
