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



import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.moesol.geoserver.sync.client.Response;
import static org.junit.Assert.*;

public class TestResponse implements Response {
	private int m_responseCode;
	private String m_responseMessage;
	private InputStream m_resultStream;

	@Override
	
	public int getResponseCode() throws IOException {
		return m_responseCode;
	}
	public void setResponseCode(int responseCode) {
		m_responseCode = responseCode;
	}

	@Override
	public String getResponseMessage() throws IOException {
		return m_responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		m_responseMessage = responseMessage;
	}

	@Override
	public InputStream getResultStream() throws IOException {
		return m_resultStream;
	}
	public void setResultStream(InputStream resultStream) {
		m_resultStream = resultStream;
	}
	@Override
	public String getContentEncoding() {
		return "text";
	}
	@Test
	public void dummyTest(){
		assertEquals(1, 1);
	}

}
