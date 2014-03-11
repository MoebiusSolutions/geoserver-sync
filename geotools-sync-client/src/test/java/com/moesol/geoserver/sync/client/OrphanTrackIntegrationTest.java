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




import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.geoserver.wfs.WFSTestSupport;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.xml.sax.SAXException;

import com.moesol.geoserver.sync.client.GeoserverClientSynchronizer;
import com.moesol.geoserver.sync.client.xml.ComplexConfiguration;
import com.moesol.geoserver.sync.client.xml.ComplexFeatureCollection;
import com.moesol.geoserver.sync.core.FeatureSha1;
import com.moesol.geoserver.sync.core.Sha1Value;

import static org.junit.Assert.*;

public class OrphanTrackIntegrationTest extends WFSTestSupport {
	
	private static final String POST_TEMPLATE = "<wfs:GetFeature " 
        + "service=\"WFS\" " 
        + "version=\"1.1.0\" "
        + "outputFormat=\"${outputFormat}\" "
        + "xmlns:cdf=\"http://www.opengis.net/cite/data\" "
        + "xmlns:ogc=\"http://www.opengis.net/ogc\" "
        + "xmlns:wfs=\"http://www.opengis.net/wfs\" " + ">\n"
        +  "<wfs:Query typeName=\"cite:Buildings\"> "
        +   "<ogc:Filter>"
        +    "<ogc:PropertyIsEqualTo> "
        +      "<ogc:Function name=\"sha1Sync\"> "
        +       "<ogc:Literal>-all</ogc:Literal> "
        +       "<ogc:Literal>${sha1Sync}</ogc:Literal> "
        +      "</ogc:Function> "
        +      "<ogc:Literal>true</ogc:Literal> "
        +    "</ogc:PropertyIsEqualTo> "
        +   "</ogc:Filter> "
        +  "</wfs:Query> " 
        + "</wfs:GetFeature>";
	private FeatureSha1 m_engine = new FeatureSha1();

	public void testParseWfs() throws IOException, SAXException, ParserConfigurationException {
		m_engine.parseAttributesToInclude("-all");
		GeoserverClientSynchronizer clientSynchronizer = new GeoserverClientSynchronizer(makeConfiguration(), "", POST_TEMPLATE);
		InputStream is = loadResource("orphantracks.xml");
		ComplexFeatureCollection features = (ComplexFeatureCollection) clientSynchronizer.parseWfs(is);
		assertEquals(2, features.size());
		FeatureIterator<?> it = features.features();
		try {
			int i = 0;
			while (it.hasNext()) {
				Feature feature = it.next();
				Sha1Value sha1One = m_engine.computeValueSha1(feature);
				switch (i) {
				case 0:
					assertEquals("eef5926af1da677907eb3efbeaf18c086571b5d7", sha1One.toString());
					break;
				case 1:
					assertEquals("570aaf1496af083b6fcbd25012ab4eb9b70b26fe", sha1One.toString());
					break;
				}
				i++;
			}
		} finally {
			it.close();
		}
	}

	private ComplexConfiguration makeConfiguration() {
		URL xsdUrl = getClass().getResource("orphantracks.xsd");
		ComplexConfiguration configuration = new ComplexConfiguration("http://www.forwardslope.com/c2rpc", xsdUrl.toString());
		return configuration;
	}

	private InputStream loadResource(String name) throws FileNotFoundException {
		InputStream is = getClass().getResourceAsStream(name);
		if (is == null) {
			throw new FileNotFoundException(name);
		}
		return is;
	}
	
}
