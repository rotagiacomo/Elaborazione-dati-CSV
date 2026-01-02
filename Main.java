import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        File file = new File("rota.csv");
        ElaborazioneCSV classeLavoro = new ElaborazioneCSV();
        classeLavoro.aggiungiRecord(recordCasuale(classeLavoro.numeroDiCampi(file)), file);
        classeLavoro.aggiungiMioCampo(file);
        classeLavoro.aggiungiCampoCancellazioneLogica(file);
        System.out.println("numero campi: " + classeLavoro.numeroDiCampi(file));

        int[] lunghezzaCampi = classeLavoro.lunghezzaCampi(file);
        System.out.println("lunghezza camp: " + intToString(lunghezzaCampi));

        classeLavoro.dimensioneFissa(file);
    }

    public static String intToString(int[] interi){
        String string = "";
        for (int i = 0; i< interi.length; i++){
            string += interi[i];
            if (i < interi.length-1){
                string += ",";
            }
        }
        string += "]";
        return string;
    }

    public static String[] recordCasuale(int numeroCampi){
        String[] records = new String[numeroCampi];
        for (int i=0; i<numeroCampi; i++){
            records[i] = Integer.toString((int) (Math.random()*100));
        }
        return records;
    }
}
