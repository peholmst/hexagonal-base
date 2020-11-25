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
 * Specialization of the {@link Component} annotation indicating that the annotated bean is an application service
 * delegate. Application service delegates are shared by multiple {@linkplain ApplicationService application services},
 * {@linkplain Worker workers} and {@linkplain Orchestrator orchestrators},
 * and perform the same duties (or parts thereof) as application services, but must never be invoked directly by
 * clients. They can choose to handle transactions and security themselves or let their callers take care of that.
 *
 * @see ApplicationService
 * @see Orchestrator
 * @see Worker
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ApplicationServiceDelegate {
    String value() default "";
}
