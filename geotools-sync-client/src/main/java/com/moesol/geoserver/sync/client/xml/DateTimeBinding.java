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

package com.moesol.geoserver.sync.client.xml;



import java.sql.Timestamp;
import java.text.ParsePosition;
import java.util.Calendar;

import javax.xml.namespace.QName;

import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.geotools.xml.impl.XsDateTimeFormat;
import org.geotools.xs.XS;

public class DateTimeBinding extends AbstractComplexBinding {
    XsDateTimeFormat format = new XsDateTimeFormat();
    GCCSDateTimeFormat gccsFormat = new GCCSDateTimeFormat();
    @Override
    public QName getTarget() {
        return XS.DATETIME;
    }

    @Override
    public Class getType() {
        // TODO Auto-generated method stub
        return Timestamp.class;
    }

    @Override
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        ParsePosition pos;
        Calendar cal = (Calendar) format.parseObject((String)value, pos = new ParsePosition(0));

        if(cal == null) {
            cal = (Calendar) gccsFormat.parseObject((String)value, pos = new ParsePosition(0));
        }

        if(cal == null) {
            throw new IllegalArgumentException("Invalid date: \"" + value + "\" starting at: \"" +
                ((String) value).substring(pos.getErrorIndex()) + "\"");
        }

        return new Timestamp(cal.getTimeInMillis());
    }
}
