import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ElaborazioneCSV {
    private void aggiungiCampoCSV(String[] campo, File file) throws IOException {
        File temp = new File("temp.csv");
        FileWriter fileWriter = new FileWriter(temp);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        for (int i=0; i<campo.length; i++){
            String linea = bufferedReader.readLine();
            printWriter.println(linea + "," + campo[i]);
        }
        printWriter.close();
        bufferedReader.close();
        Files.move(temp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
        for (int i=1; i<=numeroRecord; i++){
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

    public int numeroDiCampi(File file) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String rigaCampi = bufferedReader.readLine();
        bufferedReader.close();
        String[] splitRigaCampi = rigaCampi.split(",");
        return splitRigaCampi.length;
    }

    public int[] lunghezzaCampi(File file)throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String riga = bufferedReader.readLine();
        int[] lunghezzaCampi = new int[numeroDiCampi(file)];
        while (riga != null){
            String[] rigaSplit = riga.split(",");
            for (int i = 0; i<rigaSplit.length; i++){
                if (!rigaSplit[i].isEmpty()){
                    lunghezzaCampi[i]++;
                }
            }
            riga = bufferedReader.readLine();
        }
        bufferedReader.close();
        return lunghezzaCampi;
    }

    private int lunghezzaRecord(String record){
        return record.length();
    }

    private int[] recordLunghezzaMaggiore(File file) throws IOException{
        int[] lunghezzaMaggiore = new int[numeroDiCampi(file)];
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String riga = bufferedReader.readLine();
        while (riga != null){
            String[] rigaSplit = riga.split(",");
            for (int i=0; i<rigaSplit.length; i++){
                if (lunghezzaMaggiore[i]<lunghezzaRecord(rigaSplit[i])){
                    lunghezzaMaggiore[i] = lunghezzaRecord(rigaSplit[i]);
                }
            }
            riga = bufferedReader.readLine();
        }
        bufferedReader.close();
        return lunghezzaMaggiore;
    }

    public void dimensioneFissa(File file)throws IOException{
        int[] recordLunghezzaMaggiore = recordLunghezzaMaggiore(file);
        File temp = new File("temp.csv");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        PrintWriter printWriter = new PrintWriter(new FileWriter(temp, true));
        String riga = bufferedReader.readLine();
        while (riga != null){
            String nuovaRiga = "";
            String[] rigaSplit = riga.split(",");
            for (int i=0; i< rigaSplit.length; i++){
                int numeroSpazi = recordLunghezzaMaggiore[i]-lunghezzaRecord(rigaSplit[i]);
                nuovaRiga += rigaSplit[i] + spazi(numeroSpazi) + ",";
            }
            printWriter.println(nuovaRiga);
            riga = bufferedReader.readLine();
        }
        bufferedReader.close();
        printWriter.close();
        Files.move(temp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private String spazi(int numeroSpazi){
        String string = "";
        for (int i=0; i<numeroSpazi; i++){
            string += " ";
        }
        return string;
    }

}
