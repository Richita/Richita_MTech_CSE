package com.microkernel.core.state;

import com.microkernel.core.flow.FlowExecutor;
import com.microkernel.core.service.Service;
import org.springframework.batch.core.job.flow.FlowExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskRejectedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by NinadIngole on 9/14/2015.
 */
public class ParallelState extends AbstractState {

    private final Set<Service> services;

    private AsyncTaskExecutor taskExecutor;

    public ParallelState(String name, Set<Service> services) {
        super(name);
        this.services = services;
    }


    @Override
    public FlowExecutionStatus handle(final FlowExecutor executor) {

        Collection<Future<FlowExecution>> tasks = new ArrayList<Future<FlowExecution>>();

        for (final Service service : services) {
            FutureTask<FlowExecution> task = new FutureTask<FlowExecution>(new Callable<FlowExecution>() {
                @Override
                public FlowExecution call() throws Exception {
                    String exitStatus = executor.executeService(service);
                    return new FlowExecution(exitStatus, null);
                }
            });

            tasks.add(task);

            try {
                taskExecutor.execute(task);
            } catch (TaskRejectedException e) {
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
        return FlowExecutionStatus.COMPLETED;


    }

    @Override
    public boolean isEndState() {
        return false;
    }
}
