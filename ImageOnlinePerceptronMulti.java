
import mnisttools.MnistReader;

import java.util.Random;

public class ImageOnlinePerceptronMulti {

    /* Les donnees */
    public static String path="/home/mathis/IdeaProjects/ReseauDeNeurones/";
    public static String labelDB=path+"train-labels-idx1-ubyte.gz";
    public static String imageDB=path+"train-images-idx3-ubyte.gz";

    /* Parametres */
    // Na exemples pour l'ensemble d'apprentissage
    public static final int Na = 1000;
    // Nv exemples pour l'ensemble d'évaluation
    public static final int Nv = 1000;
    // Générateur de nombres aléatoires
    public static int seed = 1234;
    public static Random GenRdm = new Random(seed);



    public static void main(String[] args) {


        System.out.println("# Load the database !");
        /* Lecteur d'image */
        MnistReader db = new MnistReader(labelDB, imageDB);
        int idx = 1; // une variable pour stocker l'index
        // Attention la premiere valeur est 1.
        int[][] image = db.getImage(idx); /// On recupere la premiere l'image numero idx
        int D = image.length * image[0].length;

        float[][] trainData = new float[Na][D + 1];
        float[][] validData = new float[Nv][D + 1];
        for (int i = 0; i < Na; i++) {
            trainData[i] = ImageOnlinePerceptron.ConvertImage(ImageOnlinePerceptron.BinariserImage(db.getImage(i + 1), 255 / 2));
        }
        for (int i = 0; i < Nv; i++) {
            //int img[][] = db.getImage(i);
            validData[i] = ImageOnlinePerceptron.ConvertImage(ImageOnlinePerceptron.BinariserImage(db.getImage(Na+i + 1), 255 / 2));
        }

        int[] trainRefs = new int[Na];
        int[] validRefs = new int[Nv];
        for (int i = 0; i < Na; i++) {
            trainRefs[i]=db.getLabel((i+1));
            System.out.println(trainRefs[i]);
        }
        for (int i = 0; i < Nv; i++) {
            validRefs[i]=db.getLabel(Na+i+1);
        }
        float alpha = (float) 0.1;
        float [][] w = perceptronMulti.InitialiseW(D, alpha);

        //On met à jour le perceptron avec epoque(w, trainData, trainRefs)
        int nbEpoque = perceptronMulti.epoque(w, trainData, trainRefs);
        System.out.print(nbEpoque);

        //On test le nouveau w avec validData, validRefs

        int nbErrorValidData = perceptronMulti.epoqueValid(w, validData, validRefs);
        System.out.print(nbErrorValidData);

    }
}
