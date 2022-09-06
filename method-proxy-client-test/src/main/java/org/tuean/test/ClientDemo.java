package org.tuean.test;

import com.tuean.mp.annotations.MethodProxyCall;

@MethodProxyCall(methodName = "demoService", target = "${mp.target}")
public interface ClientDemo {

    Integer add(Integer a, Integer b);


}
