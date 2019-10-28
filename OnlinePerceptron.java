import java.rmi.NotBoundException;
import java.util.Arrays;
import java.util.Random;

public class OnlinePerceptron  {
    public static final int DIM = 3; // dimension de l'espace de representation
    public static float[] w = new float[DIM]; // parametres du modèle
    public static float[][] data = { // les observations
            {1,0,0}, {1,0,1} , {1,1,0},
            {1,1,1}
    };
    public static int[] refs = {-1, -1, -1, 1}; // les references

    /*public static void init(float[] w){
        Random rand = new Random();
        for(int i=0; i<DIM; i++){
            w[i] = rand.nextFloat();
        }
    }*/


    public static int epoque(float[][] donnee, int[] classe, float poids[] ){
        int nbError = 0;

        for (int i=0; i<donnee.length; i++){
            float g = 0;
            for(int j=0; j<DIM; j++){
                g+=donnee[i][j]*poids[j];
            }
            if(g * classe[i]<=0){
                for(int k=0; k<DIM; k++){
                    poids[k]=poids[k]+donnee[i][k]*classe[i];
                }
                nbError++;
            }

        }
        System.out.print("nombre d'erreur = " + nbError + "\n");
        return nbError;
    }

    public static void main(String[] args) {
        // Exemple de boucle qui parcourt tous les exemples d'apprentissage
        // pour en afficher a chaque fois l'observation et la reference.
        for (int i = 0; i < data.length; i++) {
            float[] x = data[i];
            System.out.println("x= "+Arrays.toString(x)+ " / y = "+refs[i]);
        }
        w[0]=-4;
        w[1]=1;
        w[2]=1;
        //System.out.print(w[0] + "\n");
        //System.out.print(w[1] + "\n");
        //System.out.print(w[2] + "\n");
        int nbError=1;
        int it=0;
        while(nbError>0 & it<50){
            nbError = epoque(data, refs, w);
            it++;
        }
        System.out.print("\n");
        System.out.print("w = ");
        for(int i=0; i<DIM; i++){
            System.out.print("[" + w[i] +"]");
        }
    }

}