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



import java.util.ArrayList;
import java.util.List;

import com.moesol.geoserver.sync.core.IdAndValueSha1s;
import com.moesol.geoserver.sync.core.Sha1Value;
import com.moesol.geoserver.sync.core.VersionFeatures;
import com.moesol.geoserver.sync.json.Sha1SyncJson;
import com.moesol.geoserver.sync.json.Sha1SyncPositionHash;

/**
 * Build json state of groups and their SHA-1 of SHA-1 values.
 * 
 * @author hastings
 */
public class Sha1JsonLevelGrouper extends Sha1LevelGrouper {
	private Sha1SyncJson m_json;

	public Sha1SyncJson getJson() {
		return m_json;
	}

	public Sha1JsonLevelGrouper(VersionFeatures vf, List<? extends IdAndValueSha1s> featureSha1s) {
		super(vf, featureSha1s);
	}

	@Override
	protected void end(long maxInAnyGroup) {
		m_json.m = maxInAnyGroup;
		m_json.v = versionFeatures.getToken();
	}

	@Override
	protected void begin(int level) {
		m_json = new Sha1SyncJson();
		m_json.l = level;
		m_json.h = new ArrayList<Sha1SyncPositionHash>();
	}

	@Override
	protected void groupCompleted(GroupPosition prefix, Sha1Value sha1Value) {
		Sha1SyncPositionHash hash = new Sha1SyncPositionHash();
		hash.p = prefix.toString();
		hash.s = sha1Value.toString();
		m_json.h.add(hash);
	}

}
