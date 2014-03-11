/**
 *
 *  #%L
 *  geoserver-sync-core
 *  $Id:$
 *  $HeadURL:$
 *  %%
 *  Copyright (C) 2013 Moebius Solutions Inc.
 *  %%
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  #L%
 *
 */

package com.moesol.geoserver.sync.client;




import java.util.ArrayList;
import java.util.List;

import org.opengis.feature.Feature;
import org.opengis.filter.identity.Identifier;

public class FeaturesChangedAdaptor implements FeatureChangeListener, RoundListener {
	private final List<Feature> creates = new ArrayList<Feature>();
	private final List<Feature> updates = new ArrayList<Feature>();
	private final List<Feature> deletes = new ArrayList<Feature>();
	private final FeaturesChangedListener targetListener;
	
	public FeaturesChangedAdaptor(FeaturesChangedListener target) {
		this.targetListener = target;
	}
	
	@Override
	public void beforeRound(int r) { }

	@Override
	public void afterRound(int r) { }

	@Override
	public void afterSynchronize() {
		targetListener.featuresCreate(creates.toArray(new Feature[creates.size()]));
		targetListener.featuresUpdate(updates.toArray(new Feature[updates.size()]));
		targetListener.featuresDelete(deletes.toArray(new Feature[deletes.size()]));
		
		creates.clear();
		updates.clear();
		deletes.clear();
	}

	@Override
	public void sha1Collision() { }

	@Override
	public void featureCreate(Identifier fid, Feature feature) {
		creates.add(feature);
	}

	@Override
	public void featureUpdate(Identifier fid, Feature feature) {
		updates.add(feature);
	}

	@Override
	public void featureDelete(Identifier fid, Feature feature) {
		deletes.add(feature);
	}

}
