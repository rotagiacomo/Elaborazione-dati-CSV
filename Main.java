import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        File file = new File("rota.csv");
        ElaborazioneCSV classeLavoro = new ElaborazioneCSV();
        classeLavoro.aggiungiMioCampo(file);
        classeLavoro.aggiungiCampoCancellazioneLogica(file);
        System.out.println("numero campi: " + classeLavoro.numeroDiCampi(file));
    }
}
