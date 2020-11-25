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

import net.pkhapps.hexagonal.domain.base.support.UUIDDomainObjectId;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor.PassThroughTransformer;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor.ToBytesTransformer;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor.ToStringTransformer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Hibernate type descriptor for a {@link UUIDDomainObjectId} subtype. You typically don't need to subclass this, it is
 * enough to instantiate it on demand or as a reusable constant.
 *
 * @param <ID> the ID type.
 * @see UUIDDomainObjectIdCustomType
 */
public class UUIDDomainObjectIdTypeDescriptor<ID extends UUIDDomainObjectId> extends AbstractTypeDescriptor<ID> {

    private final Function<UUID, ID> factory;

    /**
     * Creates a new {@code DomainObjectIdTypeDescriptor}.
     *
     * @param type    the ID type.
     * @param factory a factory for creating new ID instances.
     */
    public UUIDDomainObjectIdTypeDescriptor(@NotNull Class<ID> type, @NotNull Function<UUID, ID> factory) {
        super(type);
        this.factory = requireNonNull(factory);
    }

    @Override
    public String toString(ID value) {
        return ToStringTransformer.INSTANCE.transform(value.unwrap());
    }

    @Override
    public ID fromString(String string) {
        return factory.apply(ToStringTransformer.INSTANCE.parse(string));
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
        if (UUID.class.isAssignableFrom(type)) {
            return (X) PassThroughTransformer.INSTANCE.transform(value.unwrap());
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) ToStringTransformer.INSTANCE.transform(value.unwrap());
        }
        if (byte[].class.isAssignableFrom(type)) {
            return (X) ToBytesTransformer.INSTANCE.transform(value.unwrap());
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> ID wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (getJavaType().isInstance(value)) {
            return getJavaType().cast(value);
        }
        if (value instanceof UUID) {
            return factory.apply(PassThroughTransformer.INSTANCE.parse(value));
        }
        if (value instanceof String) {
            return factory.apply(ToStringTransformer.INSTANCE.parse(value));
        }
        if (value instanceof byte[]) {
            return factory.apply(ToBytesTransformer.INSTANCE.parse(value));
        }
        throw unknownWrap(value.getClass());
    }
}
