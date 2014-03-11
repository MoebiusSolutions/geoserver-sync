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

package com.moesol.geoserver.sync.grouper;




import com.moesol.geoserver.sync.json.Sha1SyncJson;
import com.moesol.geoserver.sync.json.Sha1SyncPositionHash;

import junit.framework.TestCase;

public class ReconcileTestBase extends TestCase {

	protected Sha1SyncJson makeJson() {
		return new Sha1SyncJson()
			.level(0)
			.hashes(new Sha1SyncPositionHash[0])
			.max(0);
	}

	protected Sha1SyncPositionHash makePosition(String p, String s) {
		return new Sha1SyncPositionHash().position(p).summary(s);
	}
	
	public void testNothing() {
	}

}
