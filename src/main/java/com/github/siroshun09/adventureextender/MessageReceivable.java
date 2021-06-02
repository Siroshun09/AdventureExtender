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

package com.github.siroshun09.adventureextender;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * An interface that represents who can receive messages.
 */
public interface MessageReceivable {

    /**
     * Gets Adventure's {@link Audience}.
     *
     * @return the {@link Audience}
     */
    @NotNull Audience getAudience();

    /**
     * Sends the string.
     *
     * @param message the message to send
     */
    default void sendMessage(@NotNull String message) {
        if (message.isEmpty()) {
            sendMessage(Component.empty());
        } else {
            sendMessage(Component.text(message));
        }
    }

    /**
     * Sends the colorized message.
     * <p>
     * This method has the same behavior as {@code sendColorizedMessage(message, false)},
     * so this uses {@link LegacyComponentSerializer#legacyAmpersand()} to colorize messages.
     *
     * @param message the message to send
     */
    default void sendColorizedMessage(@NotNull String message) {
        sendColorizedMessage(message, false);
    }

    /**
     * Sends the colorized message.
     * <p>
     * If {@code section} is true, this method will use {@link LegacyComponentSerializer#legacySection()},
     * otherwise {@link LegacyComponentSerializer#legacyAmpersand()}.
     *
     * @param message the message to send
     * @param section whether sections (§) are used as markers for color codes.
     */
    default void sendColorizedMessage(@NotNull String message, boolean section) {
        if (message.isEmpty()) {
            sendMessage(Component.empty());
            return;
        }

        Component colorized;

        if (section) {
            colorized = LegacyComponentSerializer.legacySection().deserialize(message);
        } else {
            colorized = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        }

        sendMessage(colorized);
    }

    /**
     * Sends the {@link Component}
     *
     * @param component the {@link Component} to send
     */
    default void sendMessage(@NotNull Component component) {
        getAudience().sendMessage(component);
    }
}
