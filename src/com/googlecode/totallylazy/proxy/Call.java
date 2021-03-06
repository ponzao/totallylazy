package com.googlecode.totallylazy.proxy;

import com.googlecode.totallylazy.Unchecked;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.googlecode.totallylazy.proxy.Proxy.proxy;


public class Call<T, R> implements InvocationHandler {
    public static ThreadLocalInvocation invocation = new ThreadLocalInvocation();

    private Call() {
    }

    public static <T> T on(Class<T> aCLass) {
        return proxy(aCLass, new Call());
    }

    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        invocation.set(new MethodInvocation<T, R>(method, arguments));
        return null;
    }

    public static <T, S> Invocation<T, S> method(S value) {
        return Unchecked.cast(invocation.get());
    }
}
