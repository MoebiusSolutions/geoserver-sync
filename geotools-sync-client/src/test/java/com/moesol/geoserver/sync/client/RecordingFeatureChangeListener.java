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




import java.util.ArrayList;
import java.util.List;

import org.opengis.feature.Feature;
import org.opengis.filter.identity.Identifier;

import com.moesol.geoserver.sync.client.FeatureChangeListener;

public class RecordingFeatureChangeListener implements FeatureChangeListener {
	private final FeatureChangeListener real;
	private final List<Feature> updates = new ArrayList<Feature>();
	
	public List<Feature> getUpdates() {
		return updates;
	}

	public RecordingFeatureChangeListener(FeatureChangeListener r) {
		this.real = r;
	}

	@Override
	public void featureCreate(Identifier fid, Feature feature) {
		real.featureCreate(fid, feature);
	}

	@Override
	public void featureUpdate(Identifier fid, Feature feature) {
		real.featureUpdate(fid, feature);
		updates.add(feature);
	}

	@Override
	public void featureDelete(Identifier fid, Feature feature) {
		real.featureDelete(fid, feature);
	}

	public void reset() {
		updates.clear();
	}

}
