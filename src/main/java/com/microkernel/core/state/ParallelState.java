package com.microkernel.core.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.ServiceExecutor;
import com.microkernel.core.flow.StateExecutionStatus;

/**
 * Created by NinadIngole on 9/14/2015.
 */
public class ParallelState extends AbstractState {


   
    public ParallelState(String name, List<Service<?>> services) {
        super(name,services);
    }


    public StateExecutionStatus handle(final ServiceExecutor executor,final ServiceContext context) {
    	StateExecutionStatus status = StateExecutionStatus.UNKNOWN;
        Collection<Future<?>> tasks = new ArrayList<Future<?>>();
        List<Service<?>> services = getServices();

        for (final Service service : services) {
            Future<?> task = executor.executeService(service, context);

            tasks.add(task);

            

        }
            Collection<Object> results = new ArrayList<Object>();

            for (Future<?> task1 : tasks) {
                try {
                    results.add((Object)task1.get());
                    status = StateExecutionStatus.COMPLETED;
                } catch (ExecutionException e) {
                	status = StateExecutionStatus.FAILED;
                    // LOG Exception
                	e.printStackTrace();
                } catch (InterruptedException e) {
                	status = StateExecutionStatus.FAILED;
                    //LOG Exception
                	e.printStackTrace();
                }
            }

            return status;

    }

    public boolean isEndState() {
        return false;
    }
}
