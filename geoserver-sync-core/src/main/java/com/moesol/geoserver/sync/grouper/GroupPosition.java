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



import com.moesol.geoserver.sync.core.ByteArrayHelper;
import com.moesol.geoserver.sync.core.Sha1Value;

/**
 * Stores prefix bytes that define the groups position.
 * @author hastings
 *
 */
public class GroupPosition {
	private static final int MAX_SHA1_LEN = 20;
	private final byte[] m_prefix;
	
	public GroupPosition(int level) {
		m_prefix = new byte[Math.min(level, MAX_SHA1_LEN)];
	}
	
	public void setFromSha1(Sha1Value sha1) {
		sha1.copyPrefixTo(m_prefix);
	}

	public String toString() {
		return ByteArrayHelper.toHex(m_prefix);
	}

	public byte[] get() {
		return m_prefix;
	}
}
