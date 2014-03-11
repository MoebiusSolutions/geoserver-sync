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




import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.xsd.XSDTypeDefinition;
import org.geotools.data.complex.config.FeatureTypeRegistry;
import org.geotools.feature.ComplexAttributeImpl;
import org.geotools.feature.NameImpl;
import org.geotools.gml3.GML;
import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.opengis.feature.ComplexAttribute;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;
import org.opengis.feature.type.AttributeType;
import org.opengis.feature.type.ComplexType;
import org.opengis.feature.type.Name;

public class ComplexFeaturePropertyTypeBinding extends AbstractComplexBinding {
    private final FeatureTypeRegistry registry;

    public ComplexFeaturePropertyTypeBinding(FeatureTypeRegistry registry) {
        this.registry = registry;
    }
    @Override
    public QName getTarget() {
        return GML.FeaturePropertyType;
    }

    @Override
    public Class getType() {
        return ComplexAttribute.class;
    }

    @Override
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        ComplexType featureType = (ComplexType) getType(instance);
        Node featureNode = node.getChild(Feature.class);
        List<Property> props;
        if(featureNode != null) {
            props = Collections.<Property>singletonList((Feature)featureNode.getValue());
        } else {
            props = Collections.EMPTY_LIST;
        }

        return new ComplexAttributeImpl(props, featureType, null);
    }

    private AttributeType getType(ElementInstance instance) {
        AttributeType featureType;
        XSDTypeDefinition def = instance.getTypeDefinition();
        if(def.getName() == null)
            featureType =
                registry.getDescriptor(new NameImpl(instance.getName()), null).getType();
                
            	//registry.getDescriptor(new NameImpl(instance.getNamespace(), instance.getName())).getType();
        else
            featureType =
                registry.getAttributeType(new NameImpl(def.getTargetNamespace(), def.getName()));
        return featureType;
    }
}
