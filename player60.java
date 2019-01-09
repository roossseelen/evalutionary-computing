import mypackage.Population;
import mypackage.Individual;

import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.lang.Object;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.*;



public class player60 implements ContestSubmission
{
    Random rnd_;
    ContestEvaluation evaluation_;
    private int evaluations_limit_;
    public int evals = 0;
    public Population currentPopulation;
    public Population parents;
    public Population childs;
    public int popsize = 100;
    public int numberofparents = 100;
    public int nextgenerationsize = 20;
    public int tournamentsize = 5;
    public double alpha = 0.5;
    double LastID = 0.0;
    static public Random RANDOM = new Random();

    public player60()
    {
        rnd_ = new Random();
    }

    public void setSeed(long seed)
    {
        // Set seed of algortihms random process
        rnd_.setSeed(seed);
    }

    public void setEvaluation(ContestEvaluation evaluation)
    {
        // Set evaluation problem used in the run
        evaluation_ = evaluation;

        // Get evaluation properties
        Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
        // Property keys depend on specific evaluation
        // E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

        // Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal){
            // Do sth
        }else{
            // Do sth else
        }
    }
    public void Survivorselectiontournament(){
        System.out.println("survivorselection started. ");
        int parentsAndBabySize = popsize + nextgenerationsize;

        Population parentsAndBabyPop = new Population(parentsAndBabySize, evaluation_, false); //20 moet populationSize x 2 zijn.

        for (int i = 0; i < popsize; i++) { //10 moet populationSize zijn.
            parentsAndBabyPop.saveIndividual(currentPopulation.getIndividual(i));
        }
        for (int i = 0; i < nextgenerationsize; i++) {
            parentsAndBabyPop.saveIndividual(childs.getIndividual(i));
        }

        Population tempFittest = new Population(popsize, evaluation_, false); //10 is populationSize

    }

    public void SurvivorSelection(){
        System.out.println("survivorselection started. ");
        int parentsAndBabySize = popsize + nextgenerationsize;

        Population parentsAndBabyPop = new Population(parentsAndBabySize, evaluation_, false); //20 moet populationSize x 2 zijn.

        for (int i = 0; i < popsize; i++) { //10 moet populationSize zijn.
            parentsAndBabyPop.saveIndividual(currentPopulation.getIndividual(i));
        }
        for (int i = 0; i < nextgenerationsize; i++) {
            parentsAndBabyPop.saveIndividual(childs.getIndividual(i));
        }

        Population tempFittest = new Population(popsize, evaluation_, false); //10 is populationSize

        for( int i =0; i< parentsAndBabySize; i++){
            System.out.println("indevidual" + i + "id"+ parentsAndBabyPop.getIndividual(i).getId() + "with fitnes" + parentsAndBabyPop.getIndividual(i).getFitness());
        }
        for (int i = 0; i <popsize; i++) {

            int parent = 0;
            for (int j = 0; j < parentsAndBabyPop.size(); j++) {
                if (parentsAndBabyPop.getIndividual(parent).getFitness() <= parentsAndBabyPop.getIndividual(j).getFitness()) {
                    parent = j;
                }
            }
            Individual fittest = parentsAndBabyPop.getIndividual(parent);
            parentsAndBabyPop.removeIndiv(parent);
            tempFittest.saveIndividual(fittest);
            System.out.println("next pop indiv " + i + "id:" + tempFittest.getIndividual(i).getId() + "fitness;" + tempFittest.getIndividual(i).getFitness());
            System.out.println("size parentandbabypop: " + parentsAndBabyPop.size());
        }
        currentPopulation.clear();
        currentPopulation = tempFittest;

        for( int i =0; i< popsize; i++){
            System.out.println("indevidual" + i + "id"+ currentPopulation.getIndividual(i).getId() + "with fitnes" + currentPopulation.getIndividual(i).getFitness());
            //for( int j = 0; j < 10; j++ ){
             //   System.out.println("gene" + j +" = " + currentPopulation.getIndividual(i).genes[j]);
            //}
        }
    }
    public void nonUniformMutation() {
        System.out.println("nonuniform mutation started ");
        double mutationRate = 0.5;
        for (int child = 0; child < childs.size(); child++) {

            for (int i = 0; i < 10; i++) {
                double random = Math.random();
                System.out.println("random = " + random);
                if (random < mutationRate) {
                    double difference = RANDOM.nextGaussian();
                    System.out.println("gaussioan = " + difference);
                    double new_value = childs.getIndividual(child).genes[i] + difference;
                    new_value = Math.max(new_value, -5);
                    new_value = Math.min(new_value, 5);
                    childs.getIndividual(child).genes[i] = new_value;

                }
            }
        }
        for(int k=0; k<childs.size();k++){
            for(int g=0; g<10;g++){
                System.out.println("child = "+ k + " gene "+ g + childs.getIndividual(k).genes[g]);
            }
        }
    }

    public Individual getFittest(Population pop, int size){
        //get fittest
        int parent = 0;
        for(int i = 0; i < size; i++) {
            if(pop.getIndividual(parent).getFitness() <= pop.getIndividual(i).getFitness()) {
                parent = i;
            }
        }
        Individual fittest = pop.getIndividual(parent);
        return fittest;
    }

    public double[] concentrate(double[] head, double[] tail, int crossoverPoint, int arrSize){
        double[] geneschild = new double[10];
        for(int i = 0; i <  crossoverPoint; i++){
            geneschild[i] = head[i];
        }
        for(int i = crossoverPoint; i <  10; i++){
            geneschild[i] = tail[i-crossoverPoint];
        }
        return geneschild;
    }

    public void SimpleArithmeticRecombination(Individual parent1, Individual parent2){
        System.out.println("SimpleArithmeticRecombination started ");
        int k = anyRandomIntRange(1,9);

        double[] geneschild1 = new double[10];
        double[] geneschild2 = new double[10];

        for(int i = 0; i< k; i++){
            geneschild1[i] = parent1.genes[i];
            geneschild2[i] = parent2.genes[i];
            System.out.println("gene child 1 " + i + " = " + geneschild1[i]);
        }

        for(int i = k; i<10 ; i++){
            double avg = alpha*parent1.genes[i] + (1-alpha)*parent2.genes[i];
            geneschild1[i] = avg;
            geneschild2[i] = avg;
        }
        Individual child1 = new Individual(geneschild1, evaluation_, LastID);
        LastID+= 0.0001;
        evals++;
        childs.saveIndividual(child1);
        System.out.println("fitnes  " + " = " + child1.getFitness());
        Individual child2 = new Individual(geneschild2, evaluation_, LastID);
        LastID+= 0.0001;
        evals++;
        childs.saveIndividual(child2);
        System.out.println("fitnes  " + " = " + child2.getFitness());
    }

    public void SingleArithmeticRecombination2(Individual parent1, Individual parent2){
        System.out.println("SingleArithmeticRecombination2");
        int k = anyRandomIntRange(1,9);
        int l = anyRandomIntRange(1,9);

        double[] geneschild1 = new double[10];
        double[] geneschild2 = new double[10];

        for(int i = 0; i< k; i++){
            geneschild1[i] = parent1.genes[i];
            System.out.println("gene child 1 " + i + " = " + geneschild1[i]);
        }

        for(int i = 0; i< l; i++){
            geneschild2[i] = parent2.genes[i];
        }


        double avgk, avgl;
        avgk = alpha*parent1.genes[k] + (1-alpha)*parent2.genes[k];

        avgl = alpha*parent1.genes[l] + (1-alpha)*parent2.genes[l];

        geneschild1[k] = avgk;
        System.out.println("gene  " + k + " = " + geneschild1[k] + "gene parent 1: " + parent1.genes[k] + " gene parent 2: "+ parent2.genes[k]);
        geneschild2[l] = avgl;

        for(int i = k+1; i< 10; i++){
            geneschild1[i] = parent1.genes[i];
            System.out.println("gene child 1 " + i + " = " + geneschild1[i]);
        }

        for(int i = l+1; i< 10; i++){
            geneschild2[i] = parent2.genes[i];
        }

        Individual child1 = new Individual(geneschild1, evaluation_, LastID);
        LastID+= 0.0001;
        evals++;
        childs.saveIndividual(child1);
        System.out.println("fitnes  " + " = " + child1.getFitness());
        Individual child2 = new Individual(geneschild2, evaluation_, LastID);
        LastID+= 0.0001;
        evals++;
        childs.saveIndividual(child2);
        System.out.println("fitnes  " + " = " + child2.getFitness());
    }


    public void blendCrossover(Individual parent1, Individual parent2){ //book page 67
        System.out.println("blend crossover  started");
        double difference;
        double x, y;
        double range_min, range_max;

        double[] geneschild = new double[10];

        for(int i = 0; i<10; i++){
            x = Math.min(parent1.genes[i], parent2.genes[i]);

            y = Math.max(parent1.genes[i], parent2.genes[i]);
            difference = Math.abs(parent1.genes[i]-parent2.genes[i]);

            range_min = Math.max((x-(0.5*difference)), -5); // 0.5 is alfa moet een global worden

            range_max = Math.min((y+(0.5*difference)), 5);


            geneschild[i] =  range_min + (range_max - range_min) * RANDOM.nextDouble();
            System.out.println("gene " + i + " = " + geneschild[i]);
        }
        Individual child = new Individual(geneschild, evaluation_, LastID);
        LastID+= 0.0001;
        evals++;
        childs.saveIndividual(child);
        System.out.println("fitnes  " + " = " + child.getFitness());

    }

    public void onePointCrossOver(Individual parent1, Individual parent2) {// simple aritmethic recombination
        System.out.println("one point crossover started");
        int k = anyRandomIntRange(1,9);

        double[] geneschild1 = new double[10];
        double[] geneschild2 = new double[10];

        for (int i =0; i<k; i++){
            geneschild1[i] = parent1.genes[i];
            geneschild2[i] = parent2.genes[i];
        }

        for (int i = k; i<10; i++){
            geneschild1[i] = parent2.genes[i];
            geneschild2[i] = parent1.genes[i];
        }
        Individual child1 = new Individual(geneschild1, evaluation_, LastID);
        LastID+= 0.0001;
        evals++;
        childs.saveIndividual(child1);

        Individual child2 = new Individual(geneschild2, evaluation_, LastID);
        LastID+= 0.0001;
        evals++;
        childs.saveIndividual(child2);
        System.out.println("k  =  " + k);
        for ( int i = 0; i<10; i++){
            System.out.println("child 1 gene " + i + " = " + geneschild1[i]);
        }
        System.out.println("child 1 fitnes  " + child1.getFitness());
        for ( int i = 0; i<10; i++){
            System.out.println("child 2 gene " + i + " = " + geneschild2[i]);
        }
        System.out.println("child 2 fitnes  " + child2.getFitness());

    }

    public void onePointCrossOverfixed(Individual parent1, Individual parent2){
        //crossoverchance variable toevoegen, nu op 1 zetten
        //Individual parent1 = parents.getIndividual(0);
        //Individual parent2 = parents.getIndividual(1);
        System.out.println("one point crossover fixed started");

        System.out.println("id parent 1 en 2 :" + parent1.getId() + parent2.getId());
        System.out.println("id parent 1 en 2 :" + parent1.genes.length + parent2.genes.length);

        int crossoverPoint = 5; //nu fixed value kan als random nummer maken.
        double[] head1 = Arrays.copyOfRange(parent1.genes, 0, crossoverPoint); //syntax(OldArray,Startindex which is inclusive, endinde which is exclusive)
        double[] tail1 = Arrays.copyOfRange(parent1.genes, crossoverPoint, parent1.genes.length);
        double[] head2 = Arrays.copyOfRange(parent2.genes, 0, crossoverPoint);
        double[] tail2 = Arrays.copyOfRange(parent2.genes, crossoverPoint, parent2.genes.length);

        for (int i = 0; i < 5; i++) {
            System.out.println("head" + i + "=" + head1[i]);
        }
        for (int i = 0; i < 5; i++) {
            System.out.println("tail" + i + "=" + tail2[i]);
        }

        double[] geneschild1 = concentrate(head1, tail2, crossoverPoint, 10);
        double[] geneschild2 = concentrate(head2, tail1, crossoverPoint, 10);

        Individual child1 = new Individual(geneschild1, evaluation_, LastID);
        LastID+= 0.0001;
        evals++;
        Individual child2 = new Individual(geneschild2, evaluation_, LastID);
        LastID+= 0.0001;
        evals++;

        for (int i = 0; i < 10; i++) {
            System.out.println("gene" + i + "=" + geneschild1[i]);
        }

        System.out.println("fitness child1 " + "=" + child1.getFitness());
        System.out.println("id child 1" + "=" + child1.getId());

        for (int i = 0; i < 10; i++) {
            System.out.println("gene" + i + "=" + geneschild2[i]);
        }

        System.out.println("fitness child 2" + "=" + child2.getFitness());
        System.out.println("id child 2 " + "=" + child2.getId());
        if (childs.size() != nextgenerationsize) childs.saveIndividual(child1);
        if (childs.size() != nextgenerationsize) childs.saveIndividual(child2);
    }
    public void Recombination(){
        System.out.println("recombination started");
        childs = new Population(nextgenerationsize, evaluation_, false);
        while (childs.size()< nextgenerationsize){
        //for (int i =0 ; i< nextgenerationsize; i++) {;

            //int position_parent1 = RANDOM.nextInt(parents.size());
            //int position_parent2 = RANDOM.nextInt(parents.size());
            Individual parent1 = tournamentSelection(currentPopulation);
            Individual parent2 = tournamentSelection(currentPopulation);
            System.out.println(parent1.getId());
            System.out.println(parent2.getId());
            while (parent1.getId() == parent2.getId()){
                parent2 = tournamentSelection(currentPopulation);
            }System.out.println(parent2.getId());
            // hier kunnen we vershillende recombinations doen vooor de vershillende soorten functies.

            //Individual parent1 = parents.getIndividual(position_parent1);
            //Individual parent2 = parents.getIndividual(position_parent2);
            System.out.println("id parent 1 en 2 :" + parent1.getId() + parent2.getId());
            //onePointCrossOver(parent1, parent2);
            //blendCrossover(parent1, parent2);
            //SimpleArithmeticRecombination(parent1, parent2);
            SingleArithmeticRecombination2(parent1, parent2);
            }
    }

    public Individual getRandomIndividual(Population pop){
        //int randomId = (int) (Math.random() * (pop.size() - 1));//generate random number om te indexeren in pop

        int randomId = RANDOM.nextInt(pop.size());
        Individual selected1 = pop.getIndividual(randomId);
        //System.out.println("fitness van selected random id :" + selected1.getId() + "=" + selected1.getFitness());
        return selected1;
    }

    public Individual tournamentSelection(Population pop) {
        Population tournamentpop = new Population(tournamentsize, evaluation_, false); //5 needs to become variable
        System.out.println("tournamentpop size = "+ tournamentpop.size());

        while(tournamentpop.size() < tournamentsize) {
            boolean duplicate = false;
            Individual selected = getRandomIndividual(pop);
            for (int j = 0; j < tournamentpop.size(); j++) {
                if (tournamentpop.getIndividual(j).getId() == selected.getId()) {
                        duplicate = true;
                }
            }
            if (duplicate == false) {
                    tournamentpop.saveIndividual(selected);
                    System.out.println("fitness van selected random id :" + selected.getId() + "=" + selected.getFitness());
            }
        }System.out.println("tournamentpop size = "+ tournamentpop.size());
        //get fittest
        int parent = 0;
        for(int i = 0; i < tournamentsize; i++) {
            if(tournamentpop.getIndividual(parent).getFitness() <= tournamentpop.getIndividual(i).getFitness()) {
                parent = i;
            }
        }
        Individual fittest = tournamentpop.getIndividual(parent);
        System.out.println("fitness van fittest selected :" + fittest.getFitness());

        return fittest;
    }

    public void selectparents(){
        parents = new Population(numberofparents, evaluation_, false);
        while(parents.size() < numberofparents) {
            Individual parent = tournamentSelection(currentPopulation);


            boolean duplicate = false;
            for(int j = 0; j< parents.size() ; j++) {
                if (parent.getId() == parents.getIndividual(j).getId()) {
                    duplicate = true;
                }
            }
            if (duplicate == false) {
                parents.saveIndividual(parent);
            }

            System.out.println("ID parent :" + parent.getId() + "fitnes: " + parent.getFitness());
        }
        System.out.println("number of parents :" + parents.size());
    }

    public int anyRandomIntRange(int low, int high) {
        int randomInt = RANDOM.nextInt(high) + low;
        System.out.println("random integer from " + low + " to " + high + ":" + randomInt);
        return randomInt;
    }

    public void run()
    {
        // Run your algorithm here


        // init population

        currentPopulation = new Population(popsize, evaluation_, true);
        LastID = currentPopulation.getmaxid();
        evals = currentPopulation.currentevaluations;
        System.out.println("eval = " + currentPopulation.currentevaluations);
        //selectparents();
        Recombination();
        nonUniformMutation();
        SurvivorSelection();

        //selectparents();
        Recombination();
        nonUniformMutation();
        SurvivorSelection();
        // calculate fitness

        while(evals<evaluations_limit_){
            //while(evals<5){
            //System.out.println("eval = " + evals);
            Recombination();
            nonUniformMutation();
            SurvivorSelection();
            //SurvivorSelection();


            // Select parents
            // Apply crossover / mutation operators
            //double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
            // Check fitness of unknown fuction
            //Double fitness = (double) evaluation_.evaluate(child);
            //System.out.println(fitness);
            evals++;
            // Select survivors
        }

    }
}
