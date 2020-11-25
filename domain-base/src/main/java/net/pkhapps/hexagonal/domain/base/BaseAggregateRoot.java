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
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Base class for aggregate roots.
 *
 * @param <ID> the ID type.
 */
@MappedSuperclass
public abstract class BaseAggregateRoot<ID extends Serializable> extends BaseEntity<ID> {

    @Transient
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected BaseAggregateRoot() {
    }

    /**
     * Registers the given domain event to be published as a Spring application event when the aggregate root is saved.
     *
     * @param event the event to register and publish.
     */
    protected void registerEvent(@NotNull DomainEvent event) {
        domainEvents.add(requireNonNull(event));
    }

    /**
     * Called by Spring Data to get the domain events to publish as application events when the aggregate root has been
     * saved. Application code should typically never need to call this.
     *
     * @return an unmodifiable list of registered domain events.
     * @see DomainEvents
     */
    @DomainEvents
    protected @NotNull Collection<DomainEvent> domainEvents() {
        return Collections.unmodifiableCollection(domainEvents);
    }

    /**
     * Called by Spring Data after the aggregate root has been saved to clear any registered domain events.
     * Application code should typically never need to call this.
     *
     * @see AfterDomainEventPublication
     */
    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        domainEvents.clear();
    }
}
