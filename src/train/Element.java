package train;

/**
 * Cette classe abstraite est la représentation générique d'un élément de base d'un
 * circuit, elle factorise les fonctionnalitÃ©s communes des deux sous-classes :
 * l'entrée d'un train, sa sortie et l'appartenance au circuit.<br/>
 * Les deux sous-classes sont :
 * <ol>
 *   <li>La représentation d'une gare : classe {@link Station}</li>
 *   <li>La représentation d'une section de voie ferrée : classe {@link Section}</li>
 * </ol>
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public abstract class Element {
	private final String name;
	protected Railway railway;

	protected Element(String name) {
		if(name == null)
			throw new NullPointerException();
		
		this.name = name;
	}

	public void setRailway(Railway r) {
		if(r == null)
			throw new NullPointerException();
		
		this.railway = r;
	}

	public Element getNextElement(Direction d)
	{
		return railway.getNextElement(this, d);
	}
	
	public Station getNextStation(Direction d) {
		if (getNextElement(d) instanceof Station) {
			return (Station) getNextElement(d);
		}
		return getNextElement(d).getNextStation(d);
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public abstract void addTrain(Train t);
	public abstract void removeTrain();
	public abstract int getNbTrain();
	
	public abstract int CountToRight(Train t);
	public abstract int CountToLeft(Train t);
}
