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

package net.pkhapps.hexagonal.domain.base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Optional;

/**
 * Base class for entities.
 *
 * @param <ID> the ID type.
 */
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Persistable<ID>, IdentifiableDomainObject<ID> {

    // The ID field is not here. This is because having the ID in the mapped superclass effectively makes it impossible
    // to customize how IDs are generated in subclasses. Instead, every subclass should declare its own ID and
    // implement the abstract getter and setter methods.

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    protected BaseEntity() {
    }

    @Override
    @Transient
    public abstract @Nullable ID getId();

    @Override
    @Transient
    public boolean isNew() {
        return getId() == null;
    }

    @Override
    @Transient
    public @NotNull ID getIdentifier() {
        var id = getId();
        if (id == null) {
            throw new IllegalStateException("No ID set");
        }
        return id;
    }

    @Override
    public boolean hasIdentifier() {
        return getId() != null;
    }

    /**
     * Returns the optimistic locking version.
     *
     * @return the version or an empty {@code Optional} if the entity has not been persisted yet.
     */
    @Transient
    public @NotNull Optional<Long> getVersion() {
        return Optional.ofNullable(version);
    }

    /**
     * Sets the optimistic locking version. You should almost never need to do this.
     *
     * @param version the version to set.
     */
    protected void setVersion(@Nullable Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return String.format("%s{id=%s, version=%d}", getClass().getSimpleName(), getId(), version);
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }

        var that = (BaseEntity<?>) obj;
        var id = getId();
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        var id = getId();
        return id == null ? super.hashCode() : id.hashCode();
    }
}
