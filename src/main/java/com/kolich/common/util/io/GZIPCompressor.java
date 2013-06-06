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

package com.kolich.common.util.io;

import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

import com.kolich.common.KolichCommonException;

public final class GZIPCompressor {
	
	/**
	 * The DEFAULT_BUFFER_SIZE in {@link org.apache.commons.io.IOUtils}
	 * is 4KB.  Looking at the source of GZIPInputStream and
	 * GZIPOutputStream in the JDK, we see that their default buffer
	 * sizes are 512-bytes.  When the GZIP stream buffer sizes do not match
	 * the size of the copy buffer in {@link org.apache.commons.io.IOUtils},
	 * performance takes a hit.  As a result, the default buffer size here is
	 * set to match IOUtils at 4KB for best performance.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 4096;
	
	// Cannot be instantiated.
	private GZIPCompressor() { }
		
	/**
	 * Given an uncompressed InputStream, compress it and return the
	 * result as new byte array.
	 * @return
	 */
	public static final byte[] compress(final InputStream is,
		final int outputBufferSize) {
		GZIPOutputStream gzos = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			gzos = new GZIPOutputStream(baos, outputBufferSize) {
				// Ugly anonymous constructor hack to set the compression
				// level on the underlying Deflater to "max compression".
				{ def.setLevel(Deflater.BEST_COMPRESSION); }
			};
			IOUtils.copyLarge(is, gzos);
			gzos.finish();
			return baos.toByteArray();
		} catch (Exception e) {
			throw new GZIPCompressorException(e);
		} finally {
			closeQuietly(gzos);
		}
	}
	
	/**
	 * Given an uncompressed byte-array, compress it using GZIP
	 * compression and return the compressed array.
	 * @param input
	 * @return
	 */
	public static final byte[] compress(final byte[] input,
		final int size) {
		return compress(new ByteArrayInputStream(input), size);
	}
	
	/**
	 * Given an uncompressed byte-array, compress it using GZIP
	 * compression and return the compressed array.  Uses a default
	 * GZIP stream buffer size of 4KB.
	 * @param input
	 * @return
	 */
	public static final byte[] compress(final byte[] input) {
		return compress(input, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * Given a GZIP'ed compressed InputStream, uncompresses it and returns
	 * the result as new byte array.  Does NOT close the InputStream; it's
	 * up to the caller to close the InputStream when necessary.
	 * @param toUncompress
	 * @return
	 */
	public static final byte[] uncompress(final InputStream is,
		final int size) {
		GZIPInputStream gzis = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			gzis = new GZIPInputStream(is, size);
			IOUtils.copyLarge(gzis, baos);
			return baos.toByteArray();
		} catch (Exception e) {
			throw new GZIPCompressorException(e);
		} finally {
			IOUtils.closeQuietly(gzis);
		}
	}
	
	/**
	 * Given a GZIP'ed compressed byte array, uncompresses it and returns
	 * the result as new byte array.
	 */
	public static final byte[] uncompress(final byte[] input,
		final int size) {
		return uncompress(new ByteArrayInputStream(input), size);
	}
	
	/**
	 * Given a GZIP'ed compressed by array, uncompresses it and returns
	 * the result as a new byte array.  Uses a default GZIP stream buffer
	 * size of 4KB.
	 * @param input
	 * @return
	 */
	public static final byte[] uncompress(final byte[] input) {
		return uncompress(input, DEFAULT_BUFFER_SIZE);
	}
	
	public static final class GZIPCompressorException
		extends KolichCommonException {
		
		private static final long serialVersionUID = -3848061272831604919L;

		public GZIPCompressorException(String message, Throwable cause) {
			super(message, cause);
		}
		
		public GZIPCompressorException(String message) {
			super(message);
		}
		
		public GZIPCompressorException(Throwable cause) {
			super(cause);
		}
		
	}

}

