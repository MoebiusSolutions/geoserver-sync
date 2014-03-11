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



import javax.xml.namespace.QName;

import org.geotools.feature.FeatureCollection;
import org.geotools.gml3.GML;
import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.opengis.feature.Feature;

public class ComplexFeatureArrayPropertyTypeBinding extends AbstractComplexBinding {
    /**
     * @generated
     */
    public QName getTarget() {
        return GML.FeatureArrayPropertyType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Class<?> getType() {
        return FeatureCollection.class;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    @Override
    public Object parse(ElementInstance instance, Node node, Object value)
        throws Exception {
        FeatureCollection fc = (FeatureCollection) node.getChildValue(FeatureCollection.class);
        if (fc != null) {
            return fc;
        }

        fc = new ComplexFeatureCollection(null, null);
        ((ComplexFeatureCollection) fc).addAll(node.getChildValues(Feature.class));
        //fc.addAll(node.getChildValues(Feature.class));

        return fc;
    }

    @Override
    public Object getProperty(Object object, QName name) {
        //passed in should be FeatureCollection, just pass it back
        return object;
    }
}
