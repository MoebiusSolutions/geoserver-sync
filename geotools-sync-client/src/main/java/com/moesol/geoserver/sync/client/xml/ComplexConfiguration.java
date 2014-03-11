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

import java.util.Map;

import org.geotools.gml3.GML;
import org.geotools.gml3.GMLConfiguration;
import org.geotools.xml.Configuration;
import org.geotools.xs.XS;
import org.geotools.xs.XSConfiguration;
import org.picocontainer.MutablePicoContainer;

public class ComplexConfiguration extends Configuration {
    
    public ComplexConfiguration(String namespace, String schemaLocation) {
        super(new ResolvingApplicationSchemaXSD(namespace, schemaLocation));
        addDependency(new XSConfiguration());
        addDependency(new GMLConfiguration());
    }

    @Override
    protected void configureBindings(Map bindings) {
        super.configureBindings(bindings);

    }

    @Override
    protected void configureContext(MutablePicoContainer container) {
        super.configureContext(container);
    }

    @Override
    protected void registerBindings(Map bindings) {
        super.registerBindings(bindings);

        /*
         * gml:CodeType is a simple type with attributes and requires special
         * handling. All such types should probably be handled by one binding
         * class.
         */
        bindings.put(GML.CodeType, CodeTypeBinding.class);
    }
}
