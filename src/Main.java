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

        String apenasIp = new StringTokenizer(ip, "/").nextToken();
        StringTokenizer st = new StringTokenizer(apenasIp, ".");
        StringTokenizer mascara = new StringTokenizer(ip, "/");

        String primeiraToken = new StringTokenizer(ip, ".").nextToken();
        String primeiroBinario = String.format("%8s", Integer.toBinaryString(Integer.parseInt(primeiraToken))).replace(' ', '0');

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
        System.out.println();

        String classe = "";
        if (primeiroBinario.startsWith("00") || primeiroBinario.startsWith("01")) {
            classe = "A";
        } else if (primeiroBinario.startsWith("10")) {
            classe = "B";
        } else if (primeiroBinario.startsWith("11")) {
            classe = "C";
        }

        if (!classe.isEmpty()) {
            System.out.println("IP classe " + classe + " com máscara /" + ultimaToken);
        } else {
            System.out.println("Classe do ip " + ip + " não identificada!");
        }
        System.out.println();
        s.close();
    }
}
