package pdf;

import org.springframework.stereotype.Component;
import path.PathApp;

import java.io.*;

@Component
public class StatementWorker {
    public StatementWorker() {
    }

    public File[] getListOfStatements(String path) {
        File folder = new File(path);
        //getting list of files and showing them to users
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }

    public void downloadStatement(String fullPath, OutputStream out) throws IOException {
        FileInputStream in = new FileInputStream(fullPath);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }

    public void deleteStatement(String fullPath) throws IOException {
        File file = new File(fullPath);
        file.delete();
    }
}
