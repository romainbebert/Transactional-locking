import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class TL2Register<T> extends ReentrantLock implements Register<T>{
	
	private static AtomicInteger idCounter = new AtomicInteger(0);
	private T value;
	private int date;
	private int id;
	
	
	public TL2Register(T value) {
		super(true);
		this.date = 0;
		this.value = value;
		this.id = idCounter.getAndIncrement();
	}
	
	// Used for making a copy of the register
	private TL2Register(T value, int date, int id) {
		super(true);
		this.date = date;
		this.value = value;
		this.id = id;
	}
	
	public T read (Transaction<T> t) throws AbortException{
		
		if(this.isLocked()) throw new AbortException("Register locked in READ");

		
		if(t.getLcx(id) != null) {
			return (T)t.getLcx(id).getValue();
		}else {
			
			t.addNewLcx(this.copy());
			t.addLrs(this);

			if(t.getLcx(id).getDate() > t.getBirth()) {
				throw new AbortException("Aboooooooooooooooort read");
			}else {
				//System.out.println(t + " I read this and I return value : " + t.getLcx(id).getValue());
				return (T)t.getLcx(id).getValue();
			}
		}	
	}
	
	public void write(Transaction<T> t, T v) throws AbortException {
		
		//if(this.isLocked()) throw new AbortException("Register locked in WRITE");
		
		//System.out.println(this.getOwner() + "  " + t + " on m'a donné la valeur : " + v);
		if(t.getLcx(id) == null){
			//System.out.println(t + "I'm here");
			t.addNewLcx(this.copy(), v);
		}
		
		t.setLcxValue(this.id, v);
		t.addLws(this);
	}
	
	public Register<T> copy() {
		return new TL2Register<T>(this.value, this.date, this.id);
	}
	
	public int getDate() {
		return date;
	}
	
	public void setDate(int d) {
		date = d;
	}
	
	public T getValue() {
		return (T) value;
	}
	
	public void setValue(T v) {
		value = v;
	}
	
	public void setValueAndDate(T v, int d) {
		date = d;
		value = v;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object register) {
		if (register == null) return false;
	    if (register == this) return true;
	    if (!(register instanceof TL2Register<?>)) return false;
		TL2Register<T> r = (TL2Register<T>) register;
		return this.id == r.getId();
	}
}
	
