import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        File file = new File("rota.csv");
        ElaborazioneCSV classeLavoro = new ElaborazioneCSV();
        classeLavoro.aggiungiMioCampo(file);
        classeLavoro.aggiungiCampoCancellazioneLogica(file);
        System.out.println("numero campi: " + classeLavoro.numeroDiCampi(file));
        int[] lunghezzaCampi = classeLavoro.lunghezzaCampi(file);
        String lCampi = "lunghezza campi [";
        for (int i = 0; i< lunghezzaCampi.length; i++){
            lCampi += lunghezzaCampi[i];
            if (i < lunghezzaCampi.length-1){
                lCampi += ",";
            }
        }
        lCampi += "]";
        System.out.println(lCampi);
    }
}
