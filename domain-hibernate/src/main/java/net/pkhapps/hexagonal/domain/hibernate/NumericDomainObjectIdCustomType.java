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
import org.hibernate.id.ResultSetIdentifierConsumer;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BigIntTypeDescriptor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hibernate custom type for a {@link NumericDomainObjectId} subtype. You need this to be able to use
 * {@link NumericDomainObjectId}s as primary keys. You have to create one subclass per {@link NumericDomainObjectId}
 * subtype.
 *
 * @param <ID> the ID type.
 * @see NumericDomainObjectIdTypeDescriptor
 * @see NumericDomainObjectIdGenerator
 */
public abstract class NumericDomainObjectIdCustomType<ID extends NumericDomainObjectId> extends AbstractSingleColumnStandardBasicType<ID>
        implements ResultSetIdentifierConsumer {

    protected NumericDomainObjectIdCustomType(@NotNull JavaTypeDescriptor<ID> domainObjectIdTypeDescriptor) {
        super(BigIntTypeDescriptor.INSTANCE, domainObjectIdTypeDescriptor);
    }

    @Override
    public Serializable consumeIdentifier(ResultSet resultSet) {
        try {
            var id = resultSet.getLong(1);
            return getJavaTypeDescriptor().wrap(id, null);
        } catch (SQLException ex) {
            throw new IllegalStateException("Could not extract ID from ResultSet");
        }
    }

    @Override
    public String getName() {
        return getJavaTypeDescriptor().getJavaType().getSimpleName();
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }
}
