package org.itheima.reggie.common;

import org.apache.logging.log4j.ThreadContext;

public class BaseContext {
    private static ThreadLocal<Long> threadContext=new ThreadLocal();
    public static void setThreadContext(Long id){
        threadContext.set(id);
    }
    public static Long getThreadContext(){
        return threadContext.get();
    }
}
