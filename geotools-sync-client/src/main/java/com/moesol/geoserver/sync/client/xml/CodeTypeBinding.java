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

package com.moesol.geoserver.sync.client.xml;



import java.util.Arrays;

import javax.xml.namespace.QName;

import org.geotools.feature.AttributeImpl;
import org.geotools.feature.ComplexAttributeImpl;
import org.geotools.feature.NameImpl;
import org.geotools.feature.type.AttributeDescriptorImpl;
import org.geotools.gml3.GMLSchema;
import org.geotools.gml3.v3_2.GML;
import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.geotools.xs.XSSchema;
import org.opengis.feature.ComplexAttribute;
import org.opengis.feature.Property;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.ComplexType;

public class CodeTypeBinding extends AbstractComplexBinding {
    private static final ComplexType CODETYPE_TYPE = GMLSchema.CODETYPE_TYPE;
    private static final AttributeDescriptor CODETYPE_TYPE$CODESPACE =
        (AttributeDescriptor) CODETYPE_TYPE.getDescriptor(new NameImpl("http://www.opengis.net/gml", "codeSpace"));
    private static final AttributeDescriptor SIMPLECONTENT_DESCRIPTOR =
        new AttributeDescriptorImpl(XSSchema.STRING_TYPE, new NameImpl(null, "simpleContent"), 1, 1, true, null);

    @Override
    public QName getTarget() {
        return GML.CodeType;
    }

    @Override
    public Class<ComplexAttribute> getType() {
        // TODO Auto-generated method stub
        return ComplexAttribute.class;
    }

    @Override
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        return new ComplexAttributeImpl(Arrays.<Property> asList(new AttributeImpl(node.getAttributeValue("codeSpace"),
            CODETYPE_TYPE$CODESPACE, null), new AttributeImpl(value, SIMPLECONTENT_DESCRIPTOR, null)), CODETYPE_TYPE, null);
    }
}
