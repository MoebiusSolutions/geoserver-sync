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

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.geotools.xml.Configuration;
import org.geotools.xml.Parser;
import org.xml.sax.SAXException;

public class GeoserverClientSynchronizer extends AbstractGeoserverClientSynchronizer {
	private final Configuration m_configuration;

    public GeoserverClientSynchronizer(Configuration configuration, String url, String postTemplate) {
        super(url, postTemplate);
        m_configuration = configuration;
    }

    @Override
	protected Object doParseWfs(InputStream is) throws IOException {
		try {
    		return new Parser(m_configuration).parse(is);
        } catch(SAXException e) {
            throw new IOException(e);
        } catch(ParserConfigurationException e) {
            throw new IOException(e);
        }
	}


}
