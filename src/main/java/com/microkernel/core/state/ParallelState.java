package com.microkernel.core.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microkernel.core.Service;
import com.microkernel.core.flow.ServiceExecutor;

/**
 * Created by NinadIngole on 9/14/2015.
 */
public class ParallelState extends AbstractState {


   
    public ParallelState(String name, List<Service<?>> services) {
        super(name,services);
    }


    public void handle(final Object request,final ServiceExecutor executor) {

        Collection<Future<?>> tasks = new ArrayList<Future<?>>();
        List<Service<?>> services = getServices();

        for (final Service service : services) {
            Future<?> task = executor.executeService(service, request);

            tasks.add(task);

            

        }
            Collection<Object> results = new ArrayList();

            for (Future<?> task1 : tasks) {
                try {
                    results.add((Object)task1.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }




    }

    public boolean isEndState() {
        return false;
    }
}
