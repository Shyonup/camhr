package com.camhr.employer.recharge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

public class HttpsClient {

    private static int httpRequestTimeout = 20000;

    public static String executeForm(String url, Map<String, String> parameterMap) {

        CloseableHttpClient httpclient = null;
        String result = null;
        try {

        	List<NameValuePair> params = new ArrayList<>();

			parameterMap.forEach((k, v) -> {
				params.add(new BasicNameValuePair(k, v));
			});
        	
            HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            Builder builder = RequestConfig.custom();
            builder.setConnectTimeout(3000);
            builder.setConnectionRequestTimeout(HttpsClient.httpRequestTimeout);
            RequestConfig requestConfig = builder.setSocketTimeout(3000).build();

            HttpPost httpPost = new HttpPost(url);
            
            httpPost.setEntity(entity);
            httpPost.setConfig(requestConfig);

            httpclient = HttpsClient.wrapClient();
            HttpResponse response = httpclient.execute(httpPost);

            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    private static CloseableHttpClient wrapClient() {
        String HTTP = "http";
        String HTTPS = "https";
        SSLConnectionSocketFactory sslsf = null;
        PoolingHttpClientConnectionManager cm = null;
        SSLContextBuilder builder = null;
        CloseableHttpClient httpClient = null;
        try {
            builder = new SSLContextBuilder();

            sslsf = new SSLConnectionSocketFactory(builder.build(),
                    new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory()).register(HTTPS, sslsf).build();
            cm = new PoolingHttpClientConnectionManager(registry);
            httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .setConnectionManager(cm)
                    .setConnectionManagerShared(true)
                    .build();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return httpClient;
    }

}
