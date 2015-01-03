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

package com.kolich.common.util.secure;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.kolich.common.KolichCommonException;

public final class KolichSecureRandom {
	
	/**
	 * Our Random Number Generator (RNG) algorithm.
	 */
	private static final String SHA1_PRNG = "SHA1PRNG";
	
	/**
	 * The default number of random bits to fetch.
	 */
	private static final int DEFAULT_RANDOM_SIZE_BITS = 512;
	
	private final SecureRandom random_;
	private final int size_;
	
	public KolichSecureRandom(int randomSize) {
		try {
			random_ = SecureRandom.getInstance(SHA1_PRNG);
			// Make sure the size of the random we're going to be asked
			// to generate is a multiple of 8.
			if((randomSize % Byte.SIZE) != 0) {
				throw new IllegalArgumentException("Requested random size (" +
					randomSize + ") is not a multiple of " + Byte.SIZE + ".");
			}
			size_ = randomSize;
	    } catch (NoSuchAlgorithmException e) {
	        throw new KolichCommonException(e);
	    }
	}
	
	public KolichSecureRandom() {
		this(DEFAULT_RANDOM_SIZE_BITS);
	}
	
	/**
	 * Gets the next 128 random bytes from a SecureRandom.
	 * @return
	 */
	public byte[] getRandom() {
		final byte[] bytes = new byte[size_/Byte.SIZE];
		synchronized(random_) {
			// Get the next X random bits.
			random_.nextBytes(bytes);
		}
		return bytes;
	}
	
	/**
	 * Returns a pseudorandom, uniformly distributed int value
	 * between 0 (inclusive) and the specified value (exclusive),
	 * drawn from this random number generator's sequence.
	 * @param max
	 * @return
	 */
	public int getRandomInt(final int max) {
		int random = -1;
		synchronized(random_) {
			random = random_.nextInt(max);
		}
		return random;
	}
	
	public int getRandomSize() {
		return size_;
	}

}
