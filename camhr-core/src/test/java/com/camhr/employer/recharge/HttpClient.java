package com.camhr.employer.recharge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClient {

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
			builder.setConnectionRequestTimeout(HttpClient.httpRequestTimeout);
			RequestConfig requestConfig = builder.setSocketTimeout(3000).build();
			
			HttpPost httpPost = new HttpPost(url);
			
			httpPost.setEntity(entity);
			httpPost.setConfig(requestConfig);
			httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (Exception e) {
				}
			}
		}
		return result;
		
	}

	
}
