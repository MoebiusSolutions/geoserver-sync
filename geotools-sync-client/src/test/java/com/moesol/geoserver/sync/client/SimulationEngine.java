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

package com.moesol.geoserver.sync.client;




import static com.moesol.geoserver.sync.client.Features.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opengis.feature.Feature;

import com.vividsolutions.jts.io.ParseException;

/**
 * Generates random changes to features to support testing.
 * The simulator is pretty simple. It has four phases:
 * <ul>
 * <li>RAMUP adds and updates features until more than stableWaterMark exist.
 * <li>STABLE just updates upto 10% of the existing features. STABLE has a 10% chances to go MIXED
 * <li>MIXED updates up to 10% of the existing features and adds up to 5% more and deletes up to 5%. MIXED has a 10% chance to go RAMPDOWN
 * <li>RAMPDOWN deletes up to 10% of the features per cycle until all features are deleted.
 * </ul>
 * @author hasting
 *
 */
public class SimulationEngine {
	private final int stableWaterMark;
	private final List<Feature> features = new ArrayList<Feature>();
	private final Random random = new Random();
	private int nextId;
	private int nextClassification;
	private int numCreated;
	private int numUpdated;
	private int numDeleted;
	enum State {
		RAMPUP, STABLE, MIXED, RAMPDOWN;
	}
	private State state = State.RAMPUP;
	
	public SimulationEngine(int m) {
		stableWaterMark = m;
	}
	
	public Feature[] getFeatures() {
		return features.toArray(new Feature[0]);
	}
	
	public void makeSomeChanges() throws ParseException {
		switch (state) {
		case RAMPUP: rampup(); break;
		case STABLE: stable(); break;
		case MIXED: mixed(); break;
		case RAMPDOWN: rampdown(); break;
		}
	}

	private void rampup() throws ParseException {
		int numToCreate = Math.max(random.nextInt(stableWaterMark / 10), 1);
		makeCreates(numToCreate);
		makeUpdates();
		if (features.size() >= stableWaterMark) {
			state = State.STABLE;
		}
	}

	private void rampdown() throws ParseException {
		int numToDelete = Math.max(random.nextInt(stableWaterMark / 10), 1);
		makeDeletes(numToDelete);
		if (features.size() == 0) {
			state = State.RAMPUP;
		} else {
			makeUpdates();
		}
	}

	private void stable() throws ParseException {
		makeUpdates();
		if (random.nextInt(10) == 0) {
			state = State.MIXED;
		}
	}
	
	private void mixed() throws ParseException {
		makeCreates(random.nextInt(stableWaterMark/20));
		makeUpdates();
		makeDeletes(random.nextInt(stableWaterMark/20));
		if (random.nextInt(10) == 0) {
			state = State.RAMPDOWN;
		}
	}
	
	private void makeCreates(int numToCreate) throws ParseException {
		for (int i = 0; i < numToCreate; i++) {
			features.add(f("F" + nextId++, nextClassification++));
			numCreated++;
		}
	}
	
	private void makeDeletes(int numToDelete) {
		for (int i = 0; i < numToDelete; i++) {
			if (features.size() == 0) { break; }
			int idx = random.nextInt(features.size());
			features.remove(idx);
			numDeleted++;
		}
	}
	
	private void makeUpdates() throws ParseException {
		int numToUpdate = random.nextInt(stableWaterMark);
		for (int i = 0; i < numToUpdate; i++) {
			int idx = random.nextInt(features.size());
			Feature f = features.get(idx);
			Feature u = f(f.getIdentifier().getID(), nextClassification++);
			features.set(idx, u);
			numUpdated++;
		}
	}

	public int getNumCreated() {
		return numCreated;
	}

	public int getNumUpdated() {
		return numUpdated;
	}

	public int getNumDeleted() {
		return numDeleted;
	}
	
	public void resetCounts() {
		numCreated = 0;
		numUpdated = 0;
		numDeleted = 0;
	}

}
