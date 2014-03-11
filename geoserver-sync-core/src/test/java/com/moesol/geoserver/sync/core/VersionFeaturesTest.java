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




import com.moesol.geoserver.sync.core.VersionFeatures;
import com.moesol.geoserver.sync.json.Sha1SyncJson;

import junit.framework.TestCase;

public class VersionFeaturesTest extends TestCase {

	public void testFromSha1SyncJson() {
		Sha1SyncJson json = new Sha1SyncJson();
		json.v = null;
		assertEquals(VersionFeatures.VERSION1, VersionFeatures.fromSha1SyncJson(json));
		
		json.v = "1";
		assertEquals(VersionFeatures.VERSION1, VersionFeatures.fromSha1SyncJson(json));

		json.v = "2";
		assertEquals(VersionFeatures.VERSION2, VersionFeatures.fromSha1SyncJson(json));
		
		try {
			json.v = "3";
			VersionFeatures.fromSha1SyncJson(json);
			fail("expected exception");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

}
