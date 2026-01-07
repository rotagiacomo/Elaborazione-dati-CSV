import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException{
        File file = new File("rota.csv");
        avviaMenu(file);
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

    public static void avviaMenu(File file) throws IOException{
        ElaborazioneCSV elaborazioneCSV = new ElaborazioneCSV();
        Scanner scanner = new Scanner(System.in);
        int opzione;
        do{
           System.out.println("1) aggiungere campo mio valore");
           System.out.println("2) aggiungere campo cancellazione logica");
           System.out.println("3) contare il numero dei campi");
           System.out.println("4) lunghezza di ogni campo");
           System.out.println("5) dimensione fissa");
           System.out.println("6) aggiungere record in coda");
           System.out.println("7) mostra campi");
           System.out.println("8) ricerca record ");
           System.out.println("9) modifica record");
           System.out.println("10) cancella logicamente record");
           System.out.println("0) uscire");
           opzione = Integer.parseInt(scanner.nextLine());
           switch (opzione){
               case 1:
                   elaborazioneCSV.aggiungiMioCampo(file);
                   break;
               case 2:
                   elaborazioneCSV.aggiungiCampoCancellazioneLogica(file);
                   break;
               case 3:
                   System.out.println("Numero di campi: " + elaborazioneCSV.numeroDiCampi(file));
                   break;
               case 4:
                   String[] campi = elaborazioneCSV.getCampi(file);
                   int[] lunghezzaCampi = elaborazioneCSV.lunghezzaCampi(file);
                   String string = "";
                   for (int i=0; i<campi.length; i++){
                       string += campi[i] + ": " + lunghezzaCampi[i];
                       if (i< campi.length-1){
                           string += ", ";
                       }
                   }
                   System.out.println(string);
                   break;
               case 5:
                   elaborazioneCSV.dimensioneFissa(file);
                   break;
               case 6:
                   elaborazioneCSV.aggiungiRecord(recordCasuale(elaborazioneCSV.numeroDiCampi(file)), file);
                   break;
               case 7:
                   //Da implementare
                   break;
               case 8:
                   System.out.println("Di quale campo?");
                   String campo = scanner.nextLine();
                   System.out.println("A quale riga?");
                   int riga = Integer.parseInt(scanner.nextLine());
                   System.out.println("Contenuto: " + elaborazioneCSV.recodDiCampo(campo, riga, file));
                   break;
               case 9:
                   System.out.println("Di quale campo?");
                   String campoDaModificare = scanner.nextLine();
                   System.out.println("A quale riga?");
                   int rigaDaModificare = Integer.parseInt(scanner.nextLine());
                   System.out.println("Nuovo record");
                   String nuovoRecord = scanner.nextLine();
                   elaborazioneCSV.modificaRecord(campoDaModificare, rigaDaModificare, nuovoRecord, file);
                   break;
               case 10:
                   System.out.println("A quale riga?");
                   int rigaDaCancellare = Integer.parseInt(scanner.nextLine());
                   break;
           }
        }while (opzione !=0);
    }
}
