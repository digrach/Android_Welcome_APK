package colley.chisholm.diploma.welcome;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;

public class MyParticleThread extends Thread {

	private int mCanvasWidth;  
	private int mCanvasHeight;  
	private SurfaceHolder holder;  

	private final List<Particle> particles = new ArrayList<Particle>();
	private int countDownTillNextParticle = 5;
	private  Paint[] colors = null;
	private List<Particle>[] checkList;
	private int particleSize = 5;
	Canvas canvas = null;  
	private boolean running = false;  
	private final int refresh_rate=16;      // How often we update the screen, in ms  



	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);  



	public MyParticleThread(SurfaceHolder holder) {  
		this.holder = holder;  
	}  

	public void setSurfaceSize(int width, int height) {  
		synchronized (holder){  
			mCanvasWidth = width;  
			mCanvasHeight = height;  
			
		}  
	}  

	@Override  
	public void run() { 
//		checkList = new List[mCanvasHeight];
		long previousTime, currentTime;  
		previousTime = System.currentTimeMillis();

		while(running == true) {
			// Look if time has past  
			currentTime=System.currentTimeMillis();  
			while ((currentTime-previousTime)<refresh_rate){  
				currentTime=System.currentTimeMillis();  
			}  
			previousTime=currentTime;  
			
			checkList = new List[mCanvasHeight];

			// PAINT  
			try {  
				canvas = holder.lockCanvas();  
				synchronized (holder) {  
					draw(canvas);           
				}  
			}  
			finally {  
				if (canvas != null) {  
					holder.unlockCanvasAndPost(canvas);  
				}  
			}  
			// WAIT  
//			try {  
//				Thread.sleep(refresh_rate-5); // Wait some time till I need to display again  
//			} catch (InterruptedException e) {  
//				e.printStackTrace();  
//			}       

			if (countDownTillNextParticle == 0) {
				countDownTillNextParticle = 5 + (int)(Math.random()*15);
				AddParticle();
			}
			countDownTillNextParticle --;
		}
	}

	private void draw(Canvas canvas)  {  
		canvas.drawColor(Color.BLACK);  
		paint.setStyle(Style.FILL_AND_STROKE);
		for (int i=0;i<particles.size();i++){   
			Particle p = particles.get(i);
			paint.setColor(p.getColor());  
			canvas.drawCircle((float)p.getPosx(),  
					(float)p.getPosy(),   
					(float)p.getSize(),  
					paint);  
		}  
		
		updateParticles();
	}
	
	private void updateParticles() {
		Iterator<Particle> i = particles.iterator();
		while (i.hasNext()) {
			Particle p = i.next();
			if (p.isOnWayDown() && p.getPosy() >= p.getTargety() - 1) {
				i.remove();
				System.out.println("Removed " + particles.size());
			} else {
				p.update();
				// Check if the current particle's y does not yet exist as an index of the array
				// and create a list of Particle at that index, if need be.
				if (checkList[(int)p.getPosy()] == null) {
					checkList[(int)p.getPosy()] = new ArrayList<Particle>();
				}
				// Add the current particle to the array of lists at the index of y.
				checkList[(int)p.getPosy()].add(p);
				checkForColision(p);
			}
		}
	}
	
	public boolean checkForColision(Particle p) {
		// Field to scan is the Particle's current y
		// minus particleSize and plus particleSize.
		int startRowIndex = (int) (p.getPosy() - particleSize);
		if (startRowIndex < 0) {
			startRowIndex = 0;
		}
		int endRowIndex = (int) (p.getPosy() + particleSize);
		if (endRowIndex > checkList.length - 2) {
			endRowIndex = checkList.length - 2;
		}

		for (int x = startRowIndex; x <= endRowIndex; x ++) {
			// Get current list and check if it's not null.
			List<Particle> l = checkList[x];
			if (l != null) {
				// Loop through each Particle in the list.
				for (int i = 0; i < l.size(); i++ ) {
					// Get current Particle in the list.
					Particle cp = l.get(i);
					
					// If the current Particle in the list is the one 
					// we are checking against (self), return.
					if (cp == p) {
						return false;
					}
					// If the current Particle is within the field we are scanning,
					// we have found a collision, return true.
					if ((cp.getPosx() + particleSize) >= (p.getPosx()) &&
							(cp.getPosx()) <= (p.getPosx() + particleSize)) {
						p.setSize(50);
						cp.setSize(50);
						return true;

					}
				}

			}

		}
		// Nothing found, return false.
		return false;
	}

	private void AddParticle() {
		Random r = new Random();
		int randomx = r.nextInt((int) (mCanvasWidth +1));
		int randomy = r.nextInt((int) (mCanvasHeight +1));
		int randomTargetx = r.nextInt((int) (mCanvasWidth +1));
		int randomTargety = r.nextInt((int) (mCanvasHeight +1));
		Particle p = new Particle(randomx, randomy, randomTargetx, randomTargety, particleSize, makeColor(), mCanvasHeight);
		particles.add(p);
		System.out.println("*** Num of particles: " + particles.size());

		//		if (particles.size() == 1) {
		//			particles.get(0).name = "track";
		//		}
	}

	private int makeColor() {
		float[] color=new float[3]; // HSV (0..360,0..1,0..1) 
		color[0]=(float)(Math.random()*360);  
		color[1]=1;  
		color[2]=1; 
		return Color.HSVToColor(128,color);
	}

	public void setRunning(boolean b) {
		running = b;
	}



}
