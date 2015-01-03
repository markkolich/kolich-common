/**
 * Copyright (c) 2015 Mark S. Kolich
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

package com.kolich.common.util.crypt;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;
import static org.apache.commons.codec.binary.StringUtils.newStringUtf8;

import org.apache.commons.codec.binary.Base32;

public final class Base32Utils {
	
	// Cannot be instantiated.
	private Base32Utils() { }
		
	/**
	 * Encodes a UTF-8 String using the Base32 algorithm but
	 * does not chunk the output.
	 * @param encode
	 * @return A UTF-8 String representation of the encoded output.
	 */
	public static final String encodeBase32(final String encode) {
		return newStringUtf8(encodeBase32(getBytesUtf8(encode)));
	}
	
	/**
	 * Encodes binary data using the Base32 algorithm but
	 * does not chunk the output.
	 * @param encode
	 * @return
	 */
	public static final byte[] encodeBase32(final byte[] encode) {
		return new Base32(true).encode(encode);
	}
		
	/**
	 * Decodes a UTF-8 String using the Base32 algorithm.  Works with
	 * URL-safe encoded Base32 Strings too.
	 * @param encode
	 * @return A UTF-8 String representation of the decoded output.
	 */
	public static final String decodeBase32(final String decode) {
		return newStringUtf8(decodeBase32(getBytesUtf8(decode)));
	}
	
	/**
	 * Decodes binary data using the Base32 algorithm but
	 * does not chunk the output.
	 * @param encode
	 * @return
	 */
	public static final byte[] decodeBase32(final byte[] decode) {
		return new Base32(true).decode(decode);
	}
	
}
