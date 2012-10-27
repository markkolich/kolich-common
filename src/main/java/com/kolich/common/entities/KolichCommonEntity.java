/**
 * Copyright (c) 2012 Mark S. Kolich
 * http://mark.koli.ch
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.kolich.common.entities;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class KolichCommonEntity {
	
	private static GsonBuilder builder__ = null;
	
	/**
	 * Get a fresh {@link Gson} instance created using the default
	 * {@link GsonBuilder}.
	 */
	public synchronized static final Gson getDefaultGsonInstance() {
		if (builder__ == null) {
			builder__ = getDefaultGsonBuilder();
		}
		return builder__.create();
	}
	
	/**
	 * Get a new, default configured, {@link GsonBuilder} instance.
	 * Pretty printing is disabled, null's are serialized.
	 * @return
	 */
	public static final GsonBuilder getDefaultGsonBuilder() {
		return new GsonBuilder().serializeNulls();
	}
	
	/**
	 * Returns the entity in its default JSON object representation
	 * as a series of UTF-8 encoded bytes.
	 * @return
	 */
	public byte[] getBytes() {
		return getBytesUtf8(toString());
	}
	
	@Override
	public abstract String toString();
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object o);

}
