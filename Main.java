import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        File file = new File("rota.csv");
        ElaborazioneCSV classeLavoro = new ElaborazioneCSV();
        classeLavoro.aggiungiRecord(recordCasuale(classeLavoro.numeroDiCampi(file)), file);
        classeLavoro.aggiungiMioCampo(file);
        classeLavoro.aggiungiCampoCancellazioneLogica(file);
        String[] campi = classeLavoro.mostraCampi(new String[]{"County", "City"}, file);
        stampaArrString(campi);

        //classeLavoro.dimensioneFissa(file);
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

    public static void stampaArrString(String[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
