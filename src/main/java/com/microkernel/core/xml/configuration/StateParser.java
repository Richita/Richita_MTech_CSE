package com.microkernel.core.xml.configuration;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.microkernel.core.Service;
import com.microkernel.core.flow.support.StateTransition;
import com.microkernel.core.state.ParallelState;
import com.microkernel.core.state.SequentialState;
import com.microkernel.core.xml.Parser;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class StateParser implements Parser<StateTransition>{
    private final String ELE_EXECUTOR = "executor";

    private final String ATTR_ID = "id";

    private final String ATTR_TYPE = "type";

    private final String ATTR_NEXT = "next";

    private final String ELE_SERVICE = "service";

    private final String PARALLEL = "parallel";

    private final String SEQUENTIAL = "sequential";


    public StateTransition parse(Element element) {
        String executorId = element.getAttribute(ATTR_ID);
        String type = element.getAttribute(ATTR_TYPE);
        String next = element.getAttribute(ATTR_NEXT);

        if("".equalsIgnoreCase(next)) next = null;

        NodeList serviceElement = element.getElementsByTagName("service");

        Set<Service<?>> services = new HashSet<Service<?>>();
        for(int i = 0 ; i < serviceElement.getLength(); i++){
            Service service = new ServiceParser().parse((Element) serviceElement.item(i));
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
