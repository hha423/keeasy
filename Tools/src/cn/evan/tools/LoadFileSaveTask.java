package cn.evan.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import android.os.AsyncTask;
import cn.evan.tools.base.IDownFile;

public class LoadFileSaveTask extends AsyncTask<Object, Object, File> {
	private String imageUrl;
	private String imageName;
	private File saveDir;
	private IDownFile id;

	public LoadFileSaveTask(IDownFile id, String imageUrl, String imageName,
			File saveDir) {
		this.id = id;
		this.imageName = imageName;
		this.imageUrl = imageUrl;
		this.saveDir = saveDir;

	}

	@Override
	protected File doInBackground(Object... params) {

		File f = new File(saveDir, imageName);
		if (f.exists()) {
			return f;
		}
		try {
			URL url = new URL(imageUrl + imageName);
			InputStream is = url.openStream();
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int index = 0;
			while ((index = is.read(b)) != -1) {
				fos.write(b, 0, index);
			}
			is.close();
			fos.close();

		} catch (Exception e) {
			id.mTaskError();
			e.printStackTrace();
		}
		return f;
	}

	@Override
	protected void onPostExecute(File result) {
		id.mTaskSuccess(result);
		super.onPostExecute(result);
	}

}