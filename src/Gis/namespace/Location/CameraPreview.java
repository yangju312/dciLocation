package Gis.namespace.Location;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity; /* �Ӻ�ѧϰ *///import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;

/* ����Camera�� */
import android.hardware.Camera;

/* ����PictureCallbackΪȡ�����պ���¼� */
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/* ʹActivityʵ��SurfaceHolder.Callback */
public class CameraPreview extends Activity implements SurfaceHolder.Callback {
	/* ����˽��Camera���� */
	private Camera mCamera;
	private Button backPic;
	private Button getPic;

	/* ����review����������Ƭ֮�� */
	private ImageView mImageView01;

	private static String TAG = "HIPPO_DEBUG";
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private int mOrientation;
	// private int intScreenX, intScreenY;

	/* Ĭ�����Ԥ��ģʽΪfalse */
	private boolean bIfPreview = true;

	/* ����������ͼƬ�����ڴ� */

	/** Called when the activity is first created. */
	@SuppressLint("SdCardPath")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			/* ʹӦ�ó���ȫ�����У���ʹ��title bar */
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.camera);

			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				mOrientation = 180;
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				mOrientation = 90;
			}

			/* �жϴ洢���Ƿ���� */
			if (!checkSDCard()) {
				/* ����Userδ��װ�洢�� */
				// mMakeTextToast(getResources().getText(R.string.str_err_nosd).toString(),
				// true);
			}

			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);

			// mTextView01 = (TextView) findViewById(R.id.myTextView1);
			// mImageView01 = (ImageView) findViewById(R.id.myImageView1);

			mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);

			mSurfaceHolder = mSurfaceView.getHolder();
			mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			/* Activity����ʵ��SurfaceHolder.Callback */
			mSurfaceHolder.addCallback(CameraPreview.this);

			/*
			 * ��SURFACE_TYPE_PUSH_BUFFERS(3) ��ΪSurfaceHolder��ʾ����
			 */
			

			backPic = (Button) findViewById(R.id.backPic);
			getPic = (Button) findViewById(R.id.getPic);

			initCamera();

			backPic.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					/* �Զ����ʼ������������� */
					finish();

				}
			});

			/* ֹͣPreview����� */
			getPic.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					/* �Զ���������������ر����Ԥ������ */
					takePicture();

				}
			});

			// /* ���� */
			// mButton03.setOnClickListener(new Button.OnClickListener() {
			//
			// public void onClick(View arg0) {
			// // TODO Auto-generated method stub
			//
			// /* ���洢�����ڲ��������գ�������ʱͼ���ļ� */
			// if (checkSDCard()) {
			// /* �Զ������պ��� */
			// takePicture();
			// } else {
			// /* �洢�������ڵ���ʾ��ʾ */
			// mTextView01.setText(getResources().getText(
			// R.string.str_err_nosd).toString());
			// }
			// }
			// });
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	/* �涩��ʼ������� */
	@SuppressLint("NewApi")
	private void initCamera() {

		/* �������?Ԥ��ģʽ��������� */
		try {

			mCamera = Camera.open();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		if (mCamera != null) {
			try {
				Log.i(TAG, "inside the camera");
				mCamera.setDisplayOrientation(mOrientation);
				mCamera.setPreviewDisplay(mSurfaceHolder);
				/* ����Camera.Parameters���� */
				Camera.Parameters parameters = mCamera.getParameters();

				parameters.setPictureFormat(PixelFormat.JPEG);
				// parameters.setPreviewSize(w, h);
				List<Camera.Size> s = parameters.getSupportedPreviewSizes();

				try {
					if (s != null) {
						for (int i = 0; i < s.size(); i++) {
							Log.i(TAG, "" + (((Camera.Size) s.get(i)).height)
									+ "/" + (((Camera.Size) s.get(i)).width));
						}
					}
					// parameters.setPreviewSize(1024, 768);
					// parameters.setPreviewSize(176, 144);

					/* ?2.0ģ�����У����ò�֧�ֵ�PreviewSize�����Exception */
					s = parameters.getSupportedPictureSizes();
					try {
						if (s != null) {
							for (int i = 0; i < s.size(); i++) {
								Log.i(TAG, ""
										+ (((Camera.Size) s.get(i)).height)
										+ "/"
										+ (((Camera.Size) s.get(i)).width));
							}
						}
						/* ?2.0ģ�����У����ò�֧�ֵ�PictureSize�����Exception */
//						parameters.setPictureSize(1024, 768);
						// parameters.setPictureSize(213, 350);
						/* ��Camera.Parameters�趨��Camera */
						// Long time=new Date().getTime();
						// parameters.setGpsTimestamp(time);
						// long timestamp= new Timestamp(new
						// java.util.Date().getTime());
						// parameters.setGpsTimestamp(timestamp);
						mCamera.setParameters(parameters);
						/* setPreviewDisplayΨ���Ĳ���ΪSurfaceHolder */
						mCamera.setPreviewDisplay(mSurfaceHolder);
						/* ��������Preview */
						mCamera.startPreview();

						bIfPreview = true;
						Log.i(TAG, "startPreview");
					} catch (Exception e) {
						Log.i(TAG, e.toString());
						e.printStackTrace();
					}
				} catch (Exception e) {
					// Toast.makeText(CameraActivity.this, "initCamera error.",
					// Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				mCamera.release();
				mCamera = null;
				Log.i(TAG, e.toString());
				e.printStackTrace();
			}
		}
	}

	/* ����ߢȡӰ�� */
	private void takePicture() {
		if (mCamera != null) {
			/* ����takePicture()�������� */
			// Camera.Parameters params = mCamera.getParameters();
			// params.setPictureFormat(PixelFormat.JPEG);
			// // params.setPreviewSize(1024,768);
			// Long time=new Date().getTime();
			// params.setGpsTimestamp(time);
			// mCamera.setParameters(params);
			mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
		}
	}

	/* ������� */
	private void resetCamera() {
		if (mCamera != null) {
			mCamera.stopPreview();

			/* �Ӻ�ѧϰ���ͷ�Camera���� */
			// mCamera.release();
			// mCamera = null;

			Log.i(TAG, "stopPreview");
			bIfPreview = false;
		}
	}

	private ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			// Shutter has closed
		}
	};

	private PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] _data, Camera _camera) {
			// TODO Handle RAW image data
		}
	};

	private PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] _data, Camera _camera) {
			// TODO Handle JPEG image data

			/* onPictureTaken����ĵڼ���������Ϊ��Ƭ��byte */
			Bitmap bm = BitmapFactory.decodeByteArray(_data, 0, _data.length);

			/* �����ļ� */

			try {
				Date date = new Date(System.currentTimeMillis());
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"_yyyyMMdd-HHmm");
				String name = "xc" + dateFormat.format(date) + ".jpg";

				File file1 = new File("/mnt/sdcard/�齭Ѳ�����/");
				if (!file1.exists()) {
					file1.mkdirs();
				}// �����ļ���
				String fileName = "/mnt/sdcard/�齭Ѳ�����/" + name;
				File myCaptureFile = new File(fileName);
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(myCaptureFile));
				/* ����ѹ��ת������ */
				bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);

				/* ����flush()����������BufferStream */
				bos.flush();

				/* ����OutputStream */
				bos.close();

				/* �����������ұ�����ϵ�ͼ�ļ�����ʾ���� */
				mImageView01.setImageBitmap(bm);

				/* ��ʾ��ͼƬ������������������ر�Ԥ�� */
				resetCamera();

				/* �����������������Ԥ�� */
				initCamera();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				Log.e(TAG, e.toString());
			}
		}
	};

	// /* �Զ����ļ����� */
	// private void delFile(String strFileName) {
	// try {
	// File myFile = new File(strFileName);
	// if (myFile.exists()) {
	// myFile.delete();
	// }
	// } catch (Exception e) {
	// Log.e(TAG, e.toString());
	// e.printStackTrace();
	// }
	// }

	// public void mMakeTextToast(String str, boolean isLong) {
	// if (isLong == true) {
	// Toast.makeText(CameraActivity.this, str, Toast.LENGTH_LONG).show();
	// } else {
	// Toast.makeText(CameraActivity.this, str, Toast.LENGTH_SHORT).show();
	// }
	// }

	private boolean checkSDCard() {
		/* �жϴ洢���Ƿ���� */
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int format, int w,
			int h) {
		// Camera.Parameters params = mCamera.getParameters();
		// params.setPictureFormat(PixelFormat.JPEG);
		// params.setPreviewSize(1024,768);
		// Long time=new Date().getTime();
		// params.setGpsTimestamp(time);
		// mCamera.setParameters(params);
		// mCamera.startPreview();
		// TODO Auto-generated method stub
		Log.i(TAG, "Surface Changed1");
	}

	public void surfaceCreated(SurfaceHolder surfaceholder) {

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		// TODO Auto-generated method stub
		/* ��Surface�����ڣ�����Ҫɾ��ͼƬ */
		try {
			// delFile(strCaptureFilePath);
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
			Log.i(TAG, "Surface Destroyed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		try {
			resetCamera();
			mCamera.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onPause();
	}

}
