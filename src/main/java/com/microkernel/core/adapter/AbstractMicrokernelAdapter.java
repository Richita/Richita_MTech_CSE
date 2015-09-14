package com.microkernel.core.adapter;

import com.microkernel.core.Microkernel;
import com.microkernel.core.MicrokernelAdapter;
import com.microkernel.core.support.MicrokernelImpl;

/**
 * Created by NinadIngole on 9/13/2015.
 */
public abstract class AbstractMicrokernelAdapter<P,Q> implements MicrokernelAdapter<P,Q> {

    public Q send(final P request) {
        Q response = null;
        Microkernel microkernel = MicrokernelImpl.getMicrokernelInstance();
        Object res = microkernel.process(request);
        try{
            if(res != null){
                response = (Q) res;
            }
        }catch (ClassCastException e){

        }
        return response;

    }

}
