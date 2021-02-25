package eac1.ex1;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
/**
 *
 * @author aleix
 */
public class Exercici1 {
    
    public static void main(String[] args) throws IOException{
        
        //Variables on s'emmagatzemen els parametres entrats per consola.
        File carpeta;
        String tipusCas;
        String primeraOpcio;
        String midaFitxers;
        
        //Variables adicionals.
        int comprovarNumMida;
         
        //Assignem valors
        carpeta = new File(args[0]);
        tipusCas = args[1];
        primeraOpcio = args[2];
            
        //Creem els filtres
        
        //Lambda expression: Filtre per comprovar arxius no ocults amb permís d'escriptura.
        FileFilter filtreOnlyFiles = (File arxiu)-> {
            return(arxiu.isFile() && !arxiu.isHidden() && arxiu.canWrite());
        };
        
        //Lambda expression: Filtre per comprovar arxius i directoris no ocults amb permís d'escriptura
        FileFilter filtreAll = (File arxiu) -> {
            return(!arxiu.isHidden() && arxiu.canWrite());
        };  
        
        
        //Comencem el control d'errors.
        
        //Si els arguments introduïts són més de quatre o menys de tres.
        if(args.length<3 || args.length>4){
            errorExit("Numero d'arguments incorrecte");
        }
        
        //Si la direcció introduïda no es un directori.
        if(!carpeta.isDirectory()){
            errorExit("La direcció introduida no correspon amb una carpeta del sistema de fitxers locals.");
        }
        
        //Si la opció introduïda no es ni una E ni una L.
        if(!tipusCas.toUpperCase().equalsIgnoreCase("E") && !tipusCas.toUpperCase().equalsIgnoreCase("L")){
            errorExit("El segon parametre no correspon amb una de les opcions disponibles.");
            
        }else{
            //Si la opció introduïda és E.
            if(tipusCas.toUpperCase().equalsIgnoreCase("E")){
                midaFitxers = args[3];
                //Comprovem que el tercer parametre és un dels simbols.
                if(!primeraOpcio.equals("<") && !primeraOpcio.equals(">")){
                    errorExit("El tercer paràmetre ha de ser o > o <.");
                }else{
                    
                    //Comprovem que la mida introduïda és un numero enter.
                    try{
                        comprovarNumMida = Integer.parseInt(midaFitxers);
                    }catch(NumberFormatException ex){
                        errorExit("La mida introduïda ha de ser un número enter.");
                    }
                    
                    //Esborrem arxius fent servir el filtre corresponent.
                    esborrar(carpeta.listFiles(filtreOnlyFiles), primeraOpcio, midaFitxers);
                }

             //Si la opció introduïda es L.  
            }else if(tipusCas.toUpperCase().equalsIgnoreCase("L")){
                
                //Comprovem que el tercer parametre és D o N.
                if(!primeraOpcio.toUpperCase().equalsIgnoreCase("D") && !primeraOpcio.toUpperCase().equalsIgnoreCase("N")){
                    errorExit("El tercer paràmetre ha de ser o D o N.");
                    
                }else{
                    
                    //Si la opció és D.
                    if(primeraOpcio.toUpperCase().equalsIgnoreCase("D")){
                        
                        //Aplicarem el filtre que retorna tant carpetes com fitxers.
                        mostrarFitxers(carpeta.listFiles(filtreAll));
                        
                    //Si la opció es N.
                    }else{
                        
                        //Aplicarem el filtre que retorna només fitxers.
                        mostrarFitxers(carpeta.listFiles(filtreOnlyFiles));
                    }
                }
            }
        }    
    }
    
    
    //Mètode per esborrar els fitxers.
    private static void esborrar(File[] arxius, String menorMajor, String mida){
        
        //Variable per comptar els fitxers esborrats.
        int comptaFitxers = 0;
        
        System.out.println("Comença el procés d'esborrat:");
        
        //Comprovem que la carpeta no està buida.
        if(arxius.length!=0){
            
            //Si el parametre introduït és >
            if(menorMajor.equals(">")){
                
                //Recorrem els fitxers
                for(int i = 0; i<arxius.length; i++){
                    
                    //Comprovem la mida dels fitxers
                    if(arxius[i].length()>Integer.parseInt(mida)){
                        
                       System.out.println("Fitxer: "+arxius[i]+ " esborrat amb èxit.");
                       
                       //Esborrem el fitxer
                       arxius[i].delete();
                       
                       comptaFitxers++;
                    }
                }
                
            //Si el parametre introduït és <    
            }else{
                 
                //Recorrem els fitxers
                for(int i = 0; i<arxius.length; i++){
                    
                    //Comprovem la mida dels fitxers
                    if(arxius[i].length()<Integer.parseInt(mida)){
                        
                       System.out.println("Fitxer: "+arxius[i]+ " esborrat amb èxit.");
                       
                       //Esborrem el fitxer
                       arxius[i].delete();
                       
                       comptaFitxers++;
                       
                    }
                }
            }
            System.out.println("");
            
            //Imprimim el numero de fitxers esborrats.
            System.out.println(comptaFitxers+" fitxers esborrats.");
        
        //Si la carpeta està buida.
        }else{
            errorExit("No hi ha fitxers per esborrar.");
        }
    }
    
    
    //Mostra els fitxers
    private static void mostrarFitxers(File[] fitxers){
        
        System.out.println("Totals "+ fitxers.length +" no ocults amb permís d'escriptura.");
        
        //Recorrem els fitxers.
        for(int i = 0; i<fitxers.length; i++){
            
            //Si és un fitxer.
            if(fitxers[i].isFile()){
                
                //Imprimim dades.
                System.out.println("\t>> "+fitxers[i]+" "+(fitxers[i].length()/1024)+ " kb.");
            
            //Si es una carpeta.
            }else if(fitxers[i].isDirectory()){
                
                //Imprimim dades.
                System.out.println("\t> "+fitxers[i]);
            }
        }
    }
    
    //Mètode per mostrar l'error.
    private static void errorExit(String missatge) {
        System.err.println(missatge);
        System.exit(2);
    } 
}
