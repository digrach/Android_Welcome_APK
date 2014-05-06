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
	private int particleSize = 10;
	Canvas canvas = null;  
	private boolean running = false;  
	private final int refresh_rate=16;        

	int maxParticleSize;

	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);  

	// Constructor accepts the surface to draw to.
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
					draw(canvas);           
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
				addParticle();
			}
			countDownTillNextParticle --;
		}
	}

	private void draw(Canvas canvas)  {  
		maxParticleSize = particleSize * 6;

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

		checkList = new List[mCanvasHeight];

		for (int x = 0; x < particles.size(); x++) {
			particles.get(x).update();
			if (checkList[(int)particles.get(x).getPosy()] == null) {
				checkList[(int)particles.get(x).getPosy()] = new ArrayList<Particle>();
			}
			checkList[(int)particles.get(x).getPosy()].add(particles.get(x));
		}

		for (int x = 0; x < particles.size(); x++) {
			//particles.get(x).update();
			if (checkForColision1(particles.get(x))) {
				particles.get(x).setSize(maxParticleSize);
			}
		}
		
	}

	
	public boolean checkForColision2(Particle p) {
		boolean collision = false;

		// Field to scan is the Particle's current y +/- max size.
		int startRowIndex = (int) (p.getPosy() - maxParticleSize);
		if (startRowIndex < 0) {
			startRowIndex = 0;
		}
		int endRowIndex = (int) (p.getPosy() + maxParticleSize);
		if (endRowIndex > checkList.length - 1) {
			endRowIndex = checkList.length - 1;
		}
		for (int x = startRowIndex; x <= endRowIndex; x ++) {
			List<Particle> l = checkList[x]; 
			if (l != null) { 
				for (int i = 0; i < l.size(); i++ ) { 
					Particle cp = l.get(i);

					if (p != cp) {

						Particle smaller = p;
						Particle larger = cp;

						if (p.getSize() > cp.getSize()) {
							larger = p;
							smaller = cp;
						} 

						if (smaller.getPosx() + smaller.getSize() >= larger.getPosx()
								&& smaller.getPosx() <= larger.getPosx() + larger.getSize()
								&& smaller.getPosy() + smaller.getSize() >= larger.getPosy()
								&& smaller.getPosy() <= larger.getPosy() + larger.getSize()) {
						
						
							l.get(i).setSize(maxParticleSize);
							collision = true;


						} 
					}
				}
			}
		}
		return collision;
	}

	private void addParticle() {
		Random r = new Random();
		int randomx = r.nextInt((int) (mCanvasWidth +1));
		int randomy = r.nextInt((int) (mCanvasHeight +1));
		int randomTargetx = r.nextInt((int) (mCanvasWidth +1));
		int randomTargety = r.nextInt((int) (mCanvasHeight +1));
		Particle p = new Particle(randomx, randomy, randomTargetx, randomTargety, particleSize, makeColor(), mCanvasHeight);
		particles.add(p);
		System.out.println("*** Num of particles: " + particles.size());
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
	
//	private void updateParticles() {
	//		Iterator<Particle> i = particles.iterator();
	//		while (i.hasNext()) {
	//			Particle p = i.next();
	//			if (p.isOnWayDown() && p.getPosy() >= p.getTargety() - 1) {
	//				i.remove();
	//				System.out.println("Removed " + particles.size());
	//			} else {
	//				p.update();
	//				// Check if the current particle's y does not yet exist as an index of the array
	//				// and create a list of Particle at that index, if need be.
	//				if (checkList[(int)p.getPosy()] == null) {
	//					checkList[(int)p.getPosy()] = new ArrayList<Particle>();
	//				}
	//				// Add the current particle to the array of lists at the index of y.
	//				checkList[(int)p.getPosy()].add(p);
	//				checkForColision(p);
	//			}
	//		}
	//	}
	
	public boolean checkForColision1(Particle p) {
		boolean collision = false;

		// Field to scan is the Particle's current y +/- max size.
		int startRowIndex = (int) (p.getPosy() - maxParticleSize);
		if (startRowIndex < 0) {
			startRowIndex = 0;
		}
		int endRowIndex = (int) (p.getPosy() + maxParticleSize);
		if (endRowIndex > checkList.length - 1) {
			endRowIndex = checkList.length - 1;
		}
		for (int x = startRowIndex; x <= endRowIndex; x ++) {
			List<Particle> l = checkList[x]; 
			if (l != null) { 
				for (int i = 0; i < l.size(); i++ ) { 
					Particle cp = l.get(i);

					if (p != cp) {

						int xd = (int) Math.abs(p.getPosx() - cp.getPosx() + cp.getSize());	
						int yd = (int) Math.abs(p.getPosy() - cp.getPosy() + cp.getSize());					

						int w = (int) (p.getSize() + cp.getSize());
						int h = (int) (p.getSize() + cp.getSize());

						if (xd<= w && yd <= h) {
							cp.setSize(maxParticleSize);
							collision = true;						
						}
					}
				}
			}
		}
		return collision;
	}



}
