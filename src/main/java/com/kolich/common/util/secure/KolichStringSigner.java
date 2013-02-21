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

package com.kolich.common.util.secure;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.kolich.common.util.crypt.Base64Utils.decodeBase64;
import static com.kolich.common.util.crypt.Base64Utils.encodeBase64;
import static com.kolich.common.util.crypt.Base64Utils.encodeBase64URLSafe;
import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;
import static org.apache.commons.codec.binary.StringUtils.newStringUtf8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import com.kolich.common.KolichCommonException;

public final class KolichStringSigner {
	
	private static final String SIGNATURE_DELIMITER = "|";
	private static final String ALGORITHM_SHA_256 = "SHA-256";
		
	/**
	 * The secret key used during signing.
	 */
	private final String secret_;
	
	/**
	 * Digest used to generate a signature.
	 */
	private final MessageDigest digest_;
	
	public KolichStringSigner(String algorithmName, String secret)
		throws NoSuchAlgorithmException {
		checkNotNull(secret, "Signature secret cannot be null.");
		secret_ = secret;
		digest_ = MessageDigest.getInstance((algorithmName != null) ?
			// If we were given an algorithm by name, attempt to use it.
			// Otherwise, just use the default.
			algorithmName : ALGORITHM_SHA_256);
	}
	
	public KolichStringSigner(String secret)
		throws NoSuchAlgorithmException {
		this(null, secret);
	}
	
	/**
	 * Given a payload String to sign, concatenates it together with the
	 * default delimiter followed by our secret.  The resulting String
	 * is a base-64 URL-safe encoded signature that consists of
	 * "payload|signature" where payload is likely to be an email address
	 * but can be any String.
	 * @param sign
	 * @return
	 */
	public final String sign(final String payload) {
		checkNotNull(payload, "Oops, the payload string to sign cannot " +
			"be null.");
		final String signed;
		synchronized(digest_) {
			signed = newStringUtf8(encodeBase64(
				digest_.digest(getBytesUtf8(
					// Relies on string concatenation. The payload really
					// shouldn't be huge but who knows.
					payload + secret_))));
		}
		return encodeBase64URLSafe(
			// We have to base-64 encode the payload in the event that it
			// might contain a "|" which is our default signature delimiter.
			encodeBase64(payload) +
			// A "|" to separate the payload and signature.
			SIGNATURE_DELIMITER +
			// The resulting signature, the signed payload and secret.
			signed);
	}
		
	/**
	 * Given a signature to validate, returns the String payload contained in
	 * the signature if the signature matches the signed payload.  The
	 * signed payload is most often an email address, but can be any String.
	 * @param input
	 * @param signature
	 * @return
	 */
	public final String isValid(final String signature) {
		checkNotNull(signature, "Oops, the signature to validate " +
			"cannot be null.");
		String certified = null;
		try {
			// Extract the payload from the signature, may be null
			// if no payload was found (e.g., a bogus signature).
			final String payload = extractPayload(signature);
			// Resign the extracted payload and make sure it matches
			// the input signature.  If it matches, when we know the
			// signature string and the contents inside came from us!
			if(sign(payload).equals(signature)) {
				certified = payload;
			} else {
				throw new StringSignerException("Signed payload (" + payload +
					") does not match signature (" + signature + ")");
			}
		} catch (Exception e) {
			throw new StringSignerException("Failed to validate input " +
				"signature: " + signature, e);
		}
		return certified;
	}
			
	/**
	 * Given a signature string, extract the relevant payload.
	 * Returns null if no payload could be extracted from within the
	 * signature.
	 * @param signature
	 * @return
	 */
	private static final String extractPayload(final String signature) {
		checkNotNull(signature, "Oops, the signature to extract a payload " +
			"from cannot be null.");
		// The signature is a base-64 encoded mess of "payload|signature"
		final String[] tokens = decodeBase64(signature).split(
			Pattern.quote(SIGNATURE_DELIMITER));
		return (tokens.length >= 1) ? decodeBase64(tokens[0]) : null;
	}
	
	/**
	 * Inline exception class for the string signer.
	 * @author mark
	 *
	 */
	public static final class StringSignerException
		extends KolichCommonException {
		
		private static final long serialVersionUID = -3848061272831604919L;

		public StringSignerException(String message, Throwable cause) {
			super(message, cause);
		}
		
		public StringSignerException(String message) {
			super(message);
		}
		
	}
	
}
