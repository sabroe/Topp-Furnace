/*
 * Project: Topp Furnace
 * GitHub: https://github.com/sabroe/Topp-Furnace
 *
 * Copyright 2024 Morten Sabroe Mortensen
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
 */

package com.yelstream.topp.execution.inject.spi;

import jakarta.enterprise.inject.spi.CDI;
import lombok.experimental.UtilityClass;

/**
 * Addresses instances of {@link CDI}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-04
 */
@UtilityClass
public class CDIs {
    public static CDI<Object> getCDI() {
        return CDI.current();
    }

    public static <X> X get(CDI<Object> cdi,
                            Class<X> clazz) {
        return cdi.select(clazz).get();
    }

    public static <X> X get(Class<X> clazz) {
        return get(getCDI(),clazz);
    }
}
