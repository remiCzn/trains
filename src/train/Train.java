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
public class Train {
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
	}

	public void move()
	{
		Element e = pos.getPos().getNextElement(pos.getDir());
		
		if(e instanceof Station)
		{
			Direction newDir;
			if(pos.getDir() == Direction.LR)
				newDir = Direction.RL;
			else if(pos.getDir() == Direction.RL)
				newDir = Direction.LR;
			else
				newDir = Direction.LR; //Par défaut, n'est pas censé arriver
			
			this.pos = new Position(e, newDir);
		}
		else
		{
			this.pos = new Position(e, pos.getDir());
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
}
