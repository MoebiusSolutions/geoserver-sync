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




import com.moesol.geoserver.sync.core.ServerReconciler;
import com.moesol.geoserver.sync.json.Sha1SyncJson;
import com.moesol.geoserver.sync.json.Sha1SyncPositionHash;

import junit.framework.TestCase;

public class ServerReconcilerTest extends TestCase {

//	public void testFilterDownNextLevel_DupPositions() {
//		Sha1SyncJson local = new Sha1SyncJson().level(0).max(2).hashes(
//				new Sha1SyncPositionHash().position("").summary("010101")
//			);
//			Sha1SyncJson remote = new Sha1SyncJson().level(0).max(2).hashes(
//				new Sha1SyncPositionHash().position("").summary("000000"),
//				new Sha1SyncPositionHash().position("").summary("000000")
//			);
//			Sha1SyncJson output = new Sha1SyncJson().level(2).max(1).hashes(
//				new Sha1SyncPositionHash().position("aa").summary("111111"),
//				new Sha1SyncPositionHash().position("bb").summary("222222"),
//				new Sha1SyncPositionHash().position("cc").summary("333333")
//			);
//			ServerReconciler r = new ServerReconciler(local, remote);
//			try {
//				r.filterDownNextLevel(output);
//				fail("ex");
//			} catch (IllegalStateException e) {
//				assertEquals("Duplicate prefix()", e.getMessage());
//			}
//	}
	
	public void testFilterDownNextLevel_Level1() {
		Sha1SyncJson local = new Sha1SyncJson().level(0).max(2).hashes(
			new Sha1SyncPositionHash().position("").summary("010101")
		);
		Sha1SyncJson remote = new Sha1SyncJson().level(0).max(2).hashes(
			new Sha1SyncPositionHash().position("").summary("000000")
		);
		Sha1SyncJson output = new Sha1SyncJson().level(2).max(1).hashes(
			new Sha1SyncPositionHash().position("aa").summary("111111"),
			new Sha1SyncPositionHash().position("bb").summary("222222"),
			new Sha1SyncPositionHash().position("cc").summary("333333")
		);
		ServerReconciler r = new ServerReconciler(local, remote);
		r.filterDownNextLevel(output);
		check(output, 0, "aa", "111111");
		check(output, 1, "bb", "222222");
		check(output, 2, "cc", "333333");
		assertEquals(3, output.hashes().size());
	}
	
	public void testFilterDownNextLevel_Level2() {
		Sha1SyncJson local = new Sha1SyncJson().level(1).max(2).hashes(
			new Sha1SyncPositionHash().position("aa").summary("111111"),
			new Sha1SyncPositionHash().position("bb").summary("222222"),
			new Sha1SyncPositionHash().position("cc").summary("333333")
		);
		Sha1SyncJson remote = new Sha1SyncJson().level(1).max(2).hashes(
			new Sha1SyncPositionHash().position("aa").summary(null),
			new Sha1SyncPositionHash().position("bb").summary("212121"),
			new Sha1SyncPositionHash().position("dd").summary("444444")
		);
		Sha1SyncJson output = new Sha1SyncJson().level(2).max(1).hashes(
			new Sha1SyncPositionHash().position("aaaa").summary("aaaaaa"),
			new Sha1SyncPositionHash().position("aabb").summary("aabbbb"),
			new Sha1SyncPositionHash().position("bbbb").summary("bbbbbb"),
			new Sha1SyncPositionHash().position("ccaa").summary("ccaaaa")
		);
		ServerReconciler r = new ServerReconciler(local, remote);
		r.filterDownNextLevel(output);
		check(output, 0, "aaaa", "aaaaaa");
		check(output, 1, "aabb", "aabbbb");
		check(output, 2, "bbbb", "bbbbbb");
		check(output, 3, "dd", null);
		assertEquals(4, output.hashes().size());
	}

    private void check(Sha1SyncJson sync, int i, String position, String summary) {
        assertEquals(position, sync.hashes().get(i).position());
        assertEquals(summary,  sync.hashes().get(i).summary());
    }
}
