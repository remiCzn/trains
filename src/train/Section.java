package train;

/**
 * Représentation d'une section de voie ferrée. C'est une sous-classe de la
 * classe {@link Element}.
 *
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Section extends Element {
	
	private int isAccessible;
	
	public Section(String name) {
		super(name);
		isAccessible =0;
	}

	@Override
	public synchronized void addTrain() {
		while (!verify()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isAccessible++;
	}

	@Override
	public synchronized void removeTrain() {
		isAccessible--;
		notifyAll();
	}
	
	public boolean verify() {
		simulate();
		boolean res = invariant();
		stopSimulation();
		return res;
	}
	
	private void simulate() {
		isAccessible++;
	}
	
	private boolean invariant() {
		return isAccessible <= 1;
	}
	
	private void stopSimulation() {
		isAccessible--;
	}
	
}
