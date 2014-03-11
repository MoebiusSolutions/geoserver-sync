/**
 *
 *  #%L
 * geoserver-sync-core
 *  $Id:$
 *  $HeadURL:$
 * %%
 * Copyright (C) 2013 Moebius Solutions Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 *
 */

package com.moesol.keys;



import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import junit.framework.TestCase;

import org.apache.commons.io.output.ByteArrayOutputStream;

//import com.skjegstad.utils.BloomFilter;

/**
 * @author hastings
 * Some testing to see what sizes we'd get by stringing together
 * comma separated uuid's and then compressing them...
 * We need this state transfer to allow any client to determine what
 * to delete.
 */
public class EncodeUidsTest extends TestCase {
	private double E = 19.0;
	private int N = 128;
	
	public void testCompress() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10000; i++) {
			UUID id = UUID.randomUUID();
			sb.append(id);
			sb.append(',');
		}
		String result = sb.toString();
//		System.out.println("val=" + result);
		
		Deflater deflate = new Deflater();
		try {
			byte[] compressed = new byte[512000];
			deflate.setInput(result.getBytes());
			deflate.finish();
			System.out.printf("in=%d out=%d%n", deflate.getBytesRead(), deflate.getBytesWritten());
			deflate.deflate(compressed);
			System.out.printf("in=%d out=%d%n", deflate.getBytesRead(), deflate.getBytesWritten());
		} finally {
			deflate.end();
		}
	}
	
	public void testSha1Compression() throws NoSuchAlgorithmException, IOException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStream out = makeCompressStream(baos);
		try {
			for (int i = 0; i < 128; i++) {
				Random r = new Random();
				byte[] bytes = new byte[20];
				r.nextBytes(bytes);
				byte[] sha1 = digest.digest(bytes);
				out.write(sha1);
			}
		} finally {
			out.close();
		}
		System.out.println("sha1 deflate: " + baos.toByteArray().length);
	}
	

	

	
	private OutputStream makeCompressStream(OutputStream out) throws IOException {
		return new DeflaterOutputStream(out);
//		return new GZIPOutputStream(out);
	}
}
