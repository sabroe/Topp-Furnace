package com.yelstream.topp.furnace.pulse.beacon;

public interface Beacon {
    interface InterceptorManager {
        void addInboundInterceptor();  //add,remove
        void addOutboundInterceptor();  //add,remove
    }
    interface CodecManager {}
    interface EndpointManager {}
    interface AddressManager {}






    InterceptorManager getInterceptorManager();
    CodecManager getCodecManager();
    EndpointManager getEndpointManager();

    AddressManager getAddressManager();

    //publisher
    //publish
    //request
    //send
    //sender
}
