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



import java.util.Collection;
import java.util.Collections;

import javax.xml.namespace.QName;

import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;

public class IgnoredTypeBinding extends AbstractComplexBinding {
    public final QName target;

    public IgnoredTypeBinding(QName target) {
        this.target = target;
    }

    @Override
    public QName getTarget() {
        return target;
    }

    @Override
    public Class getType() {
        // For some reason, the parser is expecting a Collection for unknown types
        return Collection.class;
    }

    @Override
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        return Collections.EMPTY_LIST;
    }
}
