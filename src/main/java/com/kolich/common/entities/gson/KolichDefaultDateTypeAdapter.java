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

package com.kolich.common.entities.gson;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

public final class KolichDefaultDateTypeAdapter
	implements JsonSerializer<Date>, JsonDeserializer<Date> {
	
	private final DateFormat format_;
	
	public KolichDefaultDateTypeAdapter(final DateFormat format) {
		format_ = format;
	}

    @Override
	public JsonElement serialize(final Date src, final Type typeOfSrc,
		final JsonSerializationContext context) {
    	synchronized(format_) {
    		String dateFormatAsString = format_.format(src);
    		return new JsonPrimitive(dateFormatAsString);
    	}
    }

    @Override
	public Date deserialize(final JsonElement json, final Type typeOfT,
		final JsonDeserializationContext context) throws JsonParseException {
    	if (!(json instanceof JsonPrimitive)) {
    		throw new JsonParseException("The date should be a String type.");
    	}
    	final String jsonDate = json.getAsString();
    	try {
    		synchronized(format_) {
    			return format_.parse(jsonDate);
    		}
    	} catch (ParseException e) {
    		throw new JsonSyntaxException("Failed to de-serialize " +
    			"date. Invalid format? = " + jsonDate, e);
    	}
    }
    
}
