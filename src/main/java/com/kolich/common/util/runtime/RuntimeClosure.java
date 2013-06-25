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

package com.kolich.common.util.runtime;

import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.File;

import com.kolich.common.functional.either.Either;
import com.kolich.common.functional.either.Left;
import com.kolich.common.functional.either.Right;

public abstract class RuntimeClosure<T> {
	
	private final Runtime runtime_;
	
	public RuntimeClosure(final Runtime runtime) {
		runtime_ = runtime;
	}
	
	public RuntimeClosure() {
		this(Runtime.getRuntime());
	}
	
	public abstract T run(final Process process) throws Exception;
	
	public void success() throws Exception {
		// Default, do nothing.
	}
			
	public final Either<Exception,T> exec(final String command) {
		try {
			return doit(runtime_.exec(command));
		} catch (Exception e) {
			return Left.left(e);
		}
	}
	
	public final Either<Exception,T> exec(final String[] cmdArray) {
		try {
			return doit(runtime_.exec(cmdArray));
		} catch (Exception e) {
			return Left.left(e);
		}
	}
	
	public final Either<Exception,T> exec(final String command,
		final String[] envp) {
		try {
			return doit(runtime_.exec(command, envp));
		} catch (Exception e) {
			return Left.left(e);
		}
	}
	
	public final Either<Exception,T> exec(final String command,
		final String[] envp, final File dir) {
		try {
			return doit(runtime_.exec(command, envp, dir));
		} catch (Exception e) {
			return Left.left(e);
		}
	}
	
	public final Either<Exception,T> exec(final String[] cmdArray,
		final String[] envp, final File dir) {
		try {
			return doit(runtime_.exec(cmdArray, envp, dir));
		} catch (Exception e) {
			return Left.left(e);
		}
	}
	
	private final Either<Exception,T> doit(final Process p) {
		Either<Exception,T> result = null;
		try {
			result = Right.right(run(p));
			success();
		} catch (Exception e) {
			result = Left.left(e);
		} finally {
			if(p != null) {
				closeQuietly(p.getInputStream());
				closeQuietly(p.getOutputStream());
				closeQuietly(p.getErrorStream());
			}
		}
		return result;
	}

}