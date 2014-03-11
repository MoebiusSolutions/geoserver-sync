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

package com.moesol.geoserver.sync.samples;




import org.geotools.feature.simple.SimpleFeatureBuilder;

public class SampleBuilder {
	private final SimpleFeatureBuilder m_builder;

	public SampleBuilder(Samples samples) {
		m_builder = new SimpleFeatureBuilder(samples.getFlagType());
	}
	
	public SampleBuilder add(Object o) {
		m_builder.add(o);
		return this;
	}
}
