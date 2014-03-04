/**
 *
 *  #%L
 *  geoserver-sync-core
 *  $Id:$
 *  $HeadURL:$
 *  %%
 *  Copyright (C) 2013 Moebius Solutions Inc.
 *  %%
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as
 *  published by the Free Software Foundation, either version 2 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with this program.  If not, see
 *  <http://www.gnu.org/licenses/gpl-2.0.html>.
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
