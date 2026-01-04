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
        int numeroRighe = numeroRighe(file);
        String[] recordMioValore = new String[numeroRighe];
        recordMioValore[0] = "Mio valore";
        for (int i = 1; i<numeroRighe; i++){
            recordMioValore[i] = Integer.toString((int)(Math.random()*11)+10);
        }
        aggiungiCampoCSV(recordMioValore, file);
    }

    public void aggiungiCampoCancellazioneLogica(File file) throws IOException{
        try {
            int indiceCampo = indiceCampo("Cancellazione logica", file);
            if (lunghezzaCampi(file)[indiceCampo] < numeroRighe(file)){
                aggiungiCancellazioneRecordMancanti(indiceCampo, file);
            }
        }catch (CampoNotFoundException exception){
            int numeroRighe = numeroRighe(file);
            String[] nuovoCampo = new String[numeroRighe];
            nuovoCampo[0] = "Cancellazione logica";
            for (int i = 1; i < numeroRighe; i++) {
                nuovoCampo[i] = Boolean.toString(false);
            }
            aggiungiCampoCSV(nuovoCampo, file);
        }
    }

    private void aggiungiCancellazioneRecordMancanti(int indiceCampo, File file) throws IOException{
        //Da implementare
    }

    private int numeroRighe(File file) throws IOException{
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int numeroRighe = 0;
        while (bufferedReader.readLine() != null){
            numeroRighe++;
        }
        bufferedReader.close();
        return numeroRighe;
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
                nuovaRiga += rigaSplit[i] + spazi(numeroSpazi);
                if (i < rigaSplit.length-1){
                    nuovaRiga += ",";
                }
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

    public void aggiungiRecord(String[] record, File file) throws IOException{
        PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
        String nuovaRiga = "\n";
        for (int i=0; i<record.length; i++){
            if (record[i] != null) {
                nuovaRiga += record[i];
            }else {
                nuovaRiga += " ";
            }
            if (i < record.length-1){
                nuovaRiga += ",";
            }
        }
        printWriter.print(nuovaRiga);
        printWriter.close();
    }

    public String[] mostraCampi(String[] campi, File file) throws IOException{
        int[] indiceCampi = new int[campi.length];
        for (int i=0; i<campi.length; i++){
            indiceCampi[i] = indiceCampo(campi[i], file);
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String[] campiMostrati = new String[numeroRighe(file)];
        for (int i=0; i<campiMostrati.length; i++){
            String[] rigaSplit = bufferedReader.readLine().split(",");
            String temp = "";
            for (int j=0; j<indiceCampi.length; j++){
                if (j != 0){
                    temp += " ";
                }
                temp += rigaSplit[indiceCampi[j]];
            }
            campiMostrati[i] = temp;
        }
        bufferedReader.close();
        return campiMostrati;
    }

    public String[] recodDiCampo(String campo, File file) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        bufferedReader.readLine();
        int indiceCampo = indiceCampo(campo, file);
        String[] records = new String[numeroRighe(file)-1];
        for (int i=0; i<records.length; i++){
            records[i] = bufferedReader.readLine().split(",")[indiceCampo];
        }
        bufferedReader.close();
        return records;
    }

    private int indiceCampo(String campo, File file)throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String[] campi = bufferedReader.readLine().split(",");
        bufferedReader.close();
        int indiceCampo = -1;
        for (int i=0; i<campi.length; i++){
            if (campi[i].equals(campo)){
                indiceCampo = i;
                break;
            }
        }
        if (indiceCampo == -1){
            throw new CampoNotFoundException();
        }
        return indiceCampo;
    }
}
