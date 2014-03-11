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




import com.moesol.geoserver.sync.json.Sha1SyncJson;

/**
 * Provide handling for different features in different versions.
 * @author hasting
 */
public enum VersionFeatures {
	VERSION1("1") {
		@Override
		public Sha1Value getBucketPrefixSha1(IdAndValueSha1s pair) { return pair.getValueSha1(); }
	},
	VERSION2("2") {
		@Override
		public Sha1Value getBucketPrefixSha1(IdAndValueSha1s pair) { return pair.getIdSha1(); }
	};
	
	private final String token;
	
	private VersionFeatures(String token) {
		this.token = token;
	}

	/**
	 * @return Token string for this version.
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Get the Sha1Value to use for bucket/position prefixes.
	 * <ul>
	 * <li>Version 1 uses value sha1 for bucket/position prefixes.
	 * <li>Version 2 uses ID sha1 for bucket/position prefixes.
	 * </ul>
	 * @param pair
	 * @return
	 */
	public abstract Sha1Value getBucketPrefixSha1(IdAndValueSha1s pair);
	
	public static VersionFeatures fromSha1SyncJson(Sha1SyncJson sha1SyncJson) {
		if (sha1SyncJson.v == null) {
			return VERSION1;
		}
		for (VersionFeatures f : VersionFeatures.values()) {
			if (f.getToken().equals(sha1SyncJson.v)) {
				return f;
			}
		}
		// TODO perhaps we can negotiate down to an older version
		throw new IllegalArgumentException("Unknown SHA-1 Sync JSON version: " + sha1SyncJson.v);
	}
}
