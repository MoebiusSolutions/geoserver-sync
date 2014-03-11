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


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

/**
 * A Polexis GeoLabel object. Derived from WFS-polexis.xsd (q.v.), basically a point
 * (first &lt;choice&gt; particle) with a few label-specific attributes. Don't know
 * why they didn't extend point, but oh well.
 * @author parkerd
 *
 */
public class GeoLabel extends Point {
    public static enum XAnchorType {
        CENTER, LEFT, RIGHT
    }
    
    public static enum YAnchorType {
        BASELINE, BOTTOM, MIDDLE, TOP
    }
    
    private String text;
    private double rotation;
    private XAnchorType xAnchor = XAnchorType.CENTER;
    private YAnchorType yAnchor = YAnchorType.BOTTOM;
    
    public GeoLabel(CoordinateSequence coordinates, GeometryFactory factory) {
        super(coordinates, factory);
    }

    /**
     * @deprecated
     */
    public GeoLabel(Coordinate coordinate, PrecisionModel precisionModel, int SRID) {
        super(coordinate, precisionModel, SRID);
    }

    public String getText() {
        return text;
    }

    void setText(String text) {
        this.text = text;
    }

    public double getRotation() {
        return rotation;
    }

    void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public XAnchorType getxAnchor() {
        return xAnchor;
    }

    void setxAnchor(XAnchorType xAnchor) {
        this.xAnchor = xAnchor;
    }

    public YAnchorType getyAnchor() {
        return yAnchor;
    }

    void setyAnchor(YAnchorType yAnchor) {
        this.yAnchor = yAnchor;
    }
}
