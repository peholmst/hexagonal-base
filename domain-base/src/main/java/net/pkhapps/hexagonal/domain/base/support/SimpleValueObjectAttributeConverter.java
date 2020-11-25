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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.persistence.AttributeConverter;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Base class for {@linkplain AttributeConverter attribute converters} of
 * {@linkplain SimpleValueObject simple value objects}.
 *
 * @param <VO> the type of the value object.
 * @param <V>  the type of the wrapped value.
 */
public abstract class SimpleValueObjectAttributeConverter<VO extends SimpleValueObject<V>, V>
        implements AttributeConverter<VO, V> {

    private final Function<V, VO> factory;

    /**
     * Subclasses should override this constructor, passing in a suitable factory (such as a reference to the value
     * object's constructor or factory method). The factory will never be called with a null parameter.
     *
     * @param factory the factory to use for creating new value objects.
     */
    public SimpleValueObjectAttributeConverter(@NotNull Function<V, VO> factory) {
        this.factory = requireNonNull(factory);
    }

    @Override
    @Contract("null -> null")
    public V convertToDatabaseColumn(VO vo) {
        return vo == null ? null : vo.unwrap();
    }

    @Override
    @Contract("null -> null")
    public VO convertToEntityAttribute(V v) {
        return v == null ? null : factory.apply(v);
    }
}
