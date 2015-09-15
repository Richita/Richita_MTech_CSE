package com.microkernel.core.xml.configuration;

import com.microkernel.core.flow.support.StateTransition;
import com.microkernel.core.service.Service;
import com.microkernel.core.state.ParallelState;
import com.microkernel.core.state.SequentialState;
import com.microkernel.core.xml.Parser;
import com.microkernel.core.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class ExecutorParser implements Parser<StateTransition>{
    private final String ELE_EXECUTOR = "executor";

    private final String ATTR_ID = "id";

    private final String ATTR_TYPE = "type";

    private final String ATTR_NEXT = "next";

    private final String ELE_SERVICE = "service";

    private final String PARALLEL = "parallel";

    private final String SEQUENTIAL = "sequential";


    @Override
    public StateTransition parse(Element element, ParserContext context) {
        String executorId = element.getAttribute(ATTR_ID);
        String type = element.getAttribute(ATTR_TYPE);
        String next = element.getAttribute(ATTR_NEXT);

        NodeList serviceElement = element.getChildNodes();

        Set<Service> services = new HashSet<Service>();
        for(int i = 0 ; i < serviceElement.getLength(); i++){
            Service service = new ServiceParser().parse((Element) serviceElement.item(i),context);
            services.add(service);
        }

        if(SEQUENTIAL.equalsIgnoreCase(type)){
            return StateTransition.createStateTransition(new SequentialState(executorId,services),"*",next);
        }

        if(PARALLEL.equalsIgnoreCase(type)){
            return StateTransition.createStateTransition(new ParallelState(executorId,services),"*",next);
        }

        return null;
    }
}
