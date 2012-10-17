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

package com.kolich.common.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Supports two common formats of ISO-8601 dates.
 * @author Mark Kolich
 *
 */
public final class ISO8601DateFormat {
	
	private static final String DATE_FORMAT_STRING =
		"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final String ALTERNATIVE_DATE_FORMAT_STRING =
		"yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	private static final DateFormat primaryFormat__ =
        new SimpleDateFormat(DATE_FORMAT_STRING);
	private static final DateFormat altFormat__ =
        new SimpleDateFormat(ALTERNATIVE_DATE_FORMAT_STRING);
	
	static {
		primaryFormat__.setTimeZone(new SimpleTimeZone(0, "GMT"));
		altFormat__.setTimeZone(new SimpleTimeZone(0, "GMT"));
	}
	
	// Cannot be instantiated.
	private ISO8601DateFormat() {}
	
	public static synchronized final String format(final Date d) {
		return primaryFormat__.format(d);
	}
	
	public static synchronized final Date parse(final String s) {
		Date result = null;
		try {
			result = primaryFormat__.parse(s);
		} catch (ParseException pe) {
			try {
				result = altFormat__.parse(s);
			} catch (ParseException pe2) {
				throw new RuntimeException("Failed to ISO-8601 date parse " +
					"input string: " + s, pe2);
			}
		}
		return result;
	}
	
	public static final DateFormat getNewInstance() {
		return new SimpleDateFormat(getPrimaryFormat());
	}
	
	public static final String getPrimaryFormat() {
		return DATE_FORMAT_STRING;
	}
	
	public static final String getAlternateFormat() {
		return ALTERNATIVE_DATE_FORMAT_STRING;
	}
	
}
