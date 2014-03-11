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

package com.moesol.geoserver.sync.core;




import java.util.Arrays;

import com.moesol.geoserver.sync.core.Sha1Value;

import junit.framework.TestCase;

public class Sha1ValueTest extends TestCase {

	public void testCompareTo() {
		Sha1Value left = new Sha1Value(new byte[0]);
		Sha1Value right = new Sha1Value(new byte[0]);
		assertEquals(0, left.compareTo(right));
		
		right = new Sha1Value(new byte[] { 0 });
		assertEquals(-1, left.compareTo(right));
		
		left = new Sha1Value(new byte[] { 0, 0 });
		assertEquals(1, left.compareTo(right));
		
		right = new Sha1Value(new byte[] { 0, 1 });
		assertEquals(-1, left.compareTo(right));
		
		left = new Sha1Value(new byte[] { 0, 2 });
		assertEquals(1, left.compareTo(right));
	}
	
	public void testIsPrefixMatch() {
		Sha1Value value = new Sha1Value(new byte[] { 0, 1, 2, 3 });
		assertTrue(value.isPrefixMatch(new byte[] { 0, 1 }));
		assertFalse(value.isPrefixMatch(new byte[] { 0, 2 }));
	}
	
	public void testCopyPrefixTo() {
		Sha1Value value = new Sha1Value(new byte[] { 0, 1, 2, 3 });
		byte[] out = new byte[3];
		value.copyPrefixTo(out);
		assertTrue(Arrays.equals(new byte[] { 0, 1, 2 }, out));
	}
	
	public void testHashAndEquals() {
		Sha1Value v1 = new Sha1Value("aa");
		Sha1Value v2 = new Sha1Value("bb");
		Sha1Value v3 = new Sha1Value("aa");
		
		assertFalse(v1.equals(v2));
		assertFalse(v1.hashCode() == v2.hashCode());
		assertEquals(v1, v3);
		assertEquals(v1.hashCode(), v3.hashCode());
		assertFalse(v1.equals(null));
		assertFalse(v1.equals("hi"));
		assertTrue(v1.equals(v1));
	}

}
