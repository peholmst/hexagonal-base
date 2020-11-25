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

package net.pkhapps.hexagonal.application.base.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specialization of the {@link Component} annotation indicating that the annotated bean is an orchestrator. An
 * orchestrator interacts with the domain model (typically through domain services) and with
 * {@linkplain ApplicationServiceDelegate application service delegates} in response to domain events. An orchestrator
 * typically controls transactions but only enforces security when it makes sense, taking into account that some of the
 * events may have been published by a background thread running without a security context.
 *
 * @see Worker
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Orchestrator {
    String value() default "";
}
