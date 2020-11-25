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

import java.io.Serializable;

/**
 * Interface for domain objects that are identifiable by some unique ID (which may, but is not required to be,
 * a subtype of {@link DomainObjectId}).
 *
 * @param <ID> the ID type.
 * @see DomainObjectId
 */
public interface IdentifiableDomainObject<ID extends Serializable> extends DomainObject {

    /**
     * Returns the ID of the domain object.
     *
     * @return the ID (never null).
     * @throws IllegalStateException if the domain object does not have an ID.
     */
    @NotNull ID getIdentifier();

    /**
     * Returns whether the domain object has an ID or not. Typically a domain object only lacks an ID when it has
     * first been created but not yet saved.
     *
     * @return true if the domain object has an ID, false if it does not.
     */
    boolean hasIdentifier();
}
