

import java.util.Random;
import java.lang.Math;

public class perceptronMulti {
	
	//Génère des nbr aléatoires
	public static int seed = 1234;
    public static Random GenRdm = new Random();
    public static float eta = (float) 0.1;

	
	public static float[] OneHot (float x) {
		
		float[] tab = new float [10];
		for(int i =0; i<10; i++) {
			if(x==i) {
				tab[i]=1;
			}
			else {
				tab[i]=0;
			}
		}
		return tab;
	}
	
	public static float produitscalaire (float[] x, float[] w) {
		float result = 0;
		for(int i=0; i<x.length; i++) {
			result += x[i]*w[i+1];
		}
		result += w[0];
		
		
		return result;
	}
	
	public static double[] InfPerceptron(float[] donnee, float[][] w) {
		double nbr=0;
		double[] proba = new double[w.length];
		for(int j=0; j<donnee.length;j++) {
			double num=Math.exp(produitscalaire(donnee, w[j]));
			nbr += num;
			
			proba[j]=num;
			
		}

		for (int i = 0;i<proba.length;i++) {
			proba[i]/=nbr;
		}
		
		
		return proba;
	}

	public static int indiceMaxProba(float[] donnee, float[][] w){
		double[] y = InfPerceptron(donnee, w);
		int idx=0;
		double max = -1;
		for(int i=0; i<y.length; i++){
			if(y[i]>max){
				max=y[i];
				idx=i;
			}
		}
		return idx;
	}
	
	public static float[][] InitialiseW(int sizeW, float alpha) {
        float[][] wInit = new float [10][sizeW];
        
        Random rand = new Random();
        for (int j=0; j<10; j++) {
			for (int i = 0; i < sizeW; i++) {
				wInit[j][i] = alpha * ((float) rand.nextInt(101)) / 100;
			}
		}
        return wInit;
	}
	
	
	
    public static void MAJPerceptron(float[][] w, float[] donnee, float refs){
    	double[] y= InfPerceptron(donnee,w);
    	float[] p= OneHot(refs);
        for(int l=0; l<w.length; l++) {
        	for(int i = 0; i<w[0].length; i++) {
            	w[l][i] -= donnee[i]*eta*(y[l]-p[l]) ;
            }
        	w[l][0] -= eta*(y[l]-p[l]);
        }

    }



	public static int epoque( float [][] w, float[][] donnee, int[] ref){
		/*
		*Met à jour le perceptron à chaque itération soit D+1 fois (D étant la dimension de ref)
		*Contrôle graçe à indiceMaProba() le nombre d'erreur
		*Renvoie le nombre d'itération (int)
		*
		 */
		int nbError=-1;
		int nbEpoque=0;
		int it=0;
		while(nbError!=0 & it<500){
			nbError=0;
			//Mise à jour du perceptron
			for(int i=0; i<donnee.length; i++){				/*
			 	** i va de 0 à 1000 (les 1000 images d'entraînement)
			 	* donnee[i] contient tout les pixels d'une image donnée.
			 	* ref[i] contient le label de chaque image (valeur comprise entre 1 et 9 inclu)
			 	*/
				MAJPerceptron(w, donnee[i], ref[i]);
				/*
				Si le maximum de InfPereptron n'est pas égal à ref[i], cela veut dire que il y a une erreur
				 */
				int idx=indiceMaxProba(donnee[i],w); //Variable qui stock l'indice du max de InfPerceptron
				if(idx!=ref[i]){
					nbError++;
				}

			}
			System.out.println("NbError = " + nbError);
			it++;
		}
	return it;
	}
	public static int epoqueValid(float [][] w, float[][] donnee, int[] ref ) {
		/*
		Retour juste le nombre le nombre d'erreur sans modifier el perceptron!
		Sert pour valider le perceptron.
		 */
		int nbError=0;
		for (int i = 0; i < donnee.length; i++) {
			/*
			Si le maximum de InfPereptron n'est pas égal à ref[i], cela veut dire que il y a une erreur
			 */
			int idx = indiceMaxProba(donnee[i], w); //Variable qui stock l'indice du max de InfPerceptron
			if (idx != ref[i]) {
				nbError++;
			}
		}
		return nbError;
	}

	public static void main(String[] args) {

		
		
		
		
		System.out.println("----------------------------");
		System.out.println("Test fct OneHot");
		System.out.println();
		float[] tab = OneHot(4);
		for(int i=0; i<tab.length; i++) {
			System.out.println(tab[i]);
		}
		System.out.println("----------------------------");
		System.out.println("Test fct InfPerceptron");
		System.out.println();
		System.out.println();
		float[][] w = new float[11][12];
		float[] x = new float [11];
		double[] test1 = InfPerceptron(x,w);
		float sum = 0;
		for(int i =0; i<x.length; i++) {
			//System.out.println(test1[i]);
			sum += test1[i];
		}
		System.out.print("La somme de toutes les proba est : " + sum);

		System.out.println("----------------------------");
		System.out.println("Test proédure MAJPerceptron");

	}

}
