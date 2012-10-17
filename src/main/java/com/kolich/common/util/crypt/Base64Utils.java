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

package com.kolich.common.util.crypt;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;
import static org.apache.commons.codec.binary.StringUtils.newStringUtf8;

import org.apache.commons.codec.binary.Base64;

public final class Base64Utils {
	
	// Cannot be instantiated.
	private Base64Utils() { }
	
	/**
	 * Encodes a UTF-8 String using the base64 algorithm but
	 * does not chunk the output.
	 * @param encode
	 * @return A UTF-8 String representation of the encoded output.
	 */
	public static final String encodeBase64(final String encode) {
		return newStringUtf8(encodeBase64(getBytesUtf8(encode)));
	}
	
	/**
	 * Encodes binary data using the base64 algorithm but
	 * does not chunk the output.
	 * @param encode
	 * @return
	 */
	public static final byte[] encodeBase64(final byte[] encode) {
		return Base64.encodeBase64(encode);
	}
	
	/**
	 * Encodes a UTF-8 String using a URL-safe variation of the base64
	 * algorithm but does not chunk the output. The url-safe variation
	 * emits - and _ instead of + and / characters.
	 * @param encode
	 * @return A UTF-8 String representation of the encoded output.
	 */
	public static final String encodeBase64URLSafe(final String encode) {
		return newStringUtf8(encodeBase64URLSafe(getBytesUtf8(encode)));
	}
	
	/**
	 * Encodes binary data using a URL-safe variation of the base64
	 * algorithm but does not chunk the output. The url-safe variation
	 * emits - and _ instead of + and / characters.
	 * @param encode
	 * @return
	 */
	public static final byte[] encodeBase64URLSafe(final byte[] encode) {
		return Base64.encodeBase64URLSafe(encode);
	}
	
	/**
	 * Decodes a UTF-8 String using the base64 algorithm.  Works with
	 * URL-safe encoded base64 Strings too.
	 * @param encode
	 * @return A UTF-8 String representation of the decoded output.
	 */
	public static final String decodeBase64(final String decode) {
		return newStringUtf8(decodeBase64(getBytesUtf8(decode)));
	}
	
	/**
	 * Decodes binary data using the base64 algorithm but
	 * does not chunk the output.
	 * @param encode
	 * @return
	 */
	public static final byte[] decodeBase64(final byte[] decode) {
		return Base64.decodeBase64(decode);
	}
	
}
