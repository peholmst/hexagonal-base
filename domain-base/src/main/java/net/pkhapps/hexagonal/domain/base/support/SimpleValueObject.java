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

import net.pkhapps.hexagonal.domain.base.ValueObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Base class for simple {@linkplain ValueObject value objects} that wrap a value of some other type.
 *
 * @param <V> the type of the wrapped value.
 * @see SimpleValueObjectAttributeConverter
 */
public abstract class SimpleValueObject<V> implements ValueObject {

    private final V value;

    /**
     * Creates a new simple value object.
     *
     * @param value the value to wrap (never null).
     */
    public SimpleValueObject(@NotNull V value) {
        this.value = requireNonNull(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Unwraps the value object.
     *
     * @return the wrapped value.
     */
    public @NotNull V unwrap() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (SimpleValueObject<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
