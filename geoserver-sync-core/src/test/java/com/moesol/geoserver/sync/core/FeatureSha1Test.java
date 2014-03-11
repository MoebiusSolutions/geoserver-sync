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




import java.io.IOException;

import junit.framework.TestCase;

import org.geotools.data.DataAccess;
import org.geotools.data.DataAccessFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.SampleDataAccessData;
import org.geotools.data.SampleDataAccessFactory;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.type.FeatureType;

import com.moesol.geoserver.sync.core.FeatureSha1;
import com.moesol.geoserver.sync.core.Sha1Value;
import com.moesol.geoserver.sync.samples.Samples;
import com.vividsolutions.jts.io.ParseException;

public class FeatureSha1Test extends TestCase {

	private final Samples m_samples = new Samples();
	
	public FeatureSha1Test() {
	}

	public void testSha1One() throws ParseException {
		Feature feature = m_samples.buildSimpleFeature("fid1");
		FeatureSha1 sync = new FeatureSha1();
		Sha1Value sha1 = sync.computeValueSha1(feature);
		assertEquals("56e82e2d2452830bbdb9bc1ce353a2e159996308", sha1.toString());
		
		sync.parseAttributesToInclude("-all");
		sha1 = sync.computeValueSha1(feature);
		assertEquals("ee99b7fbcfd28b5a84fd478d8c92d574d3e4875a", sha1.toString());
		
		sync.parseAttributesToInclude("name");
		sha1 = sync.computeValueSha1(feature);
		assertEquals("d4dc2740d2d92547f941bfaf677e11c9b99605ee", sha1.toString());

		sync.parseAttributesToInclude("name,classification");
		sha1 = sync.computeValueSha1(feature);
		assertEquals("c417ad12c0d5c8783e15cd6a1dcc333925febd4e", sha1.toString());
		

		sync.parseAttributesToInclude("classification,name");
		sha1 = sync.computeValueSha1(feature);
		assertEquals("c417ad12c0d5c8783e15cd6a1dcc333925febd4e", sha1.toString());

		sync.parseAttributesToInclude("name,classification,height");
		sha1 = sync.computeValueSha1(feature);
		assertEquals("ee99b7fbcfd28b5a84fd478d8c92d574d3e4875a", sha1.toString());
	}
	
	public void testValueIdValue() throws ParseException {
		Feature feature = m_samples.buildSimpleFeature("fid1");
		FeatureSha1 sync = new FeatureSha1();
		Sha1Value valueSha1 = sync.computeValueSha1(feature);
		assertEquals("56e82e2d2452830bbdb9bc1ce353a2e159996308", valueSha1.toString());
		
		Sha1Value idSha1 = sync.computeIdSha1(feature);
		assertEquals("56e82e2d2452830bbdb9bc1ce353a2e159996308", idSha1.toString());

		Sha1Value secondValueSha1 = sync.computeValueSha1(feature);
		assertEquals("56e82e2d2452830bbdb9bc1ce353a2e159996308", secondValueSha1.toString());
	}
	
	public void testComplex() throws IOException {
		DataAccess<FeatureType, Feature> dataAccess = DataAccessFinder.getDataStore(SampleDataAccessFactory.PARAMS);
		FeatureSource<FeatureType, Feature> featureSource = dataAccess.getFeatureSource(SampleDataAccessData.MAPPEDFEATURE_TYPE_NAME);
		FeatureCollection<FeatureType, Feature> featureCollection = featureSource.getFeatures();
		FeatureIterator<Feature> iterator = featureCollection.features();
		try {
			Feature feature = iterator.next(); // Just get first feature
			FeatureSha1 sync = new FeatureSha1();
			Sha1Value sha1 = sync.computeValueSha1(feature);
			assertEquals("e88d59d2142c8315878752805e15ddb6521fd9e1", sha1.toString());
		} finally {
			iterator.close();
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("sha1.keep.name", "true");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		System.clearProperty("sha1.keep.name");
	}

}
