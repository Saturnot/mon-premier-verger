import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * MonPremierVerger
 */
public class MonPremierVerger {
    private int[] aVerger = new int[4];

    private int nbCorbeau;

    private boolean aVictoire;
    private boolean aEndGame =  false;


    public static void main(String[] args) {
        int runs = 1000000;
        float[][] tab = new float[2][10];
        for(int i = 1; i < 10; i++){
            System.out.println("-----  Sans optimisation, "+ i +" cases de corbeau -----");
            tab[0][i] = runs(runs, false, i);
            System.out.println("-----  Avec optimisation, "+ i +" cases de corbeau -----");
            tab[1][i] = runs(runs, true, i);
        }
        saveToCSV(tab[0], "sansOptimisation.csv");
        saveToCSV(tab[1], "avecOptimisation.csv");


    }
    private static float runs(final int runs, final boolean optiPanier, final int nbCorbeau){
        int[] nbTour = new int[runs];
        int nbVictoire = 0;
        for(int i = 0; i < runs; i++){
            MonPremierVerger verger = new MonPremierVerger();
            nbTour[i] = verger.game(optiPanier, nbCorbeau);
            if(verger.aVictoire){
                nbVictoire++;
            }
        }
        float pourcentageVictoire = (float)nbVictoire/runs*100;
        System.out.println("Victoire : " + nbVictoire + " / " + runs);
        System.out.println("Pourcentage de victoire : " + pourcentageVictoire + "%");
        System.out.println("Moyenne de tour : " + moyenneTab(nbTour));
        return pourcentageVictoire;
    }
    private int getNbFruit(final int fruit){
        // 0 : pomme rouge
        // 1 : pomme verte
        // 2 : poire
        // 3 : prune
        return this.aVerger[fruit];
    }
    private void setNbFruit(final int fruit, final int nb){
        // 0 : pomme rouge
        // 1 : pomme verte
        // 2 : poire
        // 3 : prune
        this.aVerger[fruit] = nb;
    }
    private int game(final boolean optiPanier, final int nbCorbeau){
        // set nb fruit
        setNbFruit(0, 4);
        setNbFruit(1, 4);
        setNbFruit(2, 4);
        setNbFruit(3, 4);

        // set nb corbeau
        this.nbCorbeau = nbCorbeau;

        int tour = 0;
        // set end game
        while(!aEndGame){
            // System.out.println("-----  Tour : " + tour + " -----");
            aEndGame = !tourDeJeu(optiPanier);
            tour++;
        }
        if(this.aVictoire){
            // System.out.println("Victoire");
        }else{
            // System.out.println("Defaite");
        }
        return tour;
    }
    private boolean tourDeJeu(final boolean optiPanier){
        boolean doWeContinue = true;
        int de = lancerDe();
        // System.out.println("De : " + de);
        switch (de) {
            case 0:
                if(getNbFruit(0) > 0){
                    // System.out.println("Pomme rouge");
                    setNbFruit(0, getNbFruit(0) - 1);
                }else{
                    // System.out.println("Plus de pomme rouge");
                }
                break;
            case 1:
                if(getNbFruit(1) > 0){
                    // System.out.println("Pomme verte");
                    setNbFruit(1, getNbFruit(1) - 1);
                }else{
                    // System.out.println("Plus de pomme verte");
                }
                break;
            case 2:
                if(getNbFruit(2) > 0){
                    // System.out.println("Poire");
                    setNbFruit(2, getNbFruit(2) - 1);
                }else{
                    // System.out.println("Plus de poire");
                }
                break;
            case 3:
                if(getNbFruit(3) > 0){
                    // System.out.println("Prune");
                    setNbFruit(3, getNbFruit(3) - 1);
                }else{
                    // System.out.println("Plus de prune");
                }
                break;
            case 4:
                // System.out.println("Corbeau");
                this.nbCorbeau--;
                if(this.nbCorbeau == 0){
                    // System.out.println("perdu");
                    doWeContinue = false;
                    this.aVictoire = false;
                }
                break;
            case 5:
                // System.out.println("Panier");
                if(optiPanier){
                    panierNormal();
                }else{
                    panierHasard();
                }
                break;
            default:
                break;
        }
        // afficheVerger();
        // est-ce que le panier est vide ?
        if(getNbFruit(0) == 0 && getNbFruit(1) == 0 && getNbFruit(2) == 0 && getNbFruit(3) == 0){
            // System.out.println("gagne");
            doWeContinue = false;
            this.aVictoire = true;
        }
        return doWeContinue;
    }
    private static int lancerDe() {
        // 0 : pomme rouge
        // 1 : pomme verte
        // 2 : poire
        // 3 : prune
        // 4 : corbeau
        // 5 : panier

        return (int) (Math.random() * 6);
    }
    private void afficheVerger(){
        System.out.println("Pomme rouge : " + getNbFruit(0));
        System.out.println("Pomme verte : " + getNbFruit(1));
        System.out.println("Poire : " + getNbFruit(2));
        System.out.println("Prune : " + getNbFruit(3));
        System.out.println(" ");
        System.out.println("Corbeau : " + this.nbCorbeau);
    }
    private int maxNumberOfFruit(){
        int index = 0;
        int max = getNbFruit(index);
        for(int i = 1; i < 4; i++){
            if(getNbFruit(i) > max){
                max = getNbFruit(i);
                index = i;
            }
        }
        return index;
    }
    private static float moyenneTab(final int[] tab){
        float moyenne = 0;
        for(int i = 0; i < tab.length; i++){
            moyenne += tab[i];
        }
        return moyenne/tab.length;
    }
    private void panierNormal(){
        int index = maxNumberOfFruit();
        if(getNbFruit(index) > 0){
            // System.out.println("fruit : " + index);
            setNbFruit(index, getNbFruit(index)-1);
        }else{
            // System.out.println("Plus de fruit " + index + " dans le verger");
        }
    }
    private void panierHasard(){
        int index = (int) (Math.random() * 4);
        while(getNbFruit(index) == 0){
            index = (int) (Math.random() * 4);
        }
        if(getNbFruit(index) > 0){
            // System.out.println("fruit : " + index);
            setNbFruit(index, getNbFruit(index)-1);
        }else{
            // System.out.println("Plus de fruit " + index + " dans le verger");
        }
    }
    private static void saveToCSV(float[] data, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            for (float value : data) {
                writer.write(String.valueOf(value));
                writer.newLine();
            }
            System.out.println("Données enregistrées dans " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}