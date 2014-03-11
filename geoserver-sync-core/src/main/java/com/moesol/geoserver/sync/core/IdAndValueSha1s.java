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




public class IdAndValueSha1s {
	private final Sha1Value idSha1;
	private final Sha1Value valueSha1;
	
	public IdAndValueSha1s(Sha1Value id, Sha1Value value) {
		this.idSha1 = id;
		this.valueSha1 = value;
	}
	
	public Sha1Value getIdSha1() {
		return idSha1;
	}
	public Sha1Value getValueSha1() {
		return valueSha1;
	}

	@Override
	public String toString() {
		return String.format("{ id: %s, value: %s }", getIdSha1(), getValueSha1());
	}
	
}
