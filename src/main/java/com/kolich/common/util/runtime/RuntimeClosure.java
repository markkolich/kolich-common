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

import com.kolich.common.functional.either.Either;
import com.kolich.common.functional.either.Left;
import com.kolich.common.functional.either.Right;

import java.io.File;

import static org.apache.commons.io.IOUtils.closeQuietly;

public abstract class RuntimeClosure<T> {
	
	private final Runtime runtime_;
	
	public RuntimeClosure(final Runtime runtime) {
		runtime_ = runtime;
	}
	
	public RuntimeClosure() {
		this(Runtime.getRuntime());
	}
	
	public abstract T run(final Process process) throws Exception;
	
	/**
	 * Success callback, called only if and only if the {@link#run}
	 * method completed without exception.  Default behavior is do
	 * nothing, consumers should override in their closures as needed.
	 * @param process the {@link Process} object associated with this
	 * runtime operation. May be null.
	 */
	public void success(final Process process) throws Exception {
		// Default, do nothing.
	}
	
	/**
	 * Post execution callback, called after the {@link#run} method
	 * is complete, on success or failure.  This method is called even
	 * if {@link#run} throws an exception.
	 * @param process the {@link Process} object associated with this
	 * runtime operation. May be null.
	 */
	public void after(final Process process) {
		// Default, do nothing.
	}
			
	public final Either<Exception,T> exec(final String command) {
		Process p = null;
		try {
			return doit(p = runtime_.exec(command));
		} catch (Exception e) {
			return Left.left(e);
		} finally {
			after(p);
		}
	}
	
	public final Either<Exception,T> exec(final String[] cmdArray) {
		Process p = null;
		try {
			return doit(p = runtime_.exec(cmdArray));
		} catch (Exception e) {
			return Left.left(e);
		} finally {
			after(p);
		}
	}
	
	public final Either<Exception,T> exec(final String command,
		final String[] envp) {
		Process p = null;
		try {
			return doit(p = runtime_.exec(command, envp));
		} catch (Exception e) {
			return Left.left(e);
		} finally {
			after(p);
		}
	}
	
	public final Either<Exception,T> exec(final String command,
		final String[] envp, final File dir) {
		Process p = null;
		try {
			return doit(p = runtime_.exec(command, envp, dir));
		} catch (Exception e) {
			return Left.left(e);
		} finally {
			after(p);
		}
	}
	
	public final Either<Exception,T> exec(final String[] cmdArray,
		final String[] envp, final File dir) {
		Process p = null;
		try {
			return doit(p = runtime_.exec(cmdArray, envp, dir));
		} catch (Exception e) {
			return Left.left(e);
		} finally {
			after(p);
		}
	}
	
	private final Either<Exception,T> doit(final Process process) {
		Either<Exception,T> result = null;
		try {
			result = Right.right(run(process));
			success(process);
		} catch (Exception e) {
			result = Left.left(e);
		} finally {
			if(process != null) {
				closeQuietly(process.getInputStream());
				closeQuietly(process.getOutputStream());
				closeQuietly(process.getErrorStream());
			}
		}
		return result;
	}

}