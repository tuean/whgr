package com.tuean.whgr.http;

import com.tuean.whgr.config.Const;
import com.tuean.whgr.helper.ReqHelper;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateMdcRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String req = ReqHelper.get();
        request.getHeaders().add(Const.REQ_ID, req);
        return execution.execute(request, body);
    }
}
