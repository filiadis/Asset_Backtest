package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadCsv {

    private File filePath;

    public List readCsvFile (List<File> file) throws FileNotFoundException {

        if(file != null) {
            for(int i=0; i<file.size(); i++) {
                filePath = file.get(i);
                System.out.println(filePath);
            }
        } else {
            System.out.println("Chooser was cancelled");}

        List<List<String>> result = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath.getPath()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                List<String> fields = new ArrayList<>();
                try (Scanner rowScanner = new Scanner(line)) {
                    rowScanner.useDelimiter(",");
                    while (rowScanner.hasNext()) {
                        fields.add(rowScanner.next());
                    }
                }

                result.add(fields);
            }
        }

        return result;

    }

}

