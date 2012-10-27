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

package com.kolich.common.util.secure;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.security.MessageDigest.getInstance;
import static org.apache.commons.codec.binary.Hex.encodeHex;
import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.kolich.common.KolichCommonException;

public class KolichChecksum {
	
	public static final String getSHA256Hash(final String input) {
		checkNotNull(input, "Input string to hash cannot be null.");
		return getSHA256Hash(getBytesUtf8(input));
	}
	
	public static final String getSHA256Hash(final byte[] input) {
		checkNotNull(input, "Input byte[] array to hash cannot be null.");
		return getSHA256HashAndCopy(new ByteArrayInputStream(input), null);
	}
	
	public static final String getSHA256HashAndCopy(final InputStream is,
		final OutputStream os) {
		checkNotNull(is, "Input stream to hash and copy cannot be null.");
		try {
			return getHashAndCopy(is, new SHA256StreamCopier(), os);
		} catch (Exception e) {
			throw new KolichCommonException("Failed to SHA-256 hash and copy " +
				"input stream.", e);
		}
	}
	
	public static final String getSHA1Hash(final String input) {
		checkNotNull(input, "Input string to hash cannot be null.");
		return getSHA1Hash(getBytesUtf8(input));
	}
	
	public static final String getSHA1Hash(final byte[] input) {
		checkNotNull(input, "Input byte[] array to hash cannot be null.");
		return getSHA1HashAndCopy(new ByteArrayInputStream(input), null);
	}
	
	public static final String getSHA1HashAndCopy(final InputStream is,
		final OutputStream os) {
		checkNotNull(is, "Input stream to hash and copy cannot be null.");
		checkNotNull(os, "Output stream to copy to cannot be null.");
		try {
			return getHashAndCopy(is, new SHA1StreamCopier(), os);
		} catch (Exception e) {
			throw new KolichCommonException("Failed to SHA-1 hash and copy " +
				"input stream.", e);
		}
	}
	
	public static final String getMD5Hash(final String input) {
		checkNotNull(input, "Input string to hash cannot be null.");
		return getSHA1Hash(getBytesUtf8(input));
	}
	
	public static final String getMD5Hash(final byte[] input) {
		checkNotNull(input, "Input byte[] array to hash cannot be null.");
		return getSHA1HashAndCopy(new ByteArrayInputStream(input), null);
	}
	
	public static final String getMD5HashAndCopy(final InputStream is,
		final OutputStream os) {
		checkNotNull(is, "Input stream to hash and copy cannot be null.");
		try {
			return getHashAndCopy(is, new MD5StreamCopier(), os);
		} catch (Exception e) {
			throw new KolichCommonException("Failed to MD5 hash and copy " +
				"input stream.", e);
		}
	}
	
	private static final String getHashAndCopy(final InputStream is,
		final HavaloHashStreamCopier copier, final OutputStream os) {
		try {
			copy(is, copier,
				// Only send the output stream if it's non-null.
				(os != null) ? new OutputStreamCopier(os) : null);
			// NOTE: We're using new String(...) here cuz encodeHex() returns
			// a char[] array which already has a prescribed encoding.  E.g.,
			// there's no need to "create a new UTF-8" String, the encoding
			// is handled under-the-hood.
			return new String(encodeHex(copier.digest()));
		} catch (Exception e) {
			throw new KolichCommonException(e);
		}
	}
	
	private static final void copy(final InputStream is,
		final HavaloStreamCopier... copiers) {
		try {
			final byte[] buffer = new byte[1048576]; // 1MB
			int read = 0;
		    while((read = is.read(buffer)) != -1) {
		    	for(final HavaloStreamCopier copier : copiers) {
		    		if(copier != null) {
		    			copier.write(buffer, read);
		    		}
		    	}
		    }
		} catch (Exception e) {
			throw new KolichCommonException(e);
		}
	}
	
	private static abstract class HavaloStreamCopier {
		
		public abstract void write(final byte[] buffer, final int read)
			throws Exception;
				
	}
	
	private static abstract class HavaloHashStreamCopier extends HavaloStreamCopier {
		
		public abstract byte[] digest();
				
	}
	
	private static final class SHA256StreamCopier extends HavaloHashStreamCopier {
		private static final String ALGO_SHA_256 = "SHA-256";
		private MessageDigest md_;
		public SHA256StreamCopier() throws NoSuchAlgorithmException {
			md_ = getInstance(ALGO_SHA_256);
		}
		@Override
		public void write(byte[] buffer, int read) throws Exception {
			md_.update(buffer, 0, read);
		}
		@Override
		public byte[] digest() {
			return md_.digest();
		}
	}
	
	private static final class SHA1StreamCopier extends HavaloHashStreamCopier {
		private static final String ALGO_SHA_1 = "SHA-1";
		private MessageDigest md_;
		public SHA1StreamCopier() throws NoSuchAlgorithmException {
			md_ = getInstance(ALGO_SHA_1);
		}
		@Override
		public void write(byte[] buffer, int read) throws Exception {
			md_.update(buffer, 0, read);
		}
		@Override
		public byte[] digest() {
			return md_.digest();
		}
	}
	
	private static final class MD5StreamCopier extends HavaloHashStreamCopier {
		private static final String ALGO_MD5 = "MD5";
		private MessageDigest md_;
		public MD5StreamCopier() throws NoSuchAlgorithmException {
			md_ = getInstance(ALGO_MD5);
		}
		@Override
		public void write(byte[] buffer, int read) throws Exception {
			md_.update(buffer, 0, read);
		}
		@Override
		public byte[] digest() {
			return md_.digest();
		}
	}
	
	private static final class OutputStreamCopier extends HavaloStreamCopier {		
		private OutputStream os_;		
		public OutputStreamCopier(OutputStream os) {
			os_ = os;
		}
		@Override
		public void write(byte[] buffer, int read) throws Exception {
			os_.write(buffer, 0, read);
		}
	}
	
}
