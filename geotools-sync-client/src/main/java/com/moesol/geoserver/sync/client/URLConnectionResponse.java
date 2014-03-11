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
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

public class URLConnectionResponse implements Response {
	private final HttpURLConnection m_connection;

	public URLConnectionResponse(HttpURLConnection conn) {
		m_connection = conn;
	}

	@Override
	public int getResponseCode() throws IOException {
		return m_connection.getResponseCode();
	}

	@Override
	public String getResponseMessage() throws IOException {
		return m_connection.getResponseMessage();
	}
	
	@Override
	public String getContentEncoding() {
		return m_connection.getContentEncoding();
	}

	@Override
	public InputStream getResultStream() throws IOException {
        InputStream is = m_connection.getInputStream();
        String encoding = m_connection.getContentEncoding();
        if ("gzip".equalsIgnoreCase(encoding)) { 
        	is = new GZIPInputStream(is); 
        }
        return is;
	}

}
