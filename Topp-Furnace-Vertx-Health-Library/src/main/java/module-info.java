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

/**
 * Topp Furnace Vert.x Health provides higher level health check mechanisms.
 */
module com.yelstream.topp.furnace.vertx.health {
    requires static lombok;
    requires java.base;
    requires org.slf4j;
    requires io.vertx.core;
    requires com.yelstream.topp.standard.core;
    requires com.yelstream.topp.furnace.execution;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.common;
}
