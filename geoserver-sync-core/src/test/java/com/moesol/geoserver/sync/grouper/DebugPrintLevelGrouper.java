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



import java.io.PrintStream;
import java.util.List;

import com.moesol.geoserver.sync.core.IdAndValueSha1s;
import com.moesol.geoserver.sync.core.Sha1Value;
import com.moesol.geoserver.sync.core.VersionFeatures;
import com.moesol.geoserver.sync.grouper.GroupPosition;
import com.moesol.geoserver.sync.grouper.Sha1LevelGrouper;

/**
 * Dump debug grouping information
 * 
 * @author hastings
 */
public class DebugPrintLevelGrouper extends Sha1LevelGrouper {
	
	private PrintStream out = System.out;
	private int m_count = 0;
	private boolean justGroupHash = false;

	public boolean isJustGroupHash() {
		return justGroupHash;
	}

	public void setJustGroupHash(boolean justGroupHash) {
		this.justGroupHash = justGroupHash;
	}

	public DebugPrintLevelGrouper(VersionFeatures vf, List<IdAndValueSha1s> featureSha1s) {
		super(vf, featureSha1s);
	}

	@Override
	protected void end(long maxInAnyGroup) {
	}

	@Override
	protected void begin(int level) {
	}

	@Override
	protected void hashOne(Sha1Value sha1) {
		super.hashOne(sha1);
		
		if (justGroupHash) {
			return;
		}
		out.print(m_count);
		out.print(": ");
		out.println(sha1);
		m_count++;
	}

	@Override
	protected void groupCompleted(GroupPosition prefix, Sha1Value sha1Value) {
		out.println("--" + prefix + "--" + sha1Value);
	}

}
