package colley.chisholm.diploma.welcome.drawingsurface;

import colley.chisholm.diploma.welcome.threads.MyParticleThread;
import colley.chisholm.diploma.welcome.threads.MyThread;
import colley.chisholm.diploma.welcome.utility.Print;
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
	
	private String className;


	public MyCanvas(Context context) {
		super(context);
		
		className = this.getClass().getSimpleName();
		Print.print(className, "MyCanvas");
		
		holder = getHolder();
		holder.addCallback(this);
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Print.print(className, "surfaceCreated");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Print.print(className, "surfaceChanged");

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
		Print.print(className, "surfaceDestroyed");

		boolean retry = true;  
		myParticleThread.setRunning(false);  
		while (retry) {  
			try {  
				myParticleThread.join();  
				retry = false;  
			} catch (InterruptedException e) {}  
		}  
//		boolean retry = true;  
//		myThread.setRunning(false);  
//		while (retry) {  
//			try {  
//				myThread.join();  
//				retry = false;  
//			} catch (InterruptedException e) {}  
//		}  

	}

//	public Thread getThread() {  
//		return myThread;  
//	}  

}
