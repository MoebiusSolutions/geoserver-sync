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




import com.moesol.geoserver.sync.core.XorAccumulator;

import junit.framework.TestCase;

public class XorAccumulatorTest extends TestCase {

	public void testUpdate() {
		XorAccumulator a = new XorAccumulator();
		byte[] v = new byte[20];
		v[0] = 1;
		a.update(v);
		assertEquals("0100000000000000000000000000000000000000", a.toString());
		
		v[19] = 1;
		a.update(v);
		assertEquals("0000000000000000000000000000000000000001", a.toString());
	}

	public void testToString() {
		XorAccumulator a = new XorAccumulator();
		assertEquals(40, a.toString().length());
		assertEquals("0000000000000000000000000000000000000000", a.toString());
	}

}
