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




import java.util.List;

import javax.xml.namespace.QName;

import org.geotools.feature.FeatureCollection;
import org.geotools.gml3.GML;
import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.opengis.feature.Feature;
import org.opengis.feature.type.FeatureType;

public class ComplexFeatureCollectionTypeBinding extends AbstractComplexBinding {

    @Override
    public QName getTarget() {
        return GML.AbstractFeatureCollectionType;
    }

    @Override
    public Class getType() {
        return FeatureCollection.class;
    }

    @Override
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        FeatureCollection coll = (FeatureCollection) node.getChildValue(FeatureCollection.class);
        List<Node> featureMembers = node.getChildren("featureMember");

        if(coll == null) {
            FeatureType type = null;
            if(featureMembers.size() > 0) {
                Feature f = (Feature) featureMembers.get(0).getChildValue(Feature.class);
                if(f != null) {
                    type = f.getType();
                }
            }
            coll = new ComplexFeatureCollection(null, type);
        }

        for(Node n : featureMembers) {
            ((List<Node>) coll).addAll(n.getChildValues(Feature.class));
            
        	//coll.addAll(n.getChildValues(Feature.class));
        }

        return coll;
    }
}
