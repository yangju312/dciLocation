package Gis.namespace.Location;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity; /* 延含学习 *///import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;

/* 引用Camera类 */
import android.hardware.Camera;

/* 引用PictureCallback为取得拍照后的事件 */
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

/* 使Activity实现SurfaceHolder.Callback */
public class CameraPreview extends Activity implements SurfaceHolder.Callback {
	/* 建立私有Camera对象 */
	private Camera mCamera;
	private Button backPic;
	private Button getPic;

	/* 当成review照下来的相片之用 */
	private ImageView mImageView01;

	private static String TAG = "HIPPO_DEBUG";
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private int mOrientation;
	// private int intScreenX, intScreenY;

	/* 默认相机预览模式为false */
	private boolean bIfPreview = true;

	/* 将照下来的图片保存在此 */

	/** Called when the activity is first created. */
	@SuppressLint("SdCardPath")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			/* 使应用程序全屏运行，不使用title bar */
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.camera);

			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				mOrientation = 180;
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				mOrientation = 90;
			}

			/* 判断存储卡是否存在 */
			if (!checkSDCard()) {
				/* 提醒User未安装存储卡 */
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
			/* Activity必须实现SurfaceHolder.Callback */
			mSurfaceHolder.addCallback(CameraPreview.this);

			/*
			 * 以SURFACE_TYPE_PUSH_BUFFERS(3) 作为SurfaceHolder显示类型
			 */
			

			backPic = (Button) findViewById(R.id.backPic);
			getPic = (Button) findViewById(R.id.getPic);

			initCamera();

			backPic.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					/* 自定义初始化开启相机函数 */
					finish();

				}
			});

			/* 停止Preview及相机 */
			getPic.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					/* 自定义重置相机，并关闭相机预览函数 */
					takePicture();

				}
			});

			// /* 拍照 */
			// mButton03.setOnClickListener(new Button.OnClickListener() {
			//
			// public void onClick(View arg0) {
			// // TODO Auto-generated method stub
			//
			// /* 当存储卡存在才允许拍照，保存临时图像文件 */
			// if (checkSDCard()) {
			// /* 自定义拍照函数 */
			// takePicture();
			// } else {
			// /* 存储卡不存在的显示提示 */
			// mTextView01.setText(getResources().getText(
			// R.string.str_err_nosd).toString());
			// }
			// }
			// });
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	/* 告订初始相机函数 */
	@SuppressLint("NewApi")
	private void initCamera() {

		/* 若相机非?预览模式，则开启相机 */
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
				/* 建立Camera.Parameters对象 */
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

					/* ?2.0模拟器中，设置不支持的PreviewSize将造成Exception */
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
						/* ?2.0模拟器中，设置不支持的PictureSize将造成Exception */
//						parameters.setPictureSize(1024, 768);
						// parameters.setPictureSize(213, 350);
						/* 将Camera.Parameters设定予Camera */
						// Long time=new Date().getTime();
						// parameters.setGpsTimestamp(time);
						// long timestamp= new Timestamp(new
						// java.util.Date().getTime());
						// parameters.setGpsTimestamp(timestamp);
						mCamera.setParameters(parameters);
						/* setPreviewDisplay唯几的参数为SurfaceHolder */
						mCamera.setPreviewDisplay(mSurfaceHolder);
						/* 立即运行Preview */
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

	/* 拍照撷取影像 */
	private void takePicture() {
		if (mCamera != null) {
			/* 调用takePicture()方法拍照 */
			// Camera.Parameters params = mCamera.getParameters();
			// params.setPictureFormat(PixelFormat.JPEG);
			// // params.setPreviewSize(1024,768);
			// Long time=new Date().getTime();
			// params.setGpsTimestamp(time);
			// mCamera.setParameters(params);
			mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
		}
	}

	/* 相机重置 */
	private void resetCamera() {
		if (mCamera != null) {
			mCamera.stopPreview();

			/* 延含学习，释放Camera对象 */
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

			/* onPictureTaken传入的第几个参数即为相片的byte */
			Bitmap bm = BitmapFactory.decodeByteArray(_data, 0, _data.length);

			/* 创建文件 */

			try {
				Date date = new Date(System.currentTimeMillis());
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"_yyyyMMdd-HHmm");
				String name = "xc" + dateFormat.format(date) + ".jpg";

				File file1 = new File("/mnt/sdcard/珠江巡查相册/");
				if (!file1.exists()) {
					file1.mkdirs();
				}// 创建文件夹
				String fileName = "/mnt/sdcard/珠江巡查相册/" + name;
				File myCaptureFile = new File(fileName);
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(myCaptureFile));
				/* 采用压缩转档方法 */
				bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);

				/* 调用flush()方法，更新BufferStream */
				bos.flush();

				/* 结束OutputStream */
				bos.close();

				/* 将拍照下来且保存完毕的图文件，显示出来 */
				mImageView01.setImageBitmap(bm);

				/* 显示完图片，立即重置相机，并关闭预览 */
				resetCamera();

				/* 再重新启动相机继续预览 */
				initCamera();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				Log.e(TAG, e.toString());
			}
		}
	};

	// /* 自定义文件函数 */
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
		/* 判断存储卡是否存在 */
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
		/* 当Surface不存在，就需要删除图片 */
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
