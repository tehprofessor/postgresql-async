/*
 * Copyright 2013 Maurício Linhares
 *
 * Maurício Linhares licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.github.mauricio.async.db.mysql.decoder

import com.github.mauricio.async.db.mysql.message.server.{ResultSetRowMessage, ServerMessage}
import com.github.mauricio.async.db.util.ChannelWrapper.bufferToWrapper
import java.nio.charset.Charset
import org.jboss.netty.buffer.ChannelBuffer

object ResultSetRowDecoder {

  final val NULL = 0xfb

}

class ResultSetRowDecoder( charset : Charset ) extends MessageDecoder {

  import ResultSetRowDecoder.NULL

  def decode(buffer: ChannelBuffer): ServerMessage = {
    val row = new ResultSetRowMessage()

    while ( buffer.readable() ) {
      if ( buffer.getByte(buffer.readerIndex()) == NULL ) {
        row += null
      } else {
        val length = buffer.readBinaryLength.asInstanceOf[Int]
        row += buffer.readSlice(length)
      }
    }

    row
  }
}
