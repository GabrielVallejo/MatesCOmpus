import java.util.LinkedList;

import javax.sound.midi.Soundbank;

import com.sun.javafx.binding.SelectBinding.AsString;

import javafx.scene.control.TreeSortMode;

public class FNC {
	private GLC glc;
	
	public FNC (GLC glc){
		this.glc = glc;
		for (int i = 0; i < this.glc.getIniciales().size(); i++) {
			limpiarETransicion();
		}
		limpiarTransicionUnitaria();
		limpiarInutiles();
		limpiarInalcanzables();
		transformadaFNC();
	}
	
	private void limpiarETransicion() {
		int size = this.glc.getIniciales().size();
		int pos;
		Simbolo temp;
		String tmp, 
			   nuevo = "";
		boolean flag = false;
		for (int i = 0; i < size; i++) {
			if (this.glc.getIniciales().get(i).getETransicion()) {
				for (int m = 0; m < size; m++) {
					temp = this.glc.getIniciales().get(m);
					int size2 = temp.getDestino().size();
					for (int j = 0; j < size2; j++) {
						tmp = temp.getDestino().get(j);
						//System.out.println(tmp);
						//System.out.println("Entro con "+this.glc.getIniciales().get(m).getName()+" y "+tmp);
						if(tmp.length() == 1 && tmp.equals("ℇ") && m == i) {
							temp.getDestino().remove(j);
							size2 --;
							j --;
						}else {
							int count = 0;
							for (int k = 0; k < tmp.length(); k++) {
								if (tmp.charAt(k) == this.glc.getIniciales().get(i).getName()) {
									count++;
								}
								if(tmp.charAt(k) != this.glc.getIniciales().get(i).getName()) {
									nuevo += tmp.charAt(k);
								}else {
									//Aqui esta el problema
									if(tmp.length() == 1) {
										if(!temp.getETransicion()) {
											temp.getDestino().add("ℇ");
											temp.setE_Transicion(true);
										}
									}
									
									flag = true;
								}
							}
							if(flag) {
								if(nuevo != "" && !temp.getDestino().contains(nuevo)) {
									temp.getDestino().add(nuevo);
								}
							}
							nuevo = "";
							flag = false;
							for (int k = 0; k < count-1; k++) {
								for (int k2 = 0; k2 < k+1; k2++) {
									nuevo += String.valueOf(this.glc.getIniciales().get(i).getName());
								}
								temp.getDestino().add(nuevo);
								nuevo = "";
							}
						}			
					}	
				}
				this.glc.getIniciales().get(i).setE_Transicion(false);
			}
		}		

	}
	
	private void limpiarTransicionUnitaria() {
		//this.glc.printTransiciones();

		Simbolo temp;
		for (int i = 0; i < this.glc.getIniciales().size(); i++) {
			temp = this.glc.getIniciales().get(i);
			int size = temp.getDestino().size();
			for (int j = 0; j < size; j++) {
				if(temp.getDestino().get(j).length() == 1 && temp.getDestino().get(j).equals( temp.getDestino().get(j).toUpperCase())) {
					for (int k = 0; k < this.glc.getIniciales().size(); k++) {
						if(this.glc.getIniciales().get(k).getName() == temp.getDestino().get(j).charAt(0)) {
							if(this.glc.getIniciales().get(k).getName() != temp.getName()) {
								for (int m = 0; m < this.glc.getIniciales().get(k).getDestino().size(); m++) {
									temp.getDestino().add(this.glc.getIniciales().get(k).getDestino().get(m));
								}
							}
							temp.getDestino().remove(j);
							j--;
							size--;
						}
						//Si la transicion unitiaria es hacia si mismo, solo eliminar
						//Si es hacia otro, sustituir por todas las transiciones del otro
						//AL hacer la parte de eliminar inalcanzables es donde se elimina uno que ya no es usado despues 
						// de eliminar las unitarias
						
					}
				}
				
			}
		}
	}
	
	private void limpiarInutiles() {
		int size = this.glc.getIniciales().size(), 
			s2;
		Simbolo temp;
		String tmp;
		for (int i = 0; i < size; i++) {
			temp = this.glc.getIniciales().get(i);
			temp.setTerminal();
		}
		for (int m = 0; m < size; m++) {
			for (int i = 0; i < size; i++) {
				temp = this.glc.getIniciales().get(i);
				if(!temp.getTerminal()) {
					for (int k = 0; k < temp.getDestino().size(); k++) {
						tmp = temp.getDestino().get(k);
						for (int j = 0; j < size; j++) {
							if (this.glc.getIniciales().get(j).getTerminal()) {
								if(tmp.indexOf(this.glc.getIniciales().get(j).getName())!= -1) {
									temp.setTerminal(true);
									break;
								}
							}
						}
					}
				}
			}
		}
		
		//this.glc.printTransiciones();
		for (int i = 0; i < size; i++) {
			if(!this.glc.getIniciales().get(i).getTerminal()) {
				//System.out.println(this.glc.getIniciales().get(i).getName());
				for (int j = 0; j < size; j++) {
					temp = this.glc.getIniciales().get(j);
					s2 = temp.getDestino().size();
					for (int k = 0; k < s2; k++) {
						tmp = temp.getDestino().get(k);
						if(tmp.indexOf(this.glc.getIniciales().get(i).getName()) !=-1) {
							temp.getDestino().remove(k);
							k--;
							s2--;
						}
					}
				}
				this.glc.getIniciales().remove(i);
				i--;
				size--;
			}
		}
	}
	
