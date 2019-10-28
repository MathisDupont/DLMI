import java.util.Arrays;
import java.util.Random;
import mnisttools.MnistReader;

public class ImageOnlinePerceptron {

    /* Les donnees */
	public static String path="/home/mathis/IdeaProjects/ReseauDeNeurones/";
    public static String labelDB=path+"train-labels-idx1-ubyte.gz";
    public static String imageDB=path+"train-images-idx3-ubyte.gz";

    /* Parametres */
    // Na exemples pour l'ensemble d'apprentissage
    public static final int Na = 1000; 
    // Nv exemples pour l'ensemble d'évaluation
    public static final int Nv = 1000; 
    // Nombre d'epoque max
    public final static int EPOCHMAX=40;
    // Classe positive (le reste sera considere comme des ex. negatifs):
    public static int  classe = 5 ;

    // Générateur de nombres aléatoires
    public static int seed = 1234;
    public static Random GenRdm = new Random(seed);


    /*
    *  BinariserImage : 
    *      image: une image int à deux dimensions (extraite de MNIST)
    *      seuil: parametre pour la binarisation
    *
    *  on binarise l'image à l'aide du seuil indiqué
    *
    */
    public static int[][] BinariserImage(int[][] image, int seuil) {
    	int color;

		//int[][] binarized = new int[][](image.getWidth(), image.getHeight(), image.getType());
		
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {

				color = image[i][j];
				if (color > seuil) {
					image[i][j] = 255;
				} else {
					image[i][j] = 0;
				}
			}
		}

		return image;

    }

    /*
    *  ConvertImage : 
    *      image: une image int binarisée à deux dimensions
    *
    *  1. on convertit l'image en deux dimension dx X dy, en un tableau unidimensionnel de tail dx.dy
    *  2. on rajoute un élément en première position du tableau qui sera à 1
    *  La taille finale renvoyée sera dx.dy + 1
    *
    */
    public static float[] ConvertImage(int[][] image) {
    	float[] res = new float[image.length * image[0].length +1];
		int count = 1;
		res[0]=1;
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				res[count] = image[i][j];
				count += 1;
			}
		}

		return res;
    }

    /*
    *  InitialiseW :
    *      sizeW : la taille du vecteur de poids
    *      alpha : facteur à rajouter devant le nombre aléatoire
    *
    *  le vecteur de poids est crée et initialisé à l'aide d'un générateur
    *  de nombres aléatoires.
    */
    public static float[] InitialiseW(int sizeW, float alpha) {
            float[] wInit = new float [sizeW];
            
            Random rand = new Random();
            for(int i=0; i<sizeW; i++) {
            	wInit[i] = alpha*((float)rand.nextInt(101))/100;
            }
            return wInit;
    }

    
    public static int gradient(float[][] data, int[] pds, float[] w, int DIM) {
        int err = 0;
        for (int i = 0; i < data.length; i++) {
            int somme = 0;
            for(int j = 1; j < DIM; j++) {
                somme += data[i][j]*w[j];
            }
            somme += w[0];
            somme *= pds[i];
            if(somme <= 0) {
                err++;
                w[0] += pds[i];
                for (int j = 1; j < DIM; j++) {
                    w[j] += pds[i]*data[i][j];
                }
            }
        }
        return err;
    }
    public static int gradient1(float[][] dat, float[] w, int[] pds , int DIM) {
        int err = 0;
        for (int i = 0; i < dat.length; i++) {
            int somme = 0;
            for(int j = 1; j < DIM; j++) {
                somme += dat[i][j]*w[j];
            }
            somme += w[0];
            somme *= pds[i];
            if(somme <= 0) {
                err++;
                //w[0] += pds[i];
                //for (int j = 1; j < DIM; j++) {
                //    w[j] += pds[i]*dat[i][j];
                //}
            }
        }
        return err;
    }
   
    public static int epoque_modif(float[][] dat, int[] pds, float[] w, int tmax, int DIM) {
        int nbErreurs = 0;
        int erreur = 2;
        while (erreur !=0 && nbErreurs < tmax) {
            erreur = gradient(dat, pds, w, DIM);
            nbErreurs++;
        }
        return erreur;
    }
    
    
    

    public static void main(String[] args) {
        System.out.println("# Load the database !");
        /* Lecteur d'image */ 
        MnistReader db = new MnistReader(labelDB, imageDB);
        int idx = 1; // une variable pour stocker l'index
        // Attention la premiere valeur est 1.
        int [][] image = db.getImage(idx); /// On recupere la premiere l'image numero idx
        int D=image.length* image[0].length;
        
        float[][] trainData = new float[Na][D+1];
        float[][] validData = new float[Nv][D+1];
        for(int i=0; i<Na; i++) {
        	//int img[][] = db.getImage(i);
        	trainData[i]=ConvertImage(BinariserImage(db.getImage(i+1), 255/2));
        }
        for(int i=0; i<Nv; i++) {
        	//int img[][] = db.getImage(i);
        	validData[i]=ConvertImage(BinariserImage(db.getImage(Na+i+1), 255/2));
        }

        int[] trainRefs = new int[Na];
        int[] validRefs = new int[Nv];
        for(int i=0; i<Na; i++) {
        	if(db.getLabel(i+1)==classe) {
        		trainRefs[i]=1;
        	}
        	else {
        		trainRefs[i]=-1;
        	}
        }
        for(int i=0; i<Nv; i++) {
        	if(db.getLabel(Na + i + 1)==classe) {
        		validRefs[i]=1;
        	}
        	else {
        		validRefs[i]=-1;
        	}
        }
        int nbError=1;
        int it=0;;
        float alpha = (float) 0.1;
        float w[]=InitialiseW(D,alpha);
        while(nbError>0 & it<50) {
        	nbError = epoque_modif(trainData, trainRefs, w, 1, D);
        	System.out.println("Nombre d'erreur : " + nbError);
        	System.out.print("w = ");
            for(int i=0; i<D; i++) {
            	System.out.print("[" + w[i] + "] ");
            }
            System.out.println("");
        	//System.out.println("Qu'est-ce tu fais frérot");
        	it++;
        }
        
        
        
        
        int nbError_1 = gradient1(validData, w, validRefs, D);
        System.out.println("");
        System.out.println(nbError_1);

        
        
        
        
        
        
        /*float[] wZero = new float[D]; // parametres du modèle
        wZero[0] = (float) 3.5;
        wZero[1] = (float) -1.5;
        wZero[2] = (float) 10;
        
        
        
       // System.out.println("wZero= "+Arrays.toString(wZero));
        int nbError = epoque(trainData, wZero, trainRefs, (float) 1, D);
        System.out.println(nbError);
        */
        
        /* Creation des donnees */
        System.out.println("# Build train for digit "+ classe);
        /* Tableau où stocker les données */
        // TODO
    }
} 
