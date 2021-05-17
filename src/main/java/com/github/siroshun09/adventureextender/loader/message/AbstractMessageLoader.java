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

import com.github.siroshun09.adventureextender.util.LocaleParser;
import net.kyori.adventure.translation.TranslationRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * An abstract implementation of {@link MessageLoader}.
 */
public abstract class AbstractMessageLoader implements MessageLoader {

    private final Path path;
    private final Map<String, String> messageMap = new HashMap<>();

    private boolean isLoaded = false;

    /**
     * Constructor.
     *
     * @param path the filepath
     */
    protected AbstractMessageLoader(@NotNull Path path) {
        this.path = Objects.requireNonNull(path);
    }

    @Override
    public void reload() throws IOException {
        messageMap.clear();
        load();
    }

    @Override
    public boolean isLoaded() {
        return isLoaded;
    }

    @Override
    public boolean register(@NotNull TranslationRegistry registry) {
        var locale = parseLocaleFromFileName();

        if (locale != null) {
            return register(registry, locale);
        } else {
            return false;
        }
    }

    @Override
    public boolean register(@NotNull TranslationRegistry registry, @NotNull Locale locale) {
        if (isLoaded) {
            var messageFormatMap =
                    messageMap.entrySet()
                            .stream()
                            .collect(Collectors.toUnmodifiableMap(
                                    Map.Entry::getKey,
                                    entry -> new MessageFormat(entry.getValue())
                            ));

            registry.registerAll(locale, messageFormatMap);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull @Unmodifiable Map<String, String> getMessageMap() {
        return Collections.unmodifiableMap(messageMap);
    }

    @Override
    public @NotNull Path getPath() {
        return path;
    }

    @Override
    public @Nullable Locale getLocale() {
        return parseLocaleFromFileName();
    }

    /**
     * Set if the message was loaded successfully.
     *
     * @param isLoaded true if the message was loaded, false otherwise
     */
    protected void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    /**
     * Gets the modifiable message map.
     *
     * @return the modifiable message map
     */
    protected @NotNull Map<String, String> getModifiableMessageMap() {
        return messageMap;
    }

    /**
     * Parses the {@link Locale} from the file name.
     *
     * @return the locale if the parse was successful, null otherwise
     */
    protected @Nullable Locale parseLocaleFromFileName() {
        var filePath = path.getFileName();

        if (filePath == null) {
            return null;
        }

        var fileName = filePath.toString().toCharArray();
        var builder = new StringBuilder();

        for (var c : fileName) {
            if (c != '.') {
                builder.append(c);
            } else {
                break;
            }
        }

        return LocaleParser.parse(builder.toString());
    }

}
