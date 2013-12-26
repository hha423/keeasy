package cn.evan.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import cn.evan.tools.base.ITask;

public class HttpClients {

	ITask it;

	public HttpClients(ITask it) {
		this.it = it;
	}

	/**
	 * 获取服务器内容
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public void getContent(String url) {
		try {
			StringBuilder sb = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpParams httpParams = client.getParams();
			// 设置网络超时参数
			HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
			HttpConnectionParams.setSoTimeout(httpParams, 5000);
			HttpResponse response = client.execute(new HttpGet(url));
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"),
						8192);
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				reader.close();
			}
			it.mTaskSuccess(sb.toString());
		} catch (Exception e) {
			it.mTaskError("服务器连接失败，请重试！");
			e.printStackTrace();
		}
	}

	/**
	 * 1. 使用POST方式时，传递参数必须使用NameValuePair数组 2. 使用GET方式时，通过URL传递参数，注意写法 3.
	 * 通过setEntity方法来发送HTTP请求 4. 通过DefaultHttpClient 的 execute方法来获取HttpResponse
	 * 5. 通过getEntity()从Response中获取内容 statusCode 200成功 200服务器出错
	 * 404服务器没响应（服务器有问题）
	 * 
	 * @param urlString
	 * @param nameValuePairs
	 */
	public void httpPostData(String urlString,
			List<NameValuePair> nameValuePairs) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(urlString);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			it.mTaskSuccess("" + statusCode);
		} catch (Exception e) {
			it.mTaskError("服务器连接失败，请重试！");
			e.printStackTrace();
		}
	}

}