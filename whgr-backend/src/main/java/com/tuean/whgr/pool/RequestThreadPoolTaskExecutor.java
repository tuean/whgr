package com.tuean.whgr.pool;


import cn.hutool.core.thread.NamedThreadFactory;

import java.util.concurrent.*;

public class RequestThreadPoolTaskExecutor {

    private static ThreadPoolExecutorMdcWrapper executor = null;

    public static ThreadPoolExecutorMdcWrapper getExecutor() {

        if (executor != null) return executor;

        ThreadFactory threadFactory = new NamedThreadFactory("request", false);
        executor = new ThreadPoolExecutorMdcWrapper(
                12,
                12 * 2,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1024),
                threadFactory,
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );;
        return executor;
    }

}
