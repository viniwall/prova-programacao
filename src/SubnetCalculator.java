import java.util.Scanner;

public class SubnetCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o endereço IP (ex: 199.42.78.133): ");
        String ipTexto = scanner.nextLine();

        System.out.print("Digite a máscara de subrede (ex: 255.255.255.224): ");
        String mascaraTexto = scanner.nextLine();

        String[] ipPartes = ipTexto.split("\\.");
        String[] mascaraPartes = mascaraTexto.split("\\.");

        int[] ip = new int[4];
        int[] mascara = new int[4];
        int[] rede = new int[4];

        for (int i = 0; i < 4; i++) {
            ip[i] = Integer.parseInt(ipPartes[i]);
            mascara[i] = Integer.parseInt(mascaraPartes[i]);
            rede[i] = ip[i] & mascara[i];
        }

        System.out.println("\nEndereço de rede (decimal): " + rede[0] + "." + rede[1] + "." + rede[2] + "." + rede[3]);

        String binario = "";
        for (int i = 0; i < 4; i++) {
            if (i > 0) binario += ".";
            binario += String.format("%8s", Integer.toBinaryString(rede[i])).replace(' ', '0');
        }

        System.out.println("Endereço de rede (binário): " + binario);

        scanner.close();
    }
}
