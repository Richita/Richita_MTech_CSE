package com.microkernel.core.xml.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.microkernel.core.Service;
import com.microkernel.core.flow.State;
import com.microkernel.core.flow.support.StateTransition;
import com.microkernel.core.state.ParallelState;
import com.microkernel.core.state.SequentialState;
import com.microkernel.core.xml.Parser;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class StateParser implements Parser<StateTransition>{
    
	private Logger log = LoggerFactory.getLogger(StateParser.class);
	
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

    private final String ATTR_TIMEOUT = "timeout";

    public StateTransition parse(Element element) {
        State state = null;
    	String executorId = element.getAttribute(ATTR_ID);
        String type = element.getAttribute(ATTR_TYPE);
        String next = element.getAttribute(ATTR_NEXT);
        String timeoutString = element.getAttribute(ATTR_TIMEOUT);
        long timeout = 0; // TODO can do default here too.
        NodeList list = element.getElementsByTagName("next");
        
        log.info("Parsing State : "+ executorId);

        // check if attributes are not set in process-def.xml then take the default one
        if("".equalsIgnoreCase(next)) next = null;
        if("".equalsIgnoreCase(type)) type = SEQUENTIAL;
        if(!"".equalsIgnoreCase(timeoutString)) timeout = Long.valueOf(timeoutString);
        
        NodeList serviceElement = element.getElementsByTagName("service");

        List<Service<?>> services = new ArrayList<Service<?>>();
        for(int i = 0 ; i < serviceElement.getLength(); i++){
            Service<?> service = parser.parse((Element) serviceElement.item(i));
            services.add(service);
        }
        
        if(SEQUENTIAL.equalsIgnoreCase(type)){
        	log.info("Sequential State :"+executorId);
        	state = new SequentialState(executorId, services, timeout);
        }

        if(PARALLEL.equalsIgnoreCase(type)){
        	log.info("Parallel State :"+executorId);
        	state = new ParallelState(executorId, services, timeout);
        }

        return StateTransition.createStateTransition(state,"*",next);
    }
}
