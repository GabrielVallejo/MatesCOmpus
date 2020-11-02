import java.util.LinkedList;

public class CYK {
	private String entrada;
	private Nodo[][] tabla;
	private GLC glc;
	
	public CYK(String entrada, GLC glc) {
		this.entrada = entrada;
		this.glc = glc;
		tabla = new Nodo[this.entrada.length()][this.entrada.length()];
		if(entrada.length()>=1) {
			if(analisis()) {
				System.out.println("Es una cadena válida.");
				System.out.println("Árbol de derivación:");
				imprimirDerivacion();
			}else {
				System.out.println("Esta no es una cadena válida.");
			}
		}else {
			System.out.println("No ingreso una cadena");
		}
		
	}
	
	
	private boolean analisis() {
		Simbolo temp;
		String tmp = "";
		
		for (int i = 0; i < this.entrada.length(); i++) {
			temp = this.glc.check(String.valueOf(this.entrada.charAt(i)));
			if(temp != null) {
				this.tabla[i][i] = new Nodo(0, 0, 0, 0, temp.getName());
			}else {
				return false;
			}
		}
		
		for (int i = 1; i < this.tabla.length; i++) {
			for (int j = 0; j < this.tabla.length-i; j++) {
				for (int l = j; l < j+i; l++) {
					
					tmp += String.valueOf(tabla[j][l].getName());
					tmp += String.valueOf(tabla[l+1][j+i].getName());
					temp = this.glc.check(tmp);
					
					if(temp != null) {
						this.tabla[j][j+i] = new Nodo(j, l, l+1, j+i, temp.getName());
					}else if(tabla[j][j+i] == null ){
						this.tabla[j][j+i]= new Nodo(j, l, l+1, j+i, 'ø');
					}
					tmp ="";
				}
				
			}
			
		}
		
		if(tabla[0][this.entrada.length()-1].getName() == 'S') {
			return true;
		}
		return false;
	}
	
	private void imprimirDerivacion() {
		boolean finished = false;
		Nodo temp;
		Simbolo temporal;
		String tmp = "", nuevo = "";
		LinkedList<Nodo> historial = new LinkedList<Nodo>();
		System.out.println('S');
		int ind1 = this.tabla[0][this.entrada.length()-1].getIndice1()[0], 
			ind2 = this.tabla[0][this.entrada.length()-1].getIndice1()[1], 
			ind3 = this.tabla[0][this.entrada.length()-1].getIndice2()[0], 
			ind4 = this.tabla[0][this.entrada.length()-1].getIndice2()[1];
		Nodo aux1 = this.tabla[ind1][ind2], 
			 aux2 = this.tabla[ind3][ind4];
		historial.add(aux1);
		historial.add(aux2);
		tmp = String.valueOf(aux1.getName())+String.valueOf(aux2.getName());
		System.out.println(tmp);
		while(!finished) {
			if(tmp.equals(tmp.toLowerCase())) {
				finished = true;
			}else {
				for (int i = 0; i < tmp.length(); i++) {
					if(Character.isUpperCase(tmp.charAt(i))) {
						temp = historial.poll();
						ind1 = temp.getIndice1()[0];
						ind2 = temp.getIndice1()[1];
						ind3 = temp.getIndice2()[0];
						ind4 = temp.getIndice2()[1];
						if(ind1 == 0 && ind2 == 0 && ind3 == 0 && ind4 == 0) {
							
							int in = this.glc.getIndex(temp.getName());
							
							nuevo += this.glc.getIniciales().get(in).getDestino().get(0);
						}else {
							aux1 = this.tabla[ind1][ind2]; 
							aux2 = this.tabla[ind3][ind4];
							historial.add(aux1);
							historial.add(aux2);
							nuevo += String.valueOf(aux1.getName());
							nuevo += String.valueOf(aux2.getName());
						}
					}else {
						nuevo += tmp.charAt(i);
					}
				}
				System.out.println(nuevo);
				tmp = nuevo;
				nuevo = "";
				int size = historial.size();
				
			}
			
		}
	}
}
