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
