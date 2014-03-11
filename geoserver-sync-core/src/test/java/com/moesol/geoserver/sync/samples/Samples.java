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
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class Samples {
	private final SimpleFeatureType m_flagType;

	public SimpleFeatureType getFlagType() {
		return m_flagType;
	}

	public Samples() {
		m_flagType = buildType();
	}
	
	public SampleBuilder builder() {
		return new SampleBuilder(this);
	}

	public static SimpleFeatureType buildType() {
		SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();
		
		b.setName("Flag");
	
		//set the name
		b.setName( "Flag" );
	
		//add some properties
		b.add( "name", String.class );
		b.add( "classification", Integer.class );
		b.add( "height", Double.class );
	
		//add a geometry property
		b.setCRS( DefaultGeographicCRS.WGS84); // set crs first
		b.add( "location", Point.class ); // then add geometry
	
		return b.buildFeatureType();
	}

	public static Point makePoint(String wellKnownText) throws ParseException {
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory( null );
	
		WKTReader reader = new WKTReader( geometryFactory );
		Point point = (Point) reader.read(wellKnownText);
		return point;
	}

	public Feature buildSimpleFeature(String fid) throws ParseException {
		SimpleFeatureBuilder builder = new SimpleFeatureBuilder(m_flagType);
		builder.add("US");
		builder.add(1);
		builder.add(20.5);
		builder.add(Samples.makePoint("POINT(1 2)"));
		return builder.buildFeature("fid-0001");
	}

	public Feature buildSimpleFeature(String fid, int classification) throws ParseException {
		SimpleFeatureBuilder builder = new SimpleFeatureBuilder(m_flagType);
		builder.add("US");
		builder.add(classification);
		builder.add(20.5);
		builder.add(Samples.makePoint("POINT(1 2)"));
		return builder.buildFeature(fid);
	}
}
