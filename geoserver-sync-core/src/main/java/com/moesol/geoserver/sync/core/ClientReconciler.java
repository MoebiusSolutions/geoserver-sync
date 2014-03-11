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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.moesol.geoserver.sync.json.Sha1SyncJson;
import com.moesol.geoserver.sync.json.Sha1SyncPositionHash;

public class ClientReconciler extends Reconciler {
	private List<Sha1SyncPositionHash> m_result;
	private Set<String> m_priorLevelPrefix = new HashSet<String>();
	private ReconcilerDelete m_delete = new ReconcilerDelete() {
		@Override
		public void deleteGroup(Sha1SyncPositionHash group) {
		}
	};

	public ClientReconciler(Sha1SyncJson local, Sha1SyncJson remote) {
		super(local, remote);
	}

	@Override
	protected void differentAtPosition(String position, Sha1SyncPositionHash local, Sha1SyncPositionHash remote) {
		// Tell remote side this branch differs
		m_result.add(local);
	}

	@Override
	protected void matchAtPosition(String position) {
		// Matches can be safely skipped
	}

	@Override
	protected void localMissingPosition(String position, Sha1SyncPositionHash remote) {
		// Tell remote side this branch is empty unless remote thinks its empty too.
		if (remote.summary() == null) {
			return; // skip, remote thinks its empty
		}
		m_result.add(new Sha1SyncPositionHash().position(position));
	}

	@Override
	protected void remoteMissingPosition(String position, Sha1SyncPositionHash local) {
		// If server talks about prefix and we don't have this position this this is a delete
		// Otherwise, we skip this because server thinks we are in synch here.
		String prior = computePriorPrefix(position);
		if (m_priorLevelPrefix.contains(prior)) {
			m_delete.deleteGroup(local);
		}
	}

	public void computeOutput(Sha1SyncJson outputSha1SyncJson) {
	    m_result = new ArrayList<Sha1SyncPositionHash>();
	    computePriorLevelPrefixes();
	    reconcile();
		outputSha1SyncJson.hashes(m_result);
	}

	private void computePriorLevelPrefixes() {
		for (Sha1SyncPositionHash group : m_remote.hashes()) {
			m_priorLevelPrefix.add(computePriorPrefix(group.position()));
		}
	}

	/**
	 * Each level has a byte or 2 hex characters
	 * @param position
	 * @return
	 */
	private String computePriorPrefix(String position) {
		int length = position.length();
		if (length < 2) {
			return position;
		}
		return position.substring(0, length - 2);
	}

	public ReconcilerDelete getDelete() {
		return m_delete;
	}

	public void setDelete(ReconcilerDelete delete) {
		m_delete = delete;
	}

}
