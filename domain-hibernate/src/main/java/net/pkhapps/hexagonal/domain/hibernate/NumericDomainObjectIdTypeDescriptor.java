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

package net.pkhapps.hexagonal.domain.hibernate;

import net.pkhapps.hexagonal.domain.base.support.NumericDomainObjectId;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Hibernate type descriptor for a {@link NumericDomainObjectId} subtype. You typically don't need to subclass this, it
 * is enough to instantiate it on demand or as a reusable constant.
 *
 * @param <ID> the ID type.
 * @see NumericDomainObjectIdCustomType
 */
public class NumericDomainObjectIdTypeDescriptor<ID extends NumericDomainObjectId> extends AbstractTypeDescriptor<ID> {

    private final Function<Long, ID> factory;

    /**
     * Creates a new {@code NumericDomainObjectIdTypeDescriptor}.
     *
     * @param type    the ID type.
     * @param factory a factory for creating new ID instances.
     */
    public NumericDomainObjectIdTypeDescriptor(@NotNull Class<ID> type, @NotNull Function<Long, ID> factory) {
        super(type);
        this.factory = requireNonNull(factory);
    }

    @Override
    public String toString(ID value) {
        return value.toString();
    }

    @Override
    public ID fromString(String s) {
        return factory.apply(Long.parseLong(s));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <X> X unwrap(ID value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (getJavaType().isAssignableFrom(type)) {
            return (X) value;
        }
        if (Long.class.isAssignableFrom(type)) {
            return (X) value.unwrap();
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) value.toString();
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> ID wrap(X value, WrapperOptions wrapperOptions) {
        if (value == null) {
            return null;
        }
        if (getJavaType().isInstance(value)) {
            return getJavaType().cast(value);
        }
        if (value instanceof Long) {
            return factory.apply((Long) value);
        }
        if (value instanceof String) {
            return factory.apply(Long.parseLong((String) value));
        }
        throw unknownWrap(value.getClass());
    }
}
