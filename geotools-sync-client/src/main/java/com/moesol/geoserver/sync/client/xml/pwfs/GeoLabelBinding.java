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

package com.moesol.geoserver.sync.client.xml.pwfs;




import java.math.BigDecimal;

import javax.xml.namespace.QName;

import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;

import com.moesol.geoserver.sync.client.xml.pwfs.GeoLabel.XAnchorType;
import com.moesol.geoserver.sync.client.xml.pwfs.GeoLabel.YAnchorType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

public class GeoLabelBinding extends AbstractComplexBinding {
    public static final String NS_PWFS = "http://www.polexis.com/pwfs";
    public static final QName QN_GEO_LABEL_TYPE = new QName(NS_PWFS, "GeoLabelType");

    private final GeometryFactory gf;

    public GeoLabelBinding(GeometryFactory gf) {
        this.gf = gf;
    }

    @Override
    public QName getTarget() {
        return QN_GEO_LABEL_TYPE;
    }

    @Override
    public Class<?> getType() {
        return GeoLabel.class;
    }

    @Override
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        CoordinateSequence seq = new CoordinateArraySequence(new Coordinate[]{(Coordinate) node.getChildValue(Coordinate.class)});
        GeoLabel label = new GeoLabel(seq, gf);
        label.setRotation(((BigDecimal)node.getChildValue("rotation")).doubleValue());
        label.setText((String) node.getChildValue("text"));
        label.setxAnchor(XAnchorType.valueOf(node.getChildValue("xAnchor").toString().toUpperCase()));
        label.setyAnchor(YAnchorType.valueOf(node.getChildValue("yAnchor").toString().toUpperCase()));
        return label;
    }

}
