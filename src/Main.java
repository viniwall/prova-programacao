import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Insira um número IPv4 válido com sua máscara (xxx.xxx.x.xx/máscara)");
        String ip = s.nextLine();

        if (!ip.contains(".") || !ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}/[0-9]{1,2}")) {
            System.out.println("O IP e a máscara devem conter números e devem ser separados por '.' ou '/'!");
            s.close();
            return;
        }

        StringTokenizer st = new StringTokenizer(ip, "./");
        StringTokenizer mascara = new StringTokenizer(ip, "/");

        String primeiraToken = st.nextToken();
        String ultimaToken = "";
        while (mascara.hasMoreTokens()) {
            String tokenPlaceholder = mascara.nextToken();
            if (mascara.countTokens() == 0) {
                ultimaToken = tokenPlaceholder;
            }
        }
        
        System.out.print("IP em binário: ");
        while (st.hasMoreTokens()) {
            int valor = Integer.parseInt(st.nextToken());

            String binario = String.format("%8s", Integer.toBinaryString(valor)).replace(' ', '0');

            System.out.print(binario);
            if (st.hasMoreTokens()) System.out.print(".");
        }
        if (primeiraToken.startsWith("00") && ultimaToken.equals("8") || primeiraToken.startsWith("01") && ultimaToken.equals("8")) {
            System.out.println("IP classe A com máscara padrão /8");
        } else if (primeiraToken.startsWith("10") && ultimaToken.equals("16")) {
            System.out.println("IP classe B com máscara padrão /16");
        } else if (primeiraToken.startsWith("11") && ultimaToken.equals("24")) {
            System.out.println("IP classe C com máscara padrão /24");
        } else {
            System.out.println("Classe do ip " + ip + " não identificada!");
        }
        System.out.println();
        s.close();
    }
}