import java.util.Optional;
import java.util.Scanner;

class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("01. Resultado Eleiçao");
        System.out.println("02. Algoritmo Bubble Sort");
        System.out.println("03. Fatorial");
        System.out.println("04. Soma de múltiplos");
        System.out.println("05. Sair");
        
        int userSelection = 0;
        
        do {
            System.out.print("Escolha o projeto para visualizar: ");
            String input = scanner.nextLine();
            ParseIntResult parseResult = tryParseInt(input);

            if (parseResult.isValid) {
                userSelection = parseResult.value;
                switch (userSelection) {
                    case 1:
                        Election.main(args);
                        break;
                    case 2:
                        BubbleSort.main(args);
                        break;
                    case 3:
                        Fatorial.main(args);
                        break;
                    case 4:
                        MultiplesSum.main(args);
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número.");
            }
        } while (userSelection != 5);

        scanner.close();
    }

    public static ParseIntResult tryParseInt(String value) {
        try {
            int parsedValue = Integer.parseInt(value);
            ParseIntResult result = new ParseIntResult(true, parsedValue);
            return result;
        } catch (NumberFormatException e) {
            ParseIntResult result = new ParseIntResult(false, 0);
            return result;
        }
    }
}

class ParseIntResult {
    public boolean isValid;
    public Integer value;

    public ParseIntResult(boolean isValid, Integer value) {
        this.isValid = isValid;
        this.value = value;
    }
}