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

import static org.mockito.Matchers.*;
import static junit.framework.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.opengis.wfs.FeatureCollectionType;
import net.opengis.wfs.WfsFactory;

import org.geotools.data.FeatureSource;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.mockito.ArgumentMatcher;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.identity.Identifier;

import com.moesol.geoserver.sync.client.FeatureAccessor;
import com.moesol.geoserver.sync.client.FeatureAccessorImpl;
import com.moesol.geoserver.sync.client.xml.ComplexConfiguration;
import com.moesol.geoserver.sync.samples.Samples;
import com.vividsolutions.jts.io.ParseException;

/**
 * Shared helper methods for Features
 * @author hasting
 */
public class Features {
	private static final Samples SAMPLES = new Samples();

	public static FeatureCollectionType make(Feature... features) throws IOException {
        MemoryDataStore data = new MemoryDataStore();
        data.createSchema(SAMPLES.getFlagType());

        for (Feature f : features) {
        	data.addFeature((SimpleFeature) f);
        }
        FeatureSource<SimpleFeatureType, SimpleFeature> fs = data.getFeatureSource(SAMPLES.getFlagType().getName());
        
        FeatureCollectionType fct = WfsFactory.eINSTANCE.createFeatureCollectionType();
		addFeatureToCollection(fct, fs);
		return fct;
	}
	public static Feature f(String fid, int classification) throws ParseException {
		return SAMPLES.buildSimpleFeature(fid, classification);
	}

	@SuppressWarnings("unchecked")
	private static void addFeatureToCollection(FeatureCollectionType fct,
			FeatureSource<SimpleFeatureType, SimpleFeature> fs)
			throws IOException {
		fct.getFeature().add(fs.getFeatures());
	}
	
	public static ComplexConfiguration makeConfiguration() {
		URL xsdUrl = Features.class.getResource("buildings.xsd");
		
		ComplexConfiguration configuration = new ComplexConfiguration("http://www.opengis.net/cite", xsdUrl.toString());
		return configuration;
	}

	public static Map<Identifier, FeatureAccessor> asMap(FeatureCollectionType collection) {
		Map<Identifier, FeatureAccessor> map = new HashMap<Identifier, FeatureAccessor>();
		
		@SuppressWarnings("unchecked")
		List<FeatureCollection<?, ?>> resultsList = collection.getFeature();
		for (FeatureCollection<?, ?> fc : resultsList) {
	        FeatureIterator<?> iterator = fc.features();
	        try {
	            while (iterator.hasNext()) {
	            	Feature feature = iterator.next();
	            	FeatureAccessor accessor = new FeatureAccessorImpl(feature);
	            	map.put(feature.getIdentifier(), accessor);
	            }
	        } finally {
	        	iterator.close();
	        }
		}
		return map;
	}

	public static String asString(RecordingFeatureChangeListener listener) {
		StringBuilder sb = new StringBuilder();
		for (Feature feature : listener.getUpdates()) {
			sb.append(asString(feature));
		}
		return sb.toString();
	}
	public static String asString(Map<Identifier, FeatureAccessor> map) {
		StringBuilder sb = new StringBuilder();
		for (Identifier id : map.keySet()) {
			FeatureAccessor accessor = map.get(id);
			Feature feature = accessor.getFeature();
			
			asString(sb, feature);
		}
		return sb.toString();
	}
	public static String asString(List<Feature> expected) {
		StringBuilder sb = new StringBuilder();
		for (Feature feature : expected) {
			asString(sb, feature);
		}
		return sb.toString();
	}

	public static String asString(Feature feature) {
		StringBuilder sb = new StringBuilder();
		asString(sb,feature);
		return sb.toString();
	}
	public static void asString(StringBuilder sb, Feature feature) {
		sb.append("f(\"");
		sb.append(feature.getIdentifier().toString());
		sb.append("\",");
		sb.append(feature.getProperty("classification").getValue().toString());
		sb.append("),");
	}

	public static class IsSameFeatureSet extends ArgumentMatcher<Feature[]> {
		private final HashSet<Feature> expected;

		public IsSameFeatureSet(Feature[] features) {
			expected = new HashSet<Feature>(Arrays.asList(features));
		}

		@Override
		public boolean matches(Object argument) {
			Feature[] actual = (Feature[]) argument;
			return expected.containsAll(Arrays.asList(actual));
		}
	}
	public static Feature[] featuresEq(Feature...exp) {
		return argThat(new IsSameFeatureSet(exp));
	}
	public static void assertFeaturesEq(Feature[] exp, Features[] act) {
		HashSet<Feature> expected = new HashSet<Feature>(Arrays.asList(exp));
		assertTrue(expected.containsAll(Arrays.asList(act)));
	}
	
}
