package cn.keeasy.mobilekeeasy.custom;

import java.util.Hashtable;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.data.Sources;

public class QRCodeDialog extends Dialog {

	private ImageView ecode;
	private Bitmap qrCodeBitmap = null;

	public QRCodeDialog(Context context) {
		super(context, R.style.dialog);
		setContentView(R.layout.drawqrcode);
		ecode = (ImageView) this.findViewById(R.id.ecode_img);
	}

	public void initShow() {
		if (!"".equals(Sources.QRCODE)) {
			// 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（500*500）
			try {
				qrCodeBitmap = EncodingHandler
						.createQRCode(Sources.QRCODE, 500);
			} catch (WriterException e) {
				e.printStackTrace();
			}
			ecode.setImageBitmap(qrCodeBitmap);
		}
		show();
	}

	public final static class EncodingHandler {
		public static Bitmap createQRCode(String str, int widthAndHeight)
				throws WriterException {
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix matrix = new MultiFormatWriter().encode(str,
					BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
			int width = matrix.getWidth();
			int height = matrix.getHeight();
			int[] pixels = new int[width * height];

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (matrix.get(x, y)) {
						pixels[y * width + x] = 0xff000000;
					}
				}
			}
			Bitmap bitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			return bitmap;
		}
	}
}