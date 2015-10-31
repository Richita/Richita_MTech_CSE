package com.microkernel.core.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.microkernel.core.Service;
import com.microkernel.core.flow.FlowExecution;
import com.microkernel.core.flow.FlowExecutionStatus;
import com.microkernel.core.flow.FlowExecutor;
import com.microkernel.core.task.TaskExecutor;

/**
 * Created by NinadIngole on 9/14/2015.
 */
public class ParallelState extends AbstractState {


    private TaskExecutor taskExecutor;

    public ParallelState(String name, Set<Service<?>> services) {
        super(name,services);
    }


    public FlowExecutionStatus handle(final FlowExecutor executor) {

        Collection<Future<FlowExecution>> tasks = new ArrayList<Future<FlowExecution>>();
        Set<Service<?>> services = getServices();

        for (final Service<?> service : services) {
            FutureTask<FlowExecution> task = new FutureTask<FlowExecution>(new Callable<FlowExecution>() {
                public FlowExecution call() throws Exception {
                    String exitStatus = executor.executeService(service);
                    return null;
                }
            });

            tasks.add(task);

            try {
                taskExecutor.execute(task);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Collection<FlowExecution> results = new ArrayList<FlowExecution>();

            for (Future<FlowExecution> task1 : tasks) {
                try {
                    results.add(task1.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
        return null;


    }

    public boolean isEndState() {
        return false;
    }
}
