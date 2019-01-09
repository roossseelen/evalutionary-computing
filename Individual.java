package mypackage;

import java.lang.Math;
import java.util.Random;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;

import org.vu.contest.ContestEvaluation;

public class Individual {
    static final int numbergenes = 10;
    static final int UpperBound = 5;
    static final int LowerBound = -5;
    public double[] genes;
    public double fitness;
    public ContestEvaluation evaluation_;
    static public Random RANDOM = new Random();
    //public boolean isEvaluated;

    public double id;

    //only for initializing first random genes.
    public double getRandomDoubleBetweenDimensions() {
        //Random r = rdn;
        double x =  LowerBound + (UpperBound - LowerBound) * RANDOM.nextDouble();
        //double x = (Math.random() * (10)) - 5;
        //if (x > 5.0) {
        //   x = 5.0;
        //} else if (x < -5.0) {
        //    x = -5.0;
        //}

        return x;
    }


    public Individual(boolean initialise, ContestEvaluation evaluation, double id) {
        genes = new double[10];
        this.evaluation_ = evaluation;
        this.id = id;
        System.out.println("id = " + this.id);
        //this.isEvaluated = false;
        if (initialise) {
            for (int i = 0; i < 10; i++) {
                double gene = getRandomDoubleBetweenDimensions();
                genes[i] = gene;
                System.out.println(gene);

            }
            //this.fitness = (double) evaluation_.evaluate(this.genes);
            calculateFitness();
            System.out.println("fitness = " + this.fitness);
            //calculateFitness(evaluation_);
            //System.out.println(fitness + '\n');
            System.out.println('\n');
        }
    }

    public Individual(double[] array, ContestEvaluation evaluation, double id){
        genes = new double[10];
        for(int i=0; i<genes.length; i++){
            genes[i] = array[i];
        }
        this.evaluation_ = evaluation;
        this.id = id;
        calculateFitness();
    }

    public double calculateFitness( ) {
        fitness = (double) evaluation_.evaluate(genes);

        return 1;
    }

    public double getFitness() {
        return this.fitness;
    }

    public double getId(){
        return this.id;
    }

    public void setGenes(double[] genes) {
        this.genes = genes;
        calculateFitness();
    }

//    public void uniformMutation(){
//        for (int i = 0; i< genes.length; i++){
//            double random = Math.random();
//            if(random < mutationRate){
//                genes[i] = getRandomDoubleBetweenDimensions();
//            }
//        }
//    }

}

