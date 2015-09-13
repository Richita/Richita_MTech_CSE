package com.microkernel.core;

/**
 * Created by NinadIngole on 9/13/2015.
 */
public interface MicrokernelAdapter<P, Q> {
    Q send(P response);

    Class<Q> getResponseType();
}
