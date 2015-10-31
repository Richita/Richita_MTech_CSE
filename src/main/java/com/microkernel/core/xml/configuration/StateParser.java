package com.microkernel.core.xml.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
    
	Parser<Service<?>> parser;
	
	
	
	public Parser<Service<?>> getParser() {
		return parser;
	}


	public void setParser(Parser<Service<?>> parser) {
		this.parser = parser;
	}


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

        List<Service<?>> services = new ArrayList<Service<?>>();
        for(int i = 0 ; i < serviceElement.getLength(); i++){
            Service<?> service = parser.parse((Element) serviceElement.item(i));
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
