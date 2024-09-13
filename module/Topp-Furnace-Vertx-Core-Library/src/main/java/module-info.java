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
 * Topp Furnace Vert.x Core contains utilities addressing Vert.x Core.
 */
module com.yelstream.topp.furnace.vertx.core {
    requires static lombok;
    requires java.base;
    requires org.slf4j;
    requires io.vertx.core;
    requires com.yelstream.topp.standard.core;
    requires com.yelstream.topp.furnace.execution;
    requires com.yelstream.topp.furnace.lifecycle.management;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.common;
    exports com.yelstream.topp.furnace.vertx.core;
    exports com.yelstream.topp.furnace.vertx.core.buffer;
    exports com.yelstream.topp.furnace.vertx.core.eventbus;
    exports com.yelstream.topp.furnace.vertx.core.function;
    exports com.yelstream.topp.furnace.vertx.core.buffer.excile;
    exports com.yelstream.topp.furnace.vertx.core.buffer.excile.io;
    exports com.yelstream.topp.furnace.vertx.core.buffer.excile.cursor;
    exports com.yelstream.topp.furnace.vertx.core.buffer.cursor;
    exports com.yelstream.topp.furnace.vertx.core.buffer.io;
    exports com.yelstream.topp.furnace.vertx.core.buffer.excile.io.buffer;
}
