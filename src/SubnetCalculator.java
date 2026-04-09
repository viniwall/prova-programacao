import java.util.Scanner;

public class SubnetCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("     CALCULADORA DE ENDEREÇO DE REDE      ");
        System.out.println("===========================================");

        int[] ip = lerIP(scanner, "Digite o endereço IP (ex: 199.42.78.133): ");
        int[] mascara = lerMascara(scanner);

        int[] rede = calcularRede(ip, mascara);
        int cidr = calcularCIDR(mascara);

        System.out.println("\n===========================================");
        System.out.println("              RESULTADO                    ");
        System.out.println("===========================================");

        System.out.printf("%-28s %s%n", "Endereço IP (decimal):", formatarDecimal(ip));
        System.out.printf("%-28s %s%n", "Endereço IP (binário):", formatarBinario(ip));
        System.out.println();
        System.out.printf("%-28s %s%n", "Máscara (decimal):", formatarDecimal(mascara) + " (/" + cidr + ")");
        System.out.printf("%-28s %s%n", "Máscara (binário):", formatarBinario(mascara));
        System.out.println();
        System.out.println("  Operação AND bit a bit:");
        System.out.println("  " + formatarBinario(ip));
        System.out.println("  " + formatarBinario(mascara));
        System.out.println("  " + "-".repeat(35));
        System.out.println("  " + formatarBinario(rede));
        System.out.println();
        System.out.printf("%-28s %s%n", "Endereço de rede (decimal):", formatarDecimal(rede));
        System.out.printf("%-28s %s%n", "Endereço de rede (binário):", formatarBinario(rede));
        System.out.printf("%-28s %s%n", "Notação CIDR:", formatarDecimal(rede) + "/" + cidr);

        System.out.println("===========================================");

        scanner.close();
    }

    static int[] lerIP(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            int[] ip = parsearIP(entrada);
            if (ip != null) return ip;
            System.out.println("  [Erro] IP inválido. Use o formato: 192.168.1.1");
        }
    }

    static int[] lerMascara(Scanner scanner) {
        while (true) {
            System.out.print("Digite a máscara (ex: 255.255.255.224 ou /27): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.startsWith("/")) {
                int[] mask = cidrParaMascara(entrada.substring(1));
                if (mask != null) return mask;
            } else if (entrada.contains(".")) {
                int[] mask = parsearIP(entrada);
                if (mask != null && mascaraValida(mask)) return mask;
            } else {
                int[] mask = cidrParaMascara(entrada);
                if (mask != null) return mask;
            }

            System.out.println("  [Erro] Máscara inválida. Use 255.255.255.0 ou /24");
        }
    }

    static int[] parsearIP(String s) {
        String[] partes = s.split("\\.");
        if (partes.length != 4) return null;
        int[] octetos = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                octetos[i] = Integer.parseInt(partes[i].trim());
                if (octetos[i] < 0 || octetos[i] > 255) return null;
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return octetos;
    }

    static int[] cidrParaMascara(String s) {
        try {
            int cidr = Integer.parseInt(s.trim());
            if (cidr < 0 || cidr > 32) return null;
            int[] mask = new int[4];
            for (int i = 0; i < 4; i++) {
                int bits = Math.min(8, Math.max(0, cidr - i * 8));
                mask[i] = bits == 0 ? 0 : (256 - (int) Math.pow(2, 8 - bits));
            }
            return mask;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static boolean mascaraValida(int[] mask) {
        int cidr = calcularCIDR(mask);
        int[] reconstruida = cidrParaMascara(String.valueOf(cidr));
        for (int i = 0; i < 4; i++) {
            if (mask[i] != reconstruida[i]) return false;
        }
        return true;
    }

    static int[] calcularRede(int[] ip, int[] mascara) {
        int[] rede = new int[4];
        for (int i = 0; i < 4; i++) {
            rede[i] = ip[i] & mascara[i];
        }
        return rede;
    }

    static int calcularCIDR(int[] mascara) {
        int cidr = 0;
        for (int octeto : mascara) {
            int b = octeto;
            while (b != 0) {
                cidr += b & 1;
                b >>= 1;
            }
        }
        return cidr;
    }

    static String formatarDecimal(int[] octetos) {
        return octetos[0] + "." + octetos[1] + "." + octetos[2] + "." + octetos[3];
    }

    static String formatarBinario(int[] octetos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (i > 0) sb.append(".");
            sb.append(String.format("%8s", Integer.toBinaryString(octetos[i])).replace(' ', '0'));
        }
        return sb.toString();
    }
}