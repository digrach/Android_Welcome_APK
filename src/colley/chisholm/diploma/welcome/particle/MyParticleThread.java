package colley.chisholm.diploma.welcome.particle;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;

public class MyParticleThread extends Thread {

	private int mCanvasWidth;  
	private int mCanvasHeight;    
	private ParticleManager pm;
	private SurfaceHolder holder;

	private int countDownTillNextParticle = 5;
	private Canvas canvas = null;  
	private boolean running = false;  
	private final int refresh_rate=16;        
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);  
	
	// Constructor accepts the surface to draw to.
	public MyParticleThread(SurfaceHolder holder) {  
		this.holder = holder;  
	}  

	public void setSurfaceSize(int width, int height) {  
		synchronized (holder){  
			mCanvasWidth = width;  
			mCanvasHeight = height;  
			pm = new ParticleManager(mCanvasWidth,mCanvasHeight);
		}  
	}  

	@Override  
	public void run() { 
		long previousTime, currentTime;  
		previousTime = System.currentTimeMillis();

		while(running == true) {
			
			currentTime = System.currentTimeMillis();  
			while ((currentTime-previousTime) < refresh_rate){  
				currentTime = System.currentTimeMillis();  
			}  
			previousTime=currentTime;
			
			try {  
				canvas = holder.lockCanvas();  
				synchronized (holder) {  
					draw(canvas,pm.updateAllParticles());           
				}  
			}  
			finally {  
				if (canvas != null) {  
					holder.unlockCanvasAndPost(canvas);  
				}  
			}  
			try {   
				Thread.sleep(refresh_rate-5);   
			} catch (InterruptedException e) {  
				e.printStackTrace();  
			} 
		
			if (countDownTillNextParticle == 0) {
				countDownTillNextParticle = 5 + (int)(Math.random()*15);
				pm.addParticle();
			}
			countDownTillNextParticle --;
		}
	}
	
	private void draw(Canvas canvas, List<Particle> particles)  {  
		canvas.drawColor(Color.BLACK);  
		paint.setStyle(Style.FILL_AND_STROKE);
		for (int i=0;i<particles.size();i++){   
			Particle p = particles.get(i);
			paint.setColor(Color.HSVToColor(128,p.getColor()));
			canvas.drawCircle((float)p.getPosX(),  
					(float)p.getPosY(),   
					(float)p.getSize(),  
					paint);  
		}  
	}

	public void setRunning(boolean b) {
		running = b;
	}


}
