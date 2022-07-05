package org.tuean.autoconfiguration.tdengine;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.StatsDClientException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CachedStatsdClient {

    private static StatsDClient client;

    private static final int QUEUE_DEFAULT_SIZE = 2 << 18;

    private BlockingQueue queue = new ArrayBlockingQueue(QUEUE_DEFAULT_SIZE);

    public CachedStatsdClient(String host, Integer port, String applicationName) throws StatsDClientException {
        client = new NonBlockingStatsDClientBuilder()
                .prefix(applicationName)
                .hostname(host)
                .port(port)
                .build();
    }

    public static void gauge(String aspect, Long value , String tags) {
        client.gauge(aspect, value, tags);
    }

    public static void timer(String aspect, Long value, String ...tags) {
        client.time(aspect, value, tags);
    }


}
