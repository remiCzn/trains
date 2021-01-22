package train;

/**
 * Représentation d'une gare. C'est une sous-classe de la classe {@link Element}.
 * Une gare est caractérisée par un nom et un nombre de quais (donc de trains
 * qu'elle est susceptible d'accueillir à un instant donné).
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Station extends Element {
	private final int size;
	private int nbTrain;
	private int reservedPlaces;

	public Station(String name, int size) {
		super(name);
		if(name == null || size <=0)
			throw new NullPointerException();
		this.size = size;
		this.nbTrain = 0;
		this.reservedPlaces = 0;
	}
	
	public int getSize() {
		return size;
	}

	@Override
	public synchronized void addTrain(Train t) {
		System.out.println("Le train "+t.getTrainName()+" essaie d'aller en "+this);
		while (!verify()) {
			try {
				System.out.println(t);
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		nbTrain++;
		System.out.println("Le train "+t.getTrainName()+" est en "+this);
	}
	
	public int getNbTrain() {
		return nbTrain;
	}
	
	public int getReservedPlaces() {
		return this.reservedPlaces;
	}
	
	public void AddReservedPlace()
	{
		this.reservedPlaces ++;
	}
	
	public void RemoveReservedPlace()
	{
		this.reservedPlaces --;
	}
	
	@Override
	public synchronized void removeTrain() {
		nbTrain--;
		reservedPlaces--;
		notifyAll();
	}
	
	public boolean verify() {
		simulate();
		boolean res = invariant();
		stopSimulation();
		return res;
	}
	
	private void simulate() {
		nbTrain++;
	}
	
	private boolean invariant() {
		return nbTrain<=size;
	}
	
	private void stopSimulation() {
		nbTrain--;
	}
}
