import java.util.LinkedList;

public class Simbolo {
	private LinkedList<String> destinos;
	private boolean e_transicion,
					terminal;
	private Character name;
	
	public Simbolo(Character name, String str) {
		this.name = name;
		String[] temp = str.split("\\|");
		this.e_transicion = false;
		this.terminal = false;
		this.destinos = new LinkedList<String>();
		for (int i = 0; i < temp.length; i++) {
			if(temp[i].length() == 1 && temp[i].equals("ℇ")) {
				this.e_transicion = true;
			}
			this.destinos.add(temp[i]);
		}
	}
	
	public Simbolo(Character name) {
		this.name = name;
		this.e_transicion = false;
		this.terminal = false;
		this.destinos = new LinkedList<String>();
	}
		
	public boolean getETransicion() {
		return this.e_transicion;
	}
	
	public LinkedList<String> getDestino() {
		return this.destinos;
	}
	
	public void setE_Transicion(boolean value){
		this.e_transicion = value;
	}
	
	public boolean getTerminal() {
		return this.terminal;
	}
	
	public void setTerminal( ) {
		String temp;
		for (int i = 0; i < this.destinos.size(); i++) {
			temp = this.destinos.get(i);
			if (temp.equals(temp.toLowerCase())) {
				this.terminal = true;
				break;
			}
		}
	}
	
	public void setTerminal(boolean value) {
		this.terminal = value;
	}
	
	public Character getName() {
		return this.name;
	}

}
