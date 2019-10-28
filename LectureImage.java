/*import mnisttools.MnistReader;

public class LectureImage {




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





    public static void main(String[] args) {
        String path="/home/tp-home007/mdupon4/PUUUTE/";
        String labelDB=path+"train-labels.idx1-ubyte";
        String imageDB=path+"train-images.idx3-ubyte";
        // Creation de la base de donnees
        MnistReader db = new MnistReader(labelDB, imageDB);
        // Acces a la premiere image
        int idx = 1; // une variable pour stocker l'index
        // Attention la premiere valeur est 1.
        int [][] image = db.getImage(idx); /// On recupere la premiere l'image numero idx
        // Son etiquette ou label
        int label = db.getLabel(idx);
        // Affichage du label
        System.out.print("Le label est "+ label+"\n");
        // note: le caractère \n est le 'retour charriot' (retour a la ligne).
        // Affichage du nombre total d'image
        System.out.print("Le total est "+ db.getTotalImages()+"\n");
         A vous de jouer pour la suite
        System.out.print("La taille de l'image est " + image.length * image[0].length + "\n");
        int max=0;
        int min=0;
        int Cmax[] = new int[image.length];
        int Cmin[] = new int[image[0].length];
        for (int k:Cmin) {
            k=255;
        }
        for(int i=0; i<image.length; i++) {
            max=0;
            min=255;
            for(int j=0; j<image[0].length; j++) {
                if(image[i][j]>max) {
                    max=image[i][j];
                }
                if(image[i][j]<min) {
                    min=image[i][j];
                }
                if(Cmax[j]<image[i][j]) {
                    Cmax[j]=image[i][j];
                }
                if(Cmin[j]>image[i][j]) {
                    Cmin[j]=image[i][j];
                }
                System.out.print(" "+ image[i][j]);

            }
            System.out.print(" Le max est : " + max);
            System.out.print(" Le min est : " + min);
            System.out.print("\n");
        }
        for(int i:Cmax) {
            System.out.print("Max:" + i +" ");
        }
        System.out.print("\n");

        for(int i:Cmin) {
            System.out.print("Min:" + i + " ");
        }
    }

}
*/