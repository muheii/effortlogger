package com.th25.effortlogger.helpers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HTTPHelper {
    public static String sendRequest(String e, String s) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(e);

        StringEntity params = new StringEntity(s);

        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.setEntity(params);

        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        return EntityUtils.toString(entity, "UTF-8");
    }
}
