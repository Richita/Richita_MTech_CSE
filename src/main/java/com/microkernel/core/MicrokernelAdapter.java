package com.microkernel.core;

/**
 * Adapter are used to provide a Sync version of orchestrator processing where the caller waits in blocking mode
 * Created by NinadIngole on 9/13/2015.
 */
public interface MicrokernelAdapter<P, Q> {
    Q send(P response);

    Class<Q> getResponseType();
}
