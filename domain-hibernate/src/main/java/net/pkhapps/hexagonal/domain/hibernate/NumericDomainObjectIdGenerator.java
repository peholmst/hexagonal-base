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
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Properties;

/**
 * Base class for Hibernate {@link IdentifierGenerator}s that generate new {@link NumericDomainObjectId}s.
 *
 * @param <ID> the ID type.
 */
public abstract class NumericDomainObjectIdGenerator<ID extends NumericDomainObjectId> implements IdentifierGenerator,
        Configurable {

    private final IdentifierGenerator delegate;
    private final NumericDomainObjectIdTypeDescriptor<ID> typeDescriptor;

    /**
     * Subclasses should declare a default constructor that delegates to this constructor.
     *
     * @param delegate       the underlying ID generator to delegate the ID generation to (typically one of Hibernate's built in numeric ID generators).
     * @param typeDescriptor the type descriptor for the ID.
     */
    protected NumericDomainObjectIdGenerator(@NotNull IdentifierGenerator delegate,
                                             @NotNull NumericDomainObjectIdTypeDescriptor<ID> typeDescriptor) {
        this.delegate = delegate;
        this.typeDescriptor = typeDescriptor;
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        if (delegate instanceof Configurable) {
            ((Configurable) delegate).configure(type, params, serviceRegistry);
        }
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return typeDescriptor.wrap(delegate.generate(session, object), null);
    }

    @Override
    public boolean supportsJdbcBatchInserts() {
        return delegate.supportsJdbcBatchInserts();
    }
}
