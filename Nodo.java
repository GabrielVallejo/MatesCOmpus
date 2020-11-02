
public class Nodo {
	private int[] indice1, 
				indice2;
	private char name;
	
	public Nodo(int n, int m, int l, int k, char name) {
		this.indice1 = new int[2];
		this.indice1[0] = n;
		this.indice1[1] = m;
		this.indice2 = new int[2];
		this.indice2[0] = l;
		this.indice2[1] = k;
		this.name = name;
	}

	public int[] getIndice1() {
		return indice1;
	}

	public int[] getIndice2() {
		return indice2;
	}

	public char getName() {
		return name;
	}

	public void setIndice1(int[] indice1) {
		this.indice1 = indice1;
	}

	public void setIndice2(int[] indice2) {
		this.indice2 = indice2;
	}

	public void setName(char name) {
		this.name = name;
	}
}
