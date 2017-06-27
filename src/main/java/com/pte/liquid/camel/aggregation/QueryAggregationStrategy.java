//Copyright 2017 Paul Tegelaar
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package com.pte.liquid.camel.aggregation;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

/**
 * Aggregate paramater list so batch query can be done
 * @author Paul Tegelaar
 *
 */
@Component
public class QueryAggregationStrategy implements AggregationStrategy {

    @SuppressWarnings("unchecked")
    @Override
    public Exchange aggregate(final Exchange original, final Exchange resource) {
        final List<Object> newBody = resource.getIn().getBody(List.class);
        ArrayList<List<Object>> list = null;
        if (original == null) {
            list = new ArrayList<List<Object>>();
            if (newBody != null) {
                list.add(newBody);
            }
            resource.getIn().setBody(list);
            return resource;
        } else {
            list = original.getIn().getBody(ArrayList.class);
            if (newBody != null) {
                list.add(newBody);
            }
            return original;
        }
    }

}
