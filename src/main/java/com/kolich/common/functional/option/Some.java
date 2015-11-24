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

package com.kolich.common.functional.option;

import java.util.Collections;
import java.util.Iterator;

@Deprecated
public final class Some<T> extends Option<T> {
	
	private final T some_;
	
	private Some(final T some) {
		some_ = some;
	}

	@Override
	public boolean isSome() {
		return true;
	}

	@Override
	public boolean isNone() {
		return false;
	}
	
	@Override
	public T get() {
		return some_;
	}
	
	@Override
	public T getOrElse(final T orElse) {
		return get();
	}
	
	public static final <T> Option<T> some(final T some) {
		return new Some<T>(some);
	}

	@Override
	public Iterator<T> iterator() {
		return Collections.singletonList(some_).iterator();
	}
	
}