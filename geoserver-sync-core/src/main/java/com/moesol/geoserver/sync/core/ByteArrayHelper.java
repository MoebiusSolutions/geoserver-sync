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

public class ByteArrayHelper {
	private static final char[] charMap = "0123456789abcdef".toCharArray();
	
	public static String toHex(byte[] a) {
		char[] result = new char[2 * a.length];
		for (int i = 0; i < a.length; i++) {
			int high = (0xF0 & a[i]) >> 4;
			int low =  (0x0F & a[i]);
			result[2*i    ] = charMap[high];
			result[2*i + 1] = charMap[low];
		}
		return new String(result);
	}
	
//	public static int toInteger(byte[] a) {
//		int v = 0;
//		
//		for (byte b : a) {
//			v = (v << 8) | (0xFF &b);
//		}
//		return v;
//	}

	public static byte[] fromHex(String string) {
		if ((string.length() & 1) != 0) {
			throw new IllegalArgumentException("String length must be even");
		}
		int rl = string.length() / 2;
		byte[] result = new byte[rl];
		for (int i = 0; i < rl; i++) {
			String hex = string.substring(i * 2, i* 2 + 2);
			result[i] = (byte) Integer.parseInt(hex, 16);
		}
		return result;
	}
}
