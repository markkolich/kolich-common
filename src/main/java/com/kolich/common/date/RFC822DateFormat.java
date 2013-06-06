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

package com.kolich.common.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * The RFC-822 date format standard.
 * 
 * @author Mark Kolich
 */
public final class RFC822DateFormat {
	
	private static final String RFC822_DATE_FORMAT_STRING =
		"EEE, dd MMM yyyy HH:mm:ss Z";
	
	private static final DateFormat rfc822DateFormat__;
	static {
		rfc822DateFormat__ = new SimpleDateFormat(
			RFC822_DATE_FORMAT_STRING, Locale.US);
		rfc822DateFormat__.setTimeZone(new SimpleTimeZone(0, "GMT"));
	}
	
	// Cannot be instantiated.
	private RFC822DateFormat() {}
	
	public static synchronized final String format(final Date d) {
		return rfc822DateFormat__.format(d);
	}
	
	public static synchronized final Date format(final String s) {
		Date result = null;
		try {
			result = rfc822DateFormat__.parse(s);
		} catch (ParseException e) {
			throw new RuntimeException("Failed to RFC-822 date parse " +
				"input string: " + s, e);
		}
		return result;
	}
	
	public static final DateFormat getNewInstance() {
		return new SimpleDateFormat(RFC822_DATE_FORMAT_STRING);
	}
	
	public static final String getFormat() {
		return RFC822_DATE_FORMAT_STRING;
	}
		
}
