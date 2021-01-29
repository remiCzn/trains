package train;

/**
 * ReprÃ©sentation d'une section de voie ferrÃ©e. C'est une sous-classe de la
 * classe {@link Element}.
 *
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Section extends Element {
	private int moveToLeft;
	private int moveToRight;
	private int isAccessible;
	
	public Section(String name) {
		super(name);
		isAccessible =0;
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
		//Si l'élement precédent était une gare (si le train est en train de quitter la gare)
		if(t.getElement() instanceof Station) {
			this.getNextStation(t.getDirection()).AddReservedPlace(); // ~reservedPlace++
		}
		if(t.getDirection() == Direction.LR)
			moveToRight++;
		else
			moveToLeft++;
		System.out.println("Le train "+t.getTrainName()+" est en "+this);
		
	}

	@Override
	public synchronized void removeTrain() {
		moveToLeft = 0;
		moveToRight = 0;
		
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
		
		if(t.getDirection() == Direction.LR)
			moveToRight++;
		else
			moveToLeft++;
		
		if(t.getElement() instanceof Station) {
			this.getNextStation(t.getDirection()).AddReservedPlace();
		}
		
	}
	
	private boolean invariant(Train t) {
		boolean canGoToTheNextStation = true;
		int directionVerified = 0; // On ne vérifie l'invariant pour la direction que quand on sort de la gare
		if(t.getElement() instanceof Station) {
			Direction d = t.getDirection();
			Station nextStation = this.getNextStation(d);
			canGoToTheNextStation = nextStation.getReservedPlaces() <= nextStation.getSize();
			
			directionVerified = this.CountToRight(t) * this.CountToLeft(t);
		}
		return isAccessible <= 1 && canGoToTheNextStation;
		
	}
	
	private void stopSimulation(Train t) {
		isAccessible--;
		
		if(t.getDirection() == Direction.LR)
			moveToRight--;
		else
			moveToLeft--;
		
		if(t.getElement() instanceof Station) {
			this.getNextStation(t.getDirection()).RemoveReservedPlace();
		}
		
	}

	@Override
	public int CountToRight(Train t) {
		return moveToRight + getNextElement(t.getDirection()).CountToRight(t);
	}

	@Override
	public int CountToLeft(Train t) {
		return moveToLeft + getNextStation(t.getDirection()).CountToLeft(t);
	}
}
