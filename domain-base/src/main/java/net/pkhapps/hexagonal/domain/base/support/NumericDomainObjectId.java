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

import net.pkhapps.hexagonal.domain.base.DomainObjectId;

/**
 * Base class for {@linkplain DomainObjectId domain object IDs} that wrap long integers.
 */
public abstract class NumericDomainObjectId extends SimpleValueObject<Long> implements DomainObjectId {

    /**
     * Creates a new {@code LongDomainObjectId} from the given numeric ID.
     *
     * @param id the numeric ID.
     */
    public NumericDomainObjectId(long id) {
        super(id);
    }
}
