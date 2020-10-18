/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.sedona.core.python.adapters

import net.razorvine.pickle.Unpickler
import net.razorvine.pickle.objects.ClassDict
import org.locationtech.jts.geom.Envelope

import scala.collection.JavaConverters._

object EnvelopeAdapter {
  def getFromPython(bytes: Array[Byte]): java.util.List[Envelope] = {
    val arrBytes = bytes.map(x => x.toByte)
    val unpickler = new Unpickler
    val pythonEnvelopes = unpickler.loads(arrBytes).asInstanceOf[java.util.ArrayList[_]].toArray
    pythonEnvelopes.map(pythonEnvelope => new Envelope(
      pythonEnvelope.asInstanceOf[ClassDict].get("minx").asInstanceOf[Double],
      pythonEnvelope.asInstanceOf[ClassDict].get("maxx").asInstanceOf[Double],
      pythonEnvelope.asInstanceOf[ClassDict].get("miny").asInstanceOf[Double],
      pythonEnvelope.asInstanceOf[ClassDict].get("maxy").asInstanceOf[Double]
    )).toList.asJava
  }
}