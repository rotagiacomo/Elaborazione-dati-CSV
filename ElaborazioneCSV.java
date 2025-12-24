import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ElaborazioneCSV {
    private void aggiungiCampoCSV(String[] campo, File fileCSV) throws IOException {
        File temp = new File("temp.csv");
        FileWriter fileWriter = new FileWriter(temp);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileCSV));
        for (int i=0; i<campo.length; i++){
            String linea = bufferedReader.readLine();
            printWriter.println(linea + "," + campo[i]);
        }
        printWriter.close();
        bufferedReader.close();
        Files.move(temp.toPath(), fileCSV.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public void aggiungiMioCampo(File file) throws IOException{
        int numeroRecord = numeroDiRecord(file);
        String[] recordMioValore = new String[numeroRecord+1];
        recordMioValore[0] = "mio valore";
        for (int i = 1; i<recordMioValore.length; i++){
            recordMioValore[i] = Integer.toString((int)(Math.random()*11)+10);
        }
        aggiungiCampoCSV(recordMioValore, file);
    }

    public void aggiungiCampoCancellazioneLogica(File file) throws IOException{
        int numeroRecord = numeroDiRecord(file);
        String[] nuovoCampo = new String[numeroRecord+1];
        nuovoCampo[0] = "Cancellazione logica";
        for (int i=1; i<numeroRecord; i++){
            nuovoCampo[i] = Boolean.toString(false);
        }
        aggiungiCampoCSV(nuovoCampo, file);
    }

    private int numeroDiRecord(File file) throws IOException{
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int numeroRecord = 0;
        while (bufferedReader.readLine() != null){
            numeroRecord++;
        }
        bufferedReader.close();
        return numeroRecord-1;
    }
}
