package data;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class fileLoader {
    public fileLoader(String file_path, int lines){
        path = file_path;
        numberOfLines = lines;
    }

    public String[] openFile() throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader textReader = new BufferedReader(fr);
        String[] textData = new String[numberOfLines];
        for (int i=0; i<numberOfLines; i++){
            textData[i] = textReader.readLine();
        }
        textReader.close();
        return textData;
    }

    private int numberOfLines;
    private String path;
}
