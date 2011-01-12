/*
 * Copyright 2010 Scott Fines
 * <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.menagerie;

import java.io.*;
import java.util.AbstractMap;
import java.util.Map;

/**
 * TODO -sf- document!
 *
 * @author Scott Fines
 * @version 1.0
 *          Date: 08-Jan-2011
 *          Time: 10:58:20
 */
@Beta
public class JavaSerializer<K extends Serializable, V extends Serializable> implements Serializer<Map.Entry<K,V>> {

    @Override
    @SuppressWarnings({"unchecked"})
    public Map.Entry<K, V> deserialize(byte[] data) {
        try {
            ObjectInputStream inputStream  = new ObjectInputStream(new ByteArrayInputStream(data));
            return (AbstractMap.SimpleEntry<K,V>)inputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            //should never happen, since AbstractMap.SimpleEntry is part of the JDK
            throw new RuntimeException(e);
        }
    }


    @Override
    public byte[] serialize(Map.Entry<K, V> instance) {
         try {
            ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
            ObjectOutputStream arrayOutput = new ObjectOutputStream(byteArrayStream);
            arrayOutput.writeObject(instance);
            arrayOutput.flush();

            byte[] bytes = byteArrayStream.toByteArray();
            arrayOutput.close();
            return bytes;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
