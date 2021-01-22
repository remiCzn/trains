package train;

/**
 * ReprÃ©sentation d'une section de voie ferrÃ©e. C'est une sous-classe de la
 * classe {@link Element}.
 *
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Section extends Element {
	
	private int isAccessible;
	private Train train;
	
	public Section(String name) {
		super(name);
		isAccessible =0;
		train = null;
	}
	
	public int getNbTrain() {
		return isAccessible;
	}

	@Override
	public synchronized void addTrain(Train t) {
		System.out.println("Le train "+t.getTrainName()+" essaie d'aller en "+this);
		while (!verify(t)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isAccessible++;
		System.out.println("Le train "+t.getTrainName()+" est en "+this);
		
	}

	@Override
	public synchronized void removeTrain() {
		isAccessible--;
		notifyAll();
	}
	
	public boolean verify(Train t) {
		simulate(t);
		boolean res = invariant(t);
		stopSimulation();
		return res;
	}
	
	private void simulate(Train t) {
		isAccessible++;
	}
	
	private boolean invariant(Train t) {
		boolean canGoToTheNextStation = true;
		if (t.getElement() instanceof Station) {
			Direction d = t.getDirection();
			Station nextStation = this.getNextStation(d);
			int sizeNextStation = nextStation.getSize();
			
			int nbTrainTillNextStation = this.countTrainTillNextStation(d); //on compte déjà le train qu'on souhaite faire déplacer car on prend en compte le isAccessible qu'on a incrémenté dans simulate()
			canGoToTheNextStation = (nbTrainTillNextStation <= sizeNextStation);
		}
		return isAccessible <= 1 && canGoToTheNextStation;
		
	}
	
	private void stopSimulation() {
		isAccessible--;
	}
}
