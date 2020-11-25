/*
 * Copyright 2020 Petter Holmström
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.pkhapps.hexagonal.domain.base.support;

import net.pkhapps.hexagonal.domain.base.DomainObjectId;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Base class for {@linkplain DomainObjectId domain object IDs} that wrap {@linkplain UUID UUIDs}.
 */
public abstract class UUIDDomainObjectId extends SimpleValueObject<UUID> implements DomainObjectId {

    /**
     * Creates a new {@code UUIDDomainObjectId} containing a random type 4 UUID.
     *
     * @see UUID#randomUUID()
     */
    public UUIDDomainObjectId() {
        super(UUID.randomUUID());
    }

    /**
     * Creates a new {@code UUIDDomainObjectId} from the given UUID.
     *
     * @param uuid the UUID.
     */
    public UUIDDomainObjectId(@NotNull UUID uuid) {
        super(uuid);
    }

    /**
     * Creates a new {@code UUIDDomainObjectId} from the given string.
     *
     * @param uuid the UUID as a string.
     * @see UUID#fromString(String)
     */
    public UUIDDomainObjectId(@NotNull String uuid) {
        super(UUID.fromString(uuid));
    }

    /**
     * Creates a new {@code UUIDDomainObjectId} from the given byte array.
     *
     * @param uuid the UUID as a 16-byte big-endian array.
     * @see UUID#UUID(long, long)
     */
    public UUIDDomainObjectId(@NotNull byte[] uuid) {
        super(bytesToUUID(uuid));
    }

    private static @NotNull UUID bytesToUUID(@NotNull byte[] uuid) {
        var buf = ByteBuffer.wrap(uuid);
        return new UUID(buf.getLong(), buf.getLong());
    }

    /**
     * Returns the UUID as a byte array.
     *
     * @return a 16-byte big-endian array.
     */
    public @NotNull byte[] toBytes() {
        var buf = ByteBuffer.wrap(new byte[16]);
        var uuid = unwrap();
        buf.putLong(uuid.getMostSignificantBits());
        buf.putLong(uuid.getLeastSignificantBits());
        return buf.array();
    }
}
