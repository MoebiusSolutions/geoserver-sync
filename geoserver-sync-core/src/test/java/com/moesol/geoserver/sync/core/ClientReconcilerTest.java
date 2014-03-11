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



import static org.mockito.Mockito.*;

import com.moesol.geoserver.sync.core.ClientReconciler;
import com.moesol.geoserver.sync.core.ReconcilerDelete;
import com.moesol.geoserver.sync.grouper.ReconcileTestBase;
import com.moesol.geoserver.sync.json.Sha1SyncJson;
import com.moesol.geoserver.sync.json.Sha1SyncPositionHash;

public class ClientReconcilerTest extends ReconcileTestBase {
	private ReconcilerDelete delete = mock(ReconcilerDelete.class);
	
	Sha1SyncJson local = makeJson().level(0);
	Sha1SyncJson remote = makeJson().level(0);
	Sha1SyncJson output = makeJson().level(0);
	private ClientReconciler reconciler = new ClientReconciler(local, remote);
	
	public void testComputeOutput_Empty() {
		reconciler.computeOutput(output);
		
		assertEquals(0, output.h.size());
	}
	
	public void testComputeOutput_LevelZero_Same() {
		local.h.add(makePosition("", "aaaa"));
		remote.h.add(makePosition("", "aaaa"));
		
		reconciler.computeOutput(output);
		assertEquals(0, output.h.size());
	}
	
	public void testComputeOutput_LevelZero_Different() {
		local.h.add(makePosition("", "0000"));
		remote.h.add(makePosition("", "1111"));
		
		reconciler.computeOutput(output);
		assertEquals(1, output.h.size());
		check(0, "", "0000");
	}
	
	public void testComputeOutput_LevelOne_Same() {
		local.h.add(makePosition("aa", "0000"));
		local.h.add(makePosition("bb", "1111"));
		remote.h.add(makePosition("aa", "0000"));
		remote.h.add(makePosition("bb", "1111"));
		output.h.add(makePosition("aaaa", "0000"));
		output.h.add(makePosition("aabb", "1111"));
		output.h.add(makePosition("bbaa", "2222"));
		output.h.add(makePosition("bbbb", "3333"));
		
		reconciler.computeOutput(output);
		assertEquals(0, output.h.size());
	}

	public void testComputeOutput_LevelOne_Differ() {
		local.h.add(makePosition("aa", "0000"));
		local.h.add(makePosition("bb", "1111"));
		remote.h.add(makePosition("aa", "0001"));
		remote.h.add(makePosition("bb", "1111"));
		
		reconciler.computeOutput(output);
		assertEquals(1, output.h.size());
		check(0, "aa", "0000");
	}

	public void testComputeOutput_LevelOne_LocalLess() {
		local.h.add(makePosition("bb", "1111"));
		remote.h.add(makePosition("aa", "0001"));
		remote.h.add(makePosition("bb", "1111"));
		output.h.add(makePosition("bbaa", "2222"));
		output.h.add(makePosition("bbbb", "3333"));
		
		reconciler.computeOutput(output);
		assertEquals(1, output.h.size());
		check(0, "aa", null);
	}
	
	public void testComputeOutput_LevelOne_RemoteLess() {
		local.h.add(makePosition("aa", "0000"));
		local.h.add(makePosition("bb", "1111"));
		remote.h.add(makePosition("bb", "1111"));
		
		reconciler.computeOutput(output);
		// Remote thinks it is in sync
		assertEquals(0, output.h.size());
		verify(delete).deleteGroup(new Sha1SyncPositionHash().position("aa").summary("0000"));
	}

	public void testComputeOutput_LevelOne_RemoteLess_OneChange() {
		local.h.add(makePosition("aa", "0000"));
		local.h.add(makePosition("bb", "1110"));
		remote.h.add(makePosition("bb", "1111"));
		
		reconciler.computeOutput(output);
		// Remote thinks it is in sync
		assertEquals(1, output.h.size());
		check(0, "bb", "1110");
		verify(delete).deleteGroup(new Sha1SyncPositionHash().position("aa").summary("0000"));
	}
	
	public void testComputeOutput_LevelOne_RemoteEmpty() {
		local.h.add(makePosition("aa", "0000"));
		local.h.add(makePosition("bb", "1110"));
		
		reconciler.computeOutput(output);
		assertEquals(0, output.h.size());
	}

	private void check(int i, String position, String summary) {
		assertEquals(position, output.h.get(i).position());
		assertEquals(summary, output.h.get(i).summary());
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		reconciler.setDelete(delete);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		verifyNoMoreInteractions(delete);
	}
	
}
