package colley.chisholm.diploma.welcome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyCanvas extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder holder;
	private MyThread myThread;
	private MyParticleThread myParticleThread;
	private MyCanvas myCanvas;

	public MyCanvas(Context context) {
		super(context);
		holder = getHolder();
		holder.addCallback(this);
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub


	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		if (myParticleThread==null){  
			myParticleThread = new MyParticleThread(holder);  
			myParticleThread.setRunning(true);  
			myParticleThread.setSurfaceSize(width, height);  
			myParticleThread.start();  
		}  

//		if (myThread==null){  
//			myThread = new MyThread(holder);  
//			myThread.setRunning(true);  
//			myThread.setSurfaceSize(width, height);  
//			myThread.start();  
//		}  

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;  
		myThread.setRunning(false);  
		while (retry) {  
			try {  
				myThread.join();  
				retry = false;  
			} catch (InterruptedException e) {}  
		}  

	}

//	public Thread getThread() {  
//		return myThread;  
//	}  

}
