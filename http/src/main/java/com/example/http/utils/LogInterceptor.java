package com.example.http.utils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * @author Smile
 */
public class LogInterceptor implements Interceptor {

    private boolean debug = false;

    public LogInterceptor(boolean debug) {
        this.debug = debug;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        String logMsg = "";

        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        logMsg = logMsg + request.toString() + "\n";
        String method = request.method();
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                logMsg = logMsg + "RequestParams:{" + sb.toString() + "}\n";
            }
        }
        logMsg = logMsg + "Response:" + content + "\n" + duration + "毫秒----------";

        if (debug){
            LogUtils.e(logMsg);
        }

        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
