package general;

import entity.*;

public class Main {

	public static void main(String[] args) {
		EntitySet entitySet = new EntitySet();
		int id = 0;
		
		entitySet.insert(new BadBeast(id++,new XY(10,10)));
		entitySet.insert(new BadPlant(id++,new XY(10,30)));
		entitySet.insert(new GoodBeast(id++,new XY(2,10)));
		entitySet.insert(new GoodPlant(id++,new XY(16,18)));
		entitySet.insert(new HandOperatedMasterSquirrel(id++,new XY(1,1)));
		entitySet.insert(new Wall(id++,new XY(77,19)));
		
		
		while (true) {
			System.out.println(entitySet.toString());
			entitySet.nextStep();
			
		}
	}
}
