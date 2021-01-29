package train;

/**
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 */
public class Main {
	public static void main(String[] args) {
		Station A = new Station("GareA", 3);
		Station C = new Station("GareC", 2);
		Station E = new Station("GareE", 3);
		
		Section AB = new Section("AB");
		Section BC = new Section("BC");
		
		Section CD = new Section("CD");
		Section DE = new Section("DE");
		
		
		
		Railway r = new Railway(new Element[] { A, AB, BC, C, CD, DE, E });
		System.out.println("The railway is:");
		System.out.println("\t" + r);
		Position p = new Position(A, Direction.LR);
		Position p2 = new Position(E, Direction.RL);
		try {
			Train t1 = new Train("1", p);
			Train t2 = new Train("2", p);
			Train t3 = new Train("3", p);
			
			Train t4 = new Train("4", p2);
			Train t5 = new Train("5", p2);
			Train t6 = new Train("6", p2);
			System.out.println(t1);
			System.out.println(t2);
			System.out.println(t3);
			
			System.out.println(t4);
			System.out.println(t5);
			System.out.println(t6);
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();
		} catch (BadPositionForTrainException e) {
			System.out.println("Le train " + e.getMessage());
		}
		
		

	}
}
