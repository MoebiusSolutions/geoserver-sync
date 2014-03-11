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
import com.moesol.geoserver.sync.json.Sha1SyncPositionHash;

public abstract class Reconciler {

	protected final Sha1SyncJson m_local;
	protected final Sha1SyncJson m_remote;

	public Reconciler(Sha1SyncJson local, Sha1SyncJson remote) {
		m_local = local;
		m_remote = remote;
		if (m_local.l != m_remote.l) {
			throw new IllegalArgumentException(msg());
		}
	}

	String msg() {
		return String.format("Local level(%d) not equal to remote level(%d)", m_local.l, m_remote.l);
	}

	protected abstract void differentAtPosition(String position, Sha1SyncPositionHash local, Sha1SyncPositionHash remote);
	protected abstract void matchAtPosition(String position);
	/**
	 * Remote has this position but remote does not. Default implementation does nothing.
	 * @param position from {@link Sha1SyncPositionHash}. for example, a0, ba10, abcd0123, ... 
	 * @param missing TODO
	 */
	protected abstract void localMissingPosition(String position, Sha1SyncPositionHash missing);
	/**
	 * Local has the position but remote does not. Default implementation does nothing.
	 * @param position from {@link Sha1SyncPositionHash}. for example, a0, ba10, abcd0123, ... 
	 * @param missing TODO
	 */
	protected abstract void remoteMissingPosition(String position, Sha1SyncPositionHash missing);

	/**
	 * Reconciles local and remote {@link Sha1SyncJson} differences by calling
	 * {@link #differentAtPosition}, {@link #matchAtPosition}, {@link #localMissing}, or {@link #remoteMissing}
	 */
	protected void reconcile() {
		int i = 0; // local index
		int j = 0; // remote index
		int local_size = m_local.h.size();
		int remote_size = m_remote.h == null ? 0 : m_remote.h.size();
		while (i < local_size && j < remote_size) {
			Sha1SyncPositionHash localHashPos = m_local.h.get(i);
			Sha1SyncPositionHash remoteHashPos = m_remote.h.get(j);
			
			int cmp = localHashPos.position().compareTo(remoteHashPos.position());
			if (cmp < 0) {
				remoteMissingPosition(localHashPos.position(), localHashPos);
				i++;
				continue;
			}
			if (cmp > 0) {
				localMissingPosition(remoteHashPos.position(), remoteHashPos);
				j++;
				continue;
			}
			
			// prefix is same on both do hashes match?
			String position = localHashPos.position();
			if (localHashPos.summary().equals(remoteHashPos.summary())) {
				matchAtPosition(position);
			} else {
				differentAtPosition(position, localHashPos, remoteHashPos);
			}
	
			i++;
			j++;
		}
		for ( ; i < local_size; i++) {
			Sha1SyncPositionHash localHashPos = m_local.h.get(i);
			remoteMissingPosition(localHashPos.position(), localHashPos);
		}
		for ( ; j < remote_size; j++) {
			Sha1SyncPositionHash remoteHashPos = m_remote.h.get(j);
			localMissingPosition(remoteHashPos.position(), remoteHashPos);
		}
	}
}
