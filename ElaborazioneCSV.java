import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ElaborazioneCSV {

    private void aggiungiCampoCSV(String nomeCampo, String[] valori ,File file) throws IOException {
        try {
            int indiceCampo = indiceCampo(nomeCampo, file);
            assegnaValoriMancati(indiceCampo, valori, file);
        }catch (CampoNotFoundException exception){
            File temp = new File("temp.csv");
            PrintWriter printWriter = new PrintWriter(new FileWriter(temp));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String riga = bufferedReader.readLine();
            printWriter.println(riga + "," + nomeCampo);
            riga = bufferedReader.readLine();
            for (int i=0; riga != null; i++){
                if (i < valori.length) {
                    printWriter.println(riga + "," + valori[i]);
                }else {
                    printWriter.println(riga + ",");
                }
                riga = bufferedReader.readLine();
            }
            printWriter.close();
            bufferedReader.close();
            Files.move(temp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void aggiungiMioCampo(File file) throws IOException{
        String nomeCampo = "Mio valore";
        String[] recordMioValore = new String[(numeroRighe(file)-1)];
        for (int i = 0; i<recordMioValore.length; i++){
            recordMioValore[i] = Integer.toString((int)(Math.random()*11)+10);
        }
        aggiungiCampoCSV(nomeCampo, recordMioValore, file);
    }

    public void aggiungiCampoCancellazioneLogica(File file) throws IOException{
        String nomeCampo = "Cancellazione logica";
        String[] valoriCampo = new String[(numeroRighe(file)-1)];
        for (int i = 0; i < valoriCampo.length; i++) {
            valoriCampo[i] = Boolean.toString(false);
        }
        aggiungiCampoCSV(nomeCampo, valoriCampo, file);
    }

    private void assegnaValoriMancati(int indiceCampo, String[] valori, File file) throws IOException{
        //Da implementare
    }

    private int numeroRighe(File file) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int numeroRighe = 0;
        while (bufferedReader.readLine() != null){
            numeroRighe++;
        }
        bufferedReader.close();
        return numeroRighe;
    }

    public int numeroDiCampi(File file) throws IOException{
        String[] campi = getCampi(file);
        return campi.length;
    }

    public String[] getCampi(File file) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String rigaCampi = bufferedReader.readLine();
        bufferedReader.close();
        return rigaCampi.split(",");
    }

    public int[] lunghezzaCampi(File file)throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String riga = bufferedReader.readLine();
        int[] lunghezzaCampi = new int[numeroDiCampi(file)];
        while (riga != null){
            String[] rigaSplit = riga.split(",");
            for (int i = 0; i<rigaSplit.length; i++){
                if (!rigaSplit[i].trim().isEmpty()){
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
        String nuovaRiga = "";
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
        printWriter.println(nuovaRiga);
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

    public String recodDiCampo(String campo, int riga,File file) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int indiceCampo = indiceCampo(campo, file);
        for (int i=0; i<riga-1; i++){
            bufferedReader.readLine();
        }
        String record = bufferedReader.readLine();
        bufferedReader.close();
        record = record.split(",")[indiceCampo];
        return record;
    }

    private int indiceCampo(String campo, File file)throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String[] campi = bufferedReader.readLine().split(",");
        bufferedReader.close();
        int indiceCampo = -1;
        for (int i=0; i<campi.length; i++){
            if (campi[i].trim().equals(campo.trim())){
                indiceCampo = i;
                break;
            }
        }
        if (indiceCampo == -1){
            throw new CampoNotFoundException();
        }
        return indiceCampo;
    }

    public void modificaRecord(String campo, int riga, String nuovoRecord,File file) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        File temp = new File("temp.csv");
        PrintWriter printWriter = new PrintWriter(new FileWriter(temp));
        int indiceCampo = indiceCampo(campo, file);
        String rigaLetta = bufferedReader.readLine();
        for (int i=1; rigaLetta != null; i++){
            if (i == riga){
                String[] rigaSplit = rigaLetta.split(",");
                String nuovaRiga = "";
                for (int j=0; j<rigaSplit.length; j++){
                    if (j == indiceCampo){
                        nuovaRiga += nuovoRecord;
                    }else {
                        nuovaRiga += rigaSplit[j];
                    }
                    if (j < rigaSplit.length-1){
                        nuovaRiga += ",";
                    }
                }
                printWriter.println(nuovaRiga);
            }else {
                printWriter.println(rigaLetta);
            }
            rigaLetta = bufferedReader.readLine();
        }
        printWriter.close();
        bufferedReader.close();
        Files.move(temp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public void cancellaLogicamente(int riga, File file) throws IOException{
        modificaRecord("Cancellazione logica", riga, "true",file);
    }

    public void annullaCancellaLogicamente(int riga, File file) throws IOException{
        modificaRecord("Cancellazione logica", riga, "false", file);
    }
}
