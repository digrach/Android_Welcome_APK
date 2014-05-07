package colley.chisholm.diploma.welcome.particle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ParticleManager {
	
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Particle>[] checkList;
	
	private int spawnFieldWidth;
	private int spawnFieldHeight;
	
	public ParticleManager(int spawnFieldWidth, int spawnFieldHeight) {
		this.spawnFieldWidth = spawnFieldWidth;
		this.spawnFieldHeight = spawnFieldHeight;
	}

	public void addParticle() {
		Random r = new Random();
		int randomx = r.nextInt((int) (spawnFieldWidth +1));
		int randomy = r.nextInt((int) (spawnFieldHeight +1));
		int randomTargetx = r.nextInt((int) (spawnFieldWidth +1));
		int randomTargety = r.nextInt((int) (spawnFieldHeight +1));
		Particle p = new Particle(randomx, randomy, randomTargetx, randomTargety, spawnFieldHeight);
		particles.add(p);
	}
	
	public List<Particle> updateAllParticles() {
		updateParticles();
		checkForCollisions();
		return particles;
	}

	private void updateParticles() {
		checkList = new List[spawnFieldHeight];
		Iterator<Particle> i = particles.iterator();
		while (i.hasNext()) {
			Particle p = i.next();
			if (p.isOnWayDown() && p.getPosY() >= p.getTargetY() - 1) {
				i.remove();
				System.out.println("Removed " + particles.size());
			} else {
				p.update();
				// Check if the current particle's y does not yet exist as an index of the array
				// and create a list of Particle at that index, if need be.
				if (checkList[(int)p.getPosY()] == null) {
					checkList[(int)p.getPosY()] = new ArrayList<Particle>();
				}
				// Add the current particle to the array of lists at the index of y.
				checkList[(int)p.getPosY()].add(p);
			}
		}
	}

	private void checkForCollisions() {
		for (int x = 0; x < particles.size(); x++) {
			if (checkForColision(particles.get(x))) {
				particles.get(x).setSize(particles.get(x).getMaxCollisionSize());
			}
		}
	}
	
	private boolean checkForColision(Particle p) {
		boolean collision = false;

		// Field to scan is the Particle's current y +/- max size.
		int startRowIndex = (int) (p.getPosY() - p.getMaxCollisionSize());
		if (startRowIndex < 0) {
			startRowIndex = 0;
		}
		int endRowIndex = (int) (p.getPosY() + p.getMaxCollisionSize());
		if (endRowIndex > checkList.length - 1) {
			endRowIndex = checkList.length - 1;
		}
		for (int x = startRowIndex; x <= endRowIndex; x ++) {
			List<Particle> l = checkList[x]; 
			if (l != null) { 
				for (int i = 0; i < l.size(); i++ ) { 
					Particle cp = l.get(i);

					if (p != cp) {

						int xd = (int) Math.abs(p.getPosX() - cp.getPosX() + cp.getSize());	
						int yd = (int) Math.abs(p.getPosY() - cp.getPosY() + cp.getSize());					

						int w = (int) (p.getSize() + cp.getSize());
						int h = (int) (p.getSize() + cp.getSize());

						if (xd<= w && yd <= h) {
							cp.setSize(cp.getMaxCollisionSize());
							collision = true;						
						}
					}
				}
			}
		}
		return collision;
	}

}
