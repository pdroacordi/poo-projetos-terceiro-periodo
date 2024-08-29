import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static ArrayList<String> columns = new ArrayList<>();
    public static ArrayList<String[]> matrix = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        getColumns();
        getRows();
        try {
            String path = writeCSVFile(getCSVText());
            System.out.println("Arquivo CSV gravado com sucesso em: " + path);
        } catch (Exception e) {
            System.out.println("Não foi possível gravar o arquivo CSV.");
        }
    }

    public static void getColumns() {
        char option = 'y';
        int counter = 1;
        do {
            System.out.println("Insira o nome da " + counter + "a coluna: ");
            String columnName = scanner.nextLine();
            columns.add(columnName);
            if (counter >= 3) {
                System.out.println("Deseja inserir outra coluna? (y/n) ");
                option = scanner.next().charAt(0);
            }
            counter++;
        } while (option != 'n');
    }

    public static void getRows() {
        char option = 'y';
        int counter = 1;

        do {
            String[] columnsValues = new String[columns.size()];
            scanner.nextLine();
            for (int i = 0; i < columns.size(); i++) {
                System.out.println("Adicione um valor à coluna " + columns.get(i) + " na linha " + counter + ": ");
                columnsValues[i] = scanner.nextLine();
            }
            matrix.add(columnsValues);
            System.out.println("Deseja inserir outra linha? (y/n) ");
            option = scanner.next().charAt(0);
            counter++;
        } while (option != 'n');
    }

    public static String getCSVText() {
        String columnsText = columns.stream()
                .map(row -> String.join(",", row))
                .collect(Collectors.joining(","));
        String data = matrix.stream()
                .map(row -> String.join(",", row))
                .collect(Collectors.joining(System.lineSeparator()));
        return columnsText +"\n"+ data;
    }

    public static String writeCSVFile(String data) {
        try {
            Path path = Paths.get("arquivo.csv");
            Files.write(path, data.getBytes(), StandardOpenOption.CREATE);
            return path.toAbsolutePath().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}