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




import java.util.Comparator;

/**
 * Compare pairs based on which sha1 is being used for the bucket prefix.
 * @author hasting
 */
public class IdAndValueSha1Comparator implements Comparator<IdAndValueSha1s> {
	private VersionFeatures versionFeatures;

	public IdAndValueSha1Comparator(VersionFeatures vf) {
		versionFeatures = vf;
	}
	
	@Override
	public int compare(IdAndValueSha1s left, IdAndValueSha1s right) {
		return versionFeatures.getBucketPrefixSha1(left).compareTo(versionFeatures.getBucketPrefixSha1(right));
	}
	
}
