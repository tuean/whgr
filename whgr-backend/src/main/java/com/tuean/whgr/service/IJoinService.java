package com.tuean.whgr.service;

import com.tuean.whgr.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
@Slf4j
public class IJoinService {

    private static final String UTF_8 = "utf-8";

    public void completableSearch() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(10);
        List<String> topSites = Arrays.asList(
                "www.baidu.com", "cn.bing.com"
        );

        List<CompletableFuture<Double>> relevanceFutures = topSites.stream().
                map(site -> CompletableFuture.supplyAsync(() -> downloadSite(site), executor)).
                map(contentFuture -> contentFuture.thenApply(this::parse)).
                map(docFuture -> docFuture.thenCompose(this::calculateRelevance)).
                collect(Collectors.<CompletableFuture<Double>>toList());

        CompletableFuture<List<Double>> allDone = Util.allOf(relevanceFutures);
        CompletableFuture<OptionalDouble> maxRelevance = allDone.thenApply(relevances ->
                relevances.stream().
                        mapToDouble(Double::valueOf).
                        max()
        );
        log.info(String.valueOf(maxRelevance.get()));
    }

    private String downloadSite(final String site) {
        try {
            log.debug("Downloading {}", site);
            final String res = IOUtils.toString(new URL("http://" + site), UTF_8);
            log.debug("Done {}", site);
            return res;
        } catch (IOException e) {
            log.error("", e);
            return "error";
        }
    }

    private String parse(String xml) {
        log.info(xml);
        return xml;
    }

    private CompletableFuture<Double> calculateRelevance(String doc) {
        return CompletableFuture.completedFuture(Double.valueOf("1"));
    }

}
