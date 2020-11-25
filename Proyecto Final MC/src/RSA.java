import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Random;

public class RSA {
	
	
	private static BigInteger MCD(BigInteger a, BigInteger b) {
		BigInteger residuo = a.remainder(b);
		if(residuo.compareTo(new BigInteger("0")) == 0) {
			return b;
		}
		return MCD(b, residuo);
	}
	
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Seleccione un # primo grande: ");
		BigInteger p = new BigInteger(scanner.nextLine());
		System.out.println("Seleccione otro # primo grande: ");
		BigInteger q = new BigInteger(scanner.nextLine());
		scanner.close();
		
		BigInteger n = q.multiply(p);
		BigInteger uno = new BigInteger("1");

		BigInteger size = p.subtract(uno);
		size = size.multiply(q.subtract(uno));
		BigInteger count;
		BigInteger pub = null;
		BigInteger priv = null;
		count = new BigInteger("2");
		while(count.compareTo(size) < 0) {
			pub = MCD(size, count);
			if (pub.compareTo(uno) == 0) {
				pub = count;
				priv = pub.modInverse(size);
				break;
			}
			count = count.add(uno);
		}
		
		System.out.println("N es igual a: "+n);
		System.out.println("El # de coprimos con N es: "+size);
		System.out.println("La llave pública es: "+pub);
		System.out.println("La llave privada es: "+priv);
		
		String original;	
		String enviado = "";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("Correo.txt"));
			FileWriter fw = new FileWriter("Enviado.txt");
			PrintWriter pw = new PrintWriter(fw);
			String linea;
			while ((linea=br.readLine())!=null) {
				for (int i = 0; i < linea.length(); i++) {	
					count = new BigInteger(Integer.toString(linea.charAt(i)));
					count = count.modPow(pub, n);
					enviado += count.toString()+" ";
					
				}
				
				count = new BigInteger(Integer.toString('\n'));
				count = count.modPow(pub, n);
				enviado += count.toString()+" ";

				pw.write(enviado);
				enviado = "";
			}
			
			br.close();
			pw.close();
			fw.close();
			
			br = new BufferedReader(new FileReader("Enviado.txt"));
			fw = new FileWriter("Recibido.txt");
			pw = new PrintWriter(fw);
			
			String[] leido; 
			original = "";
			
			while ((linea=br.readLine())!=null) {
				leido = linea.split(" ");
				for (int i = 0; i < leido.length; i++) {
					count = new BigInteger(leido[i]);
					count = count.modPow(priv, n);
					original += (char)count.intValue();
				}
				pw.write(original);
				original = "";
			}
			
			br.close();
			pw.close();
			fw.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo en la dirección especificada.");
		} catch (IOException ex) {
			 System.out.println("Ocurrió un error de I/O"+ex);
		}
		
		
	}
}
