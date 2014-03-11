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

package com.moesol.geoserver.sync.json;




import com.google.gson.Gson;

/**
 * Java bean used to format and parse the JSON messages for SHA-1 synchronization.
 * @author hasting
 */
public class Sha1SyncPositionHash {
	/** prefix in hex */
	public String p;
	/** summary hash */
	public String s;
	
	public Sha1SyncPositionHash position(String position) {
		p = position;
		return this;
	}
	public String position() {
		return p;
	}
	public Sha1SyncPositionHash summary(String summary) {
		s = summary;
		return this;
	}
	public String summary() {
		return s;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sha1SyncPositionHash other = (Sha1SyncPositionHash) obj;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}
}
