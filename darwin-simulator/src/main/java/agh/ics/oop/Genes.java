package agh.ics.oop;


public class Genes {
    private int size = 32;
    private int[] genes = new int[size];
    private int minGene = 0;
    private int maxGene = 7;

    // new animal on game start
    public Genes() {
        randomGenes();
    }

    // new animal from reproduction
    public Genes(int[] p1, int[] p2, int e1, int e2) {

        int energyTotal = e1 + e2;
        int p1Genes = (int) Math.round((double)e1 / (double)energyTotal * size);
        int p2Genes = size - p1Genes;

        int side = (int) Math.round(Math.random());
        int currGenesSize = 0;
        // genotype left from parent1 , right from parent2
        if (side == 1) {
            for (int i = 0; i < p1Genes; i++) {
                // removing reference
                genes[currGenesSize] = p1[i] + 0;
                currGenesSize += 1;
            }
            for (int i = 0; i < p2Genes; i++) {
                genes[currGenesSize] = p2[size - i-1] + 0;
                currGenesSize += 1;
            }
        }
        else {
            for (int i = 0; i < p2Genes; i++) {
                // removing reference
                genes[currGenesSize] = p2[i] + 0;
                currGenesSize += 1;
            }
            for (int i = 0; i < p1Genes; i++) {
                genes[currGenesSize] = p1[size - i-1] + 0;
                currGenesSize += 1;
            }
        }
    }

    private void randomGenes() {
        for (int i = 0; i < size; i++) {
            genes[i] = minGene + (int) (Math.random() * (maxGene + 1));
        }
    }

    public int randomRotate() {
        return genes[(int) (Math.random() * size)];
    }

    public int[] getGenes() {
        return genes;
    }

}
