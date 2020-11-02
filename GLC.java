import java.util.LinkedList;
import java.util.Scanner;

public class GLC {
	private String glc;
	private LinkedList<Simbolo> iniciales;
	private LinkedList<Character> storage;
	
	
	public GLC(String glc, String entrada) {
		System.out.println("Gramática entrante: "+glc);
		System.out.println("Cadena entrante: "+entrada);
		this.glc = glc;
		this.iniciales = new LinkedList<Simbolo>();
		this.storage = new LinkedList<Character>();
		this.storage.add('Q');
		this.storage.add('W');
		this.storage.add('R');
		this.storage.add('T');
		this.storage.add('U');
		this.storage.add('O');
		this.storage.add('Ñ');
		this.storage.add('X');
		this.storage.add('V');
		this.storage.add('P');
		this.storage.add('R');
		this.storage.add('O');
		this.storage.add('M');
		this.storage.add('N');
		this.storage.add('L');
		Init(entrada);
	}
	
	public void Init(String entrada) {
		String[] path = this.glc.split(" ");
		String[] temp;
		for (int i = 0; i < path.length; i++) {
			temp = path[i].split("\\-");
			iniciales.add(new Simbolo(path[i].charAt(0), temp[1]));
		}
		FNC fnc = new FNC(this);
		CYK cyk = new CYK(entrada, this);
	}

	public LinkedList<Simbolo> getIniciales() {
		return this.iniciales;
	}
	
	public LinkedList<Character> getStorage(){
		return this.storage;
	}

	public void setIniciales(LinkedList<Simbolo> iniciales) {
		this.iniciales = iniciales;
	}

	public void printTransiciones() {
		Simbolo temp;
		System.out.print("Gramática en FNCh: ");
		//System.out.println(this.iniciales.size());
		for (int i = 0; i < this.iniciales.size(); i++) {
			temp = this.iniciales.get(i);
			System.out.print(temp.getName()+"-");
			for (int j = 0; j < temp.getDestino().size()-1; j++) {
				System.out.print(temp.getDestino().get(j)+"|");
			}
			System.out.print(temp.getDestino().peekLast()+" ");
		}
		System.out.println();

	}
	public Simbolo check(String dest){
		for (int i = 0; i < this.iniciales.size(); i++) {
			for (int j = 0; j < this.iniciales.get(i).getDestino().size(); j++) {
				if (this.iniciales.get(i).getDestino().get(j).equals(dest)) {
					return this.iniciales.get(i);
				}
			}
		}
		return null;
	}
	
	public int getIndex(char c) {
		for (int i = 0; i < this.iniciales.size(); i++) {
			if (this.iniciales.get(i).getName().equals(c)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese la gramática libre de contexto: ");
		String g = scanner.nextLine();
		System.out.println("Ingrese la cadena a evaluar: ");
		String cadena = scanner.nextLine();
		GLC glc = new GLC(g, cadena);
		/*GLC glc = new GLC("S-ab|SS|aSb|bSa|ba", "aabbab");
		GLC glc2 = new GLC("S-AB|SS|AC|BD|BA A-a B-b C-SB D-SA", "aabbab");
		GLC glc3 = new GLC("S-ab|SS|aSb|bSa|ba", "aabbaab");
		GLC glc4 = new GLC("S-AB|SS|AC|BD|BA A-a B-b C-SB D-SA", "aabbabb");*/
	}
}
