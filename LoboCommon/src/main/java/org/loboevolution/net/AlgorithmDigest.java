/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.net;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>AlgorithmDigest class.</p>
 */
@Slf4j
public class AlgorithmDigest implements Comparable<AlgorithmDigest> {

    private final static Map<String, Integer> str = createMap();
    private final String algorithm;
    private final String digest;
    private final Integer strength;

    public AlgorithmDigest(final String algorithm, final String digest, final Integer strength) {
        this.algorithm = algorithm;
        this.digest = digest;
        this.strength = strength;
    }

    public static boolean validate(final byte[] input, final String integrity) {
        final List<AlgorithmDigest> algDigests = parseMetadata(integrity);
        if (algDigests == null) {
            return true;
        }
        final boolean matchFound = algDigests.stream().anyMatch((algDigest) -> {
            final String encodedResult = algDigest.getHash(input);
            return encodedResult.equals(algDigest.digest);
        });
        return matchFound;
    }

    public static List<AlgorithmDigest> parseMetadata(final String integrity) {
        if (integrity == null || integrity.length() == 0) {
            return null;
        }
        final String[] tokens = integrity.split("\\s+");
        final List<AlgorithmDigest> hashes = getHashes(tokens);
        if (hashes.isEmpty()) {
            return null;
        }
        return strongestAlgDigests(hashes);
    }

    private static List<AlgorithmDigest> getHashes(final String[] tokens) {
        final List<AlgorithmDigest> hashes = new ArrayList<AlgorithmDigest>();
        for (final String token : tokens) {
            final int hyphen = token.indexOf("-");
            if (hyphen < 0) {
                continue;
            }
            final String alg = token.substring(0, hyphen);
            if (str.containsKey(alg)) {
                final String hashWithOptions = token.substring(hyphen + 1);
                final String hash = parseHashExpression(hashWithOptions);
                final AlgorithmDigest ag = new AlgorithmDigest(alg, hash, str.get(alg));
                hashes.add(ag);
            }
        }
        return hashes;
    }

    private static String parseHashExpression(final String hashWithOptions) {
        final int question = hashWithOptions.indexOf("?");
        return (question < 0) ? hashWithOptions : hashWithOptions.substring(0, question);
    }

    public static List<AlgorithmDigest> strongestAlgDigests(final List<AlgorithmDigest> hashes) {
        Collections.sort(hashes, (a, b) -> a.compareTo(b));
        final Integer strongest = hashes.get(0).strength;
        final List<AlgorithmDigest> result = hashes.stream().filter((h) -> h.strength == strongest)
                .collect(Collectors.toList());
        return result;
    }

    private static Map<String, Integer> createMap() {
        final Map<String, Integer> stren = new HashMap<String, Integer>();
        stren.put("sha256", 1);
        stren.put("sha384", 2);
        stren.put("sha512", 3);
        return stren;
    }

    public String getHash(final byte[] input) {
        final String alg = algorithm.substring(0, 3) + "-" + algorithm.substring(3);
        try {
            final MessageDigest md = MessageDigest.getInstance(alg);
            md.update(input);
            final byte[] digestedBytes = md.digest();
            return Base64.getEncoder().encodeToString(digestedBytes);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public int compareTo(final AlgorithmDigest o) {
        return o.strength - strength;
    }

    @Override
    public String toString() {
        return "AlgorithmDigest [algorithm=" + algorithm + ", digest=" + digest + ", strength=" + strength + "]";
    }

}