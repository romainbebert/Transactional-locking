import java.util.ArrayList;

public class Master {
	
	public static void main(String[] args) {
		
		//* jeux de tests increment, retirer ou ajouter un / à ce commentaire pour changer de jeu de tests
		Register<Integer> register0 = new TL2Register<Integer>(0);
		Register<Integer> register1 = new TL2Register<Integer>(10);
		Register<Integer> register2 = new TL2Register<Integer>(20);
		/*/
		Register<Integer> register0 = new TL2Register<Integer>(5000);
		Register<Integer> register1 = new TL2Register<Integer>(1000);
		Register<Integer> register2 = new TL2Register<Integer>(2000);
		/*jeux de test bankAccount (plus adaptés)*/
		
		ArrayList<TL2Pile> ArrayTL2 = new ArrayList<TL2Pile>();
		ArrayList<BankAccount> ArrayAccount = new ArrayList<BankAccount>();
		ArrayList<Thread> ArrayThreads = new ArrayList<Thread>();
		
		int nbThread = 50;
		
		ArrayAccount.add(new BankAccount(register0, register1, register2, 1));
		ArrayAccount.add(new BankAccount(2));
		ArrayAccount.add(new BankAccount(3));
		ArrayAccount.add(new BankAccount(2));
		ArrayAccount.add(new BankAccount(5));
		ArrayAccount.add(new BankAccount(18));
		ArrayAccount.add(new BankAccount(4));

		for(int i = 0; i < nbThread; ++i) {
			ArrayTL2.add(new TL2Pile(register0,100));
			ArrayThreads.add(new Thread(ArrayTL2.get(i), "Thread " + i));
			//ArrayThreads.add(new Thread(ArrayAccount.get(i%7), "Thread " + i));
		}
		
		// We launch all of the threads
		try {
			for(int i = 0; i < nbThread; ++i) {
				ArrayThreads.get(i).start();
			}
		} catch (Exception e) {e.printStackTrace();}
		
		// We wait for all of the threads to complete 
		for(int i = 0; i < nbThread; ++i) {
			try {
				ArrayThreads.get(i).join();
			}catch(Exception e) {e.printStackTrace();}
		}
		System.out.println("r0 value : " + register0.getValue());
		System.out.println("r0 date : " + register0.getDate());
		System.out.println("r1 value : " + register1.getValue());
		System.out.println("r1 date : " + register1.getDate());
		System.out.println("r2 value : " + register2.getValue());
		System.out.println("r2 date : " + register2.getDate());
	}
}