	private void limpiarInalcanzables() {
		int size = this.glc.getIniciales().size();
		LinkedList<Simbolo> copia = new LinkedList<Simbolo>();
		copia.addAll(this.glc.getIniciales());
		Simbolo temp;
		String tmp;
		copia.removeFirst();
		for (int i = 0; i < size; i++) {
			temp = this.glc.getIniciales().get(i);
			for (int j = 0; j < temp.getDestino().size(); j++) {
				tmp = temp.getDestino().get(j);
				for (int k = 0; k < size; k++) {
					//System.out.println(this.glc.getIniciales().size());
					if (tmp.indexOf(this.glc.getIniciales().get(k).getName()) !=-1) {
						if(copia.contains(this.glc.getIniciales().get(k))) {
							copia.remove(this.glc.getIniciales().get(k));
						}
					}
				}
			}
		}
		
		int s2 = copia.size();
		for (int i = 0; i < s2; i++) {
			this.glc.getIniciales().remove(copia.get(i));
		}
	}

	private void transformadaFNC() {
		Simbolo temp, 
				nuevo;
		String tmp, 
			   nuevoS = "";
		//this.glc.printTransiciones();
		int size = this.glc.getIniciales().size();
		for (int i = 0; i < this.glc.getIniciales().size(); i++) {
			temp = this.glc.getIniciales().get(i);
			int s2 = temp.getDestino().size();
			for (int j = 0; j < s2; j++) {
				tmp = temp.getDestino().get(j);
				//System.out.println(tmp+","+this.glc.getIniciales().get(i).getName());
				//this.glc.printTransiciones();
				if(tmp.length() > 2) {
					//System.out.println(tmp+"1");
					int last, previous;
					while (tmp.length() > 2) {
						last = tmp.length()-1;
						previous = tmp.length()-2;
						nuevoS += tmp.charAt(previous);
						nuevoS += tmp.charAt(last);
						Simbolo check = this.glc.check(nuevoS);
						
						if (check == null) {
							nuevo = new Simbolo(this.glc.getStorage().poll(), nuevoS);
							nuevoS = "";

							for (int k = 0; k < tmp.length()-2; k++) {
								nuevoS += tmp.charAt(k);
							}
							this.glc.getIniciales().add(nuevo);
							nuevoS += nuevo.getName();


						}else {
							nuevoS = "";

							for (int k = 0; k < tmp.length()-2; k++) {
								nuevoS += tmp.charAt(k);
							}
							nuevoS += check.getName();
						}
						
						tmp = nuevoS;
						nuevoS = "";
					}
					temp.getDestino().set(j, tmp);

				}

				if (tmp.length() == 2) {
					if(!tmp.equals(tmp.toUpperCase()) || !soloLetras(tmp)) {

						for (int k = 0; k < tmp.length(); k++) {
							
							if (Character.isLowerCase(tmp.charAt(k)) || !soloLetras(String.valueOf(tmp.charAt(k)))) {
								Simbolo check = this.glc.check(String.valueOf(tmp.charAt(k)));
								if (check == null || check.getName().equals(temp.getName())) {
									nuevo = new Simbolo(this.glc.getStorage().poll(), String.valueOf(tmp.charAt(k)));
									nuevo.setTerminal(true);
									this.glc.getIniciales().add(nuevo);
									nuevoS += String.valueOf(nuevo.getName());
								}else{
									nuevoS += check.getName();
								}
							} else {
								nuevoS += String.valueOf(tmp.charAt(k));
							}
						}
						temp.getDestino().set(j, nuevoS);
						tmp = nuevoS;
						nuevoS = "";
						
					}
				}
				
			}
		}
		this.glc.printTransiciones();
	}
	
	private boolean soloLetras(String cadena) {
		cadena = cadena.toLowerCase();
		for (int i = 0; i < cadena.length(); i++) {
			if(!Character.isLowerCase(cadena.charAt(i))) {
				return false;
			}
		}
		return true;
	}
 
}