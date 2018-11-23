package com.example.http.utils;

import com.example.http.Base64Utils;
import com.example.http.rsa.RSAKey;
import com.example.http.rsa.RSAUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.http.rsa.RSAKey.privateKey;

/**
 * @author Smile
 * @date 2018/5/18
 */

public class RsaInterceptir implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {


        //请求头加密部分
        Request request = chain.request();
        String secretData = null;

        if (request.body() instanceof FormBody) {

            //解析请求头
            JSONObject object = new JSONObject();
            FormBody body = (FormBody) request.body();
            for (int i = 0; i < body.size(); i++) {
                String key = body.encodedName(i);
                String value = body.encodedValue(i);
                try {
                    object.put(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            try {
                //数据加密
                secretData = Base64Utils.encode(RSAUtils.encryptByPrivateKey(object.toString().getBytes(), privateKey));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //重定义请求体
            RequestBody myBody = FormBody.create(request.body().contentType(), "data=" + secretData);
            request = new Request.Builder()
                    .url(request.url())
                    .method(request.method(), myBody)
                    .build();
        }

        //执行请求
        Response response = chain.proceed(request);

        //返回数据体解密部分
        String responseContent = response.body().toString();
        String decryptContent = "";
        try {
            decryptContent = new String(RSAUtils.decryptByPrivateKey(responseContent.getBytes(), RSAKey.privateKey));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //重定义返回体
        ResponseBody myResBody = ResponseBody.create(response.body().contentType(), decryptContent);

        return new Response.Builder()
                .body(myResBody)
                .build();


    }
}
