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

package net.pkhapps.hexagonal.domain.base.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specialization of the {@link Component} annotation indicating that the annotated bean is a
 * {@linkplain org.springframework.data.jpa.domain.Specification specification} factory. Specification factories
 * are used to build specifications that in turn are used when querying
 * {@linkplain net.pkhapps.hexagonal.domain.base.BaseRepository repositories}. The factory methods are instance methods
 * in order to make them mockable during testing (as opposed to having them as static factory methods in a
 * non-instantiable utility class).
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface SpecificationFactory {
    String value() default "";
}
