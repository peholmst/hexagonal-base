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
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Base class for Hibernate {@link IdentifierGenerator}s that generate new {@link UUIDDomainObjectId}s.
 *
 * @param <ID> the ID type.
 */
public abstract class UUIDDomainObjectIdGenerator<ID extends UUIDDomainObjectId> implements IdentifierGenerator {

    private final Supplier<ID> identifierFactory;

    /**
     * Subclasses should declare a default constructor that delegates to this constructor and pass in
     * {@link UUIDDomainObjectId#UUIDDomainObjectId()} as the identifier factory.
     *
     * @param identifierFactory the factory that creates new IDs.
     */
    protected UUIDDomainObjectIdGenerator(@NotNull Supplier<ID> identifierFactory) {
        this.identifierFactory = requireNonNull(identifierFactory);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return identifierFactory.get();
    }
}
