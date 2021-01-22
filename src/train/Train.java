package train;

/**
 * Représentation d'un train. Un train est caractérisé par deux valeurs :
 * <ol>
 *   <li>
 *     Son nom pour l'affichage.
 *   </li>
 *   <li>
 *     La position qu'il occupe dans le circuit (un élément avec une direction) : classe {@link Position}.
 *   </li>
 * </ol>
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Mayte segarra <mt.segarra@imt-atlantique.fr>
 * Test if the first element of a train is a station
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 * @version 0.3
 */
public class Train extends Thread implements Runnable  {
	private final String name;
	private Position pos;
	
	public Train(String name, Position p) throws BadPositionForTrainException {
		if (name == null || p == null)
			throw new NullPointerException();

		// A train should be first be in a station
		if (!(p.getPos() instanceof Station))
			throw new BadPositionForTrainException(name);
		this.name = name;
		this.pos = p.clone();
		
		p.getPos().addTrain(this);
	}

	public void move()
	{		
		pos.toNextPosition(this);
		
		//System.out.println("Le train "+this.name+" est en position "+ this.pos.toString());
	}
	
	public void run() {
		while(true) {
			move();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Train[");
		result.append(this.name);
		result.append("]");
		result.append(" is on ");
		result.append(this.pos);
		return result.toString();
	}
	
	public boolean invariant() {
		return false;
	}
	
	public String getTrainName() {
		return name;
	}
	
	public Element getElement(){
		return pos.getPos();
	}
	
	public Direction getDirection() {
		return pos.getDir();
	}
}
