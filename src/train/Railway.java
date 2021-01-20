package train;


/**
 * Représentation d'un circuit constitué d'éléments de voie ferrée : gare ou
 * section de voie
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Railway {
	private final Element[] elements;

	public Railway(Element[] elements) {
		if(elements == null)
			throw new NullPointerException();
		
		this.elements = elements;
		for (Element e : elements)
			e.setRailway(this);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Element e : this.elements) {
			if (first)
				first = false;
			else
				result.append("--");
			result.append(e);
		}
		return result.toString();
	}
	
	public Element getNextElement(Element e, Direction d)
	{
		int elementIndex = 0;
		for(int i = 0; i<elements.length;i++)
		{
			if(elements[i].equals(e))
				elementIndex = i;
		}
		if(d==Direction.LR)
			return elements[elementIndex+1];
		else
			return elements[elementIndex-1];
		
	}
}
