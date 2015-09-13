package com.microkernel.core.support;

import com.microkernel.core.Microkernel;

/**
 * Created by NinadIngole on 9/13/2015.
 */
public class MicrokernelImpl implements Microkernel {

    private static Microkernel INSTANCE;

    @Override
    public void start() {

    }

    @Override
    public void halt() {

    }

    @Override
    public Object process(Object request) {
        return null;
    }

    public static Microkernel getMicrokernelInstance() {
        if(INSTANCE == null){
            synchronized (MicrokernelImpl.class){
                if(INSTANCE == null){
                    INSTANCE = new MicrokernelImpl();
                }
            }
        }
        return INSTANCE;
    }
}
