package train;

/**
 * Repr√©sentation d'une section de voie ferr√©e. C'est une sous-classe de la
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
		//Si l'Èlement precÈdent Ètait une gare (si le train est en train de quitter la gare)
		if(t.getElement() instanceof Station) {
			this.getNextStation(t.getDirection()).AddReservedPlace(); // ~reservedPlace++
		}
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
		stopSimulation(t);
		return res;
	}
	
	private void simulate(Train t) {
		isAccessible++;
		this.getNextStation(t.getDirection()).AddReservedPlace();
	}
	
	private boolean invariant(Train t) {
		boolean canGoToTheNextStation = true;
		if(t.getElement() instanceof Station) {
			Direction d = t.getDirection();
			Station nextStation = this.getNextStation(d);
			canGoToTheNextStation = nextStation.getReservedPlaces() <= nextStation.getSize();
		}
		return isAccessible <= 1 && canGoToTheNextStation;
		
	}
	
	private void stopSimulation(Train t) {
		isAccessible--;
		this.getNextStation(t.getDirection()).RemoveReservedPlace();;
	}
}
