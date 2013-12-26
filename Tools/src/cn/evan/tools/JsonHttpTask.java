package cn.evan.tools;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.os.AsyncTask;
import cn.evan.tools.base.ITask;

import com.keeasy.phone.bean.request.ChatBean;

/**
 * 下载json 文件的类 ，关于AsyncTask 可以参考sdk 文档
 */
public class JsonHttpTask extends AsyncTask<String, Integer, String> {

	public static final int TIMEOUT = 20000;
	ITask its;
	// List<NameValuePair> nameValuePairs;

	ChatBean nameValuePairs;

	public JsonHttpTask(ITask it, ChatBean nameValuePairs) {
		this.its = it;
		this.nameValuePairs = nameValuePairs;
	}

	/**
	 * 获取服务器端的游戏列表文件
	 * 
	 * @param String
	 *            url, GameListView gamelistView
	 * @author lanqi
	 */
	@Override
	protected String doInBackground(String... params) {
		try {
			StringBuilder sb = new StringBuilder();
			if (params[0].equals(RequestTask.UPDATE)) {
				// HttpClient httpclient = new DefaultHttpClient();
				// HttpPost httppost = new HttpPost(params[1].toString());
				// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				// HttpResponse response = httpclient.execute(httppost);
				// int statusCode = response.getStatusLine().getStatusCode();
				// if (statusCode != 200) {
				// return "err";
				// } else {
				// return EntityUtils.toString(response.getEntity());
				// }
				URL url = new URL(params[1].toString());
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(TIMEOUT);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				ByteArrayOutputStream byteos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(byteos);
				oos.writeObject(nameValuePairs);
				oos.flush();
				byte[] buf = byteos.toByteArray();
				conn.setRequestProperty("Content-type",
						"application/octet-stream");
				conn.setRequestProperty("Content-length", "" + buf.length);
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				dos.write(buf);
				dos.flush();
				InputStream is = conn.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"), 8192);
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				reader.close();
				oos.close();
				dos.close();
				return sb.toString();
			} else {
				HttpClient client = new DefaultHttpClient();
				// 请求超时
				client.getParams().setParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT);
				// 读取超时
				client.getParams().setParameter(
						CoreConnectionPNames.SO_TIMEOUT, TIMEOUT);
				// params[0] 代表连接的url
				HttpGet get = new HttpGet(params[1].toString());
				HttpResponse response = client.execute(get);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"), 8192);
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					reader.close();
				}
				// 返回结果
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "err";
		}
	}

	@Override
	protected void onPreExecute() {
		// 开始
	}

	@Override
	protected void onPostExecute(String result) {
		if (result != null) {
			if (result.equals("err"))
				its.mTaskError("服务器连接超时，请重试！");
			else
				its.mTaskSuccess(result);
		}
		// 成功
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// System.out.println(values[0]);
	}

}
