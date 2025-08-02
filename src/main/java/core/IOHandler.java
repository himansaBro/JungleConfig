package core;

import java.io.*;

public class IOHandler {
    /*design
    path:type=value\n
     */
    private File file;

    public IOHandler(File file) {
        this.file = file;
        if (!file.exists()) {
            try {
                boolean d =file.createNewFile();
                if (!d) throw new RuntimeException(new IOException("file cannot be created!"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String ReadFile() throws IOException {
        System.out.println(">>> IO Read");
        StringBuilder builder = new StringBuilder();
        FileReader fs = new FileReader(file);
        BufferedReader bff = new BufferedReader(fs);
        String line;
        while ((line=bff.readLine())!=null){
            builder.append(line).append("\n");
        }
        bff.close();
        fs.close();
        return builder.toString();
    }
    public void WriteFile(String data) throws IOException {
        System.out.println("<<< IO Write");
        FileWriter fr = new FileWriter(file,false);
        BufferedWriter br = new BufferedWriter(fr);
        br.write(data);
        br.close();
        fr.close();
    }
}
