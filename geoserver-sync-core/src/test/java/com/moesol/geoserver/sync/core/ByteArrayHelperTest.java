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

import com.moesol.geoserver.sync.core.ByteArrayHelper;

import junit.framework.TestCase;

public class ByteArrayHelperTest extends TestCase {

	public void testFromHex() {
		byte[] value;
		String hex;
		
		value = new byte[] { 0, 1, 2, 3 };
		hex = ByteArrayHelper.toHex(value);
		assertTrue(hex, Arrays.equals(value, ByteArrayHelper.fromHex(hex)));
		
		value = new byte[] { (byte) 0xFF, (byte) 0xF1, (byte) 0xF2 };
		hex = ByteArrayHelper.toHex(value);
		assertTrue(hex, Arrays.equals(value, ByteArrayHelper.fromHex(hex)));
	}
	
	public void testBadHex() {
		try {
			ByteArrayHelper.fromHex("a");
			fail("ex");
		} catch (IllegalArgumentException e) {
			assertEquals("String length must be even", e.getMessage());
		}
	}

}
