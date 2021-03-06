/* This object have been created in the need of the project and is a funny object
 * 
 * @author Romain Bernard, Loïc Boutin, Ivan Dromigny
 * 
 */


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TL2RandString implements Runnable {

	private volatile boolean running;
	
	private ArrayList<TL2Register<String>> registers;
	
	public TL2RandString(ArrayList<TL2Register<String>> r) {
		registers = new ArrayList<TL2Register<String>> ();
		this.registers = r;
	}
	
	// Function use to add a string to the register
	public void addText(Register<String> reg, String s) {
		Transaction<String> t = new TL2Transaction<String>();

		while(!t.isCommited()) {
			try{
				t.begin();
				reg.write(t, (String) reg.read(t) + s);
				t.try_to_commit();
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
	}
	
	// Function use to del a string to the register if it's present in it
	public void delText(Register<String> reg, String s) {
		Transaction<String> t = new TL2Transaction<String>();

		while(!t.isCommited()) {
			try{
				t.begin();
				if(((String) reg.read(t)).contains(s)) {
					reg.write(t, (String) reg.read(t).substring(reg.read(t).indexOf(s), reg.read(t).indexOf(s)+s.length()));
				}else {
					//System.out.println("Doesn't contain the substring");
				}
				t.try_to_commit();
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		this.running = true;
		
		int randomChoice = ThreadLocalRandom.current().nextInt(1, 3);
		int randomReg = ThreadLocalRandom.current().nextInt(0, registers.size());
		
		Random r = new Random();
		
		String s = "";
	    String alphabet = "ivan&romain&loic&jean-patrickmaurice";
	    
	    for (int i = 0; i < 5; i++) {
	        s += alphabet.charAt(r.nextInt(alphabet.length()));
	    }
	    
	    if(randomChoice == 1) { 
	    	
		    for (int i = 0; i < 5; i++) {
		        s += alphabet.charAt(r.nextInt(alphabet.length()));
		    }
		    
	    	addText(registers.get(randomReg), s);
	    } else {
	    	
		    for (int i = 0; i < 3; i++) {
		        s += alphabet.charAt(r.nextInt(alphabet.length()));
		    }
		    
	    	delText(registers.get(randomReg), s);
	    }
	}
	
	public void stop() {
		this.running = false;
	}
}
