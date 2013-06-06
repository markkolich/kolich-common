/**
 * Copyright (c) 2013 Mark S. Kolich
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

package com.kolich.common.util;

import static com.kolich.common.DefaultCharacterEncoding.UTF_8;
import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;

import java.io.UnsupportedEncodingException;

import com.kolich.common.KolichCommonException;

public final class URLEncodingUtils {
	
	// Cannot be instantiated.
	private URLEncodingUtils() { }
	
	/**
	 * URL encodes the given String with our default character encoding,
	 * likely UTF-8.
	 * @param s the String to encode
	 * @return a URL encoded String
	 */
	public static final String urlEncode(final String s) {
		return urlEncode(s, null);
	}
	
	/**
	 * URL encodes the given String with the desired encoding.
	 * @param s the String to encode
	 * @param enc the name of the supported character encoding
	 * @return a URL encoded String
	 */
	public static final String urlEncode(final String s, final String enc) {
		try {
			return encode(s, (enc != null) ? enc : UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new KolichCommonException("Failed to URL encode string: " +
				s, e);
		}
	}
	
	/**
	 * URL decodes the given String with our default character encoding,
	 * likely UTF-8.
	 * @param s the String to decode
	 * @return a URL decoded String
	 */
	public static final String urlDecode(final String s) {
		return urlDecode(s, null);
	}
	
	/**
	 * URL decodes the given String with the desired encoding.
	 * @param s the String to decode
	 * @param enc the name of the supported character encoding
	 * @return a URL decoded String
	 */
	public static final String urlDecode(final String s, final String enc) {
		try {
			return decode(s, (enc != null) ? enc : UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new KolichCommonException("Failed to URL decode string: " +
				s, e);
		}
	}

}
