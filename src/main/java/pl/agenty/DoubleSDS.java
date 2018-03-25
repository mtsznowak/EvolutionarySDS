package pl.agenty;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.List;
import java.util.Random;


public class DoubleSDS implements SDSOperator<DoubleSolution> {
    private int iterations;
    private Random randomGenerator;


    public DoubleSDS(int iterations) {
        this.iterations = 1;
        this.randomGenerator = new Random();
    }

    public List<DoubleSolution> execute(List<DoubleSolution> population) {
        for (int it = 0; it < this.iterations; it++) {
            Double averageObjective = calculateAverageObjective(population);

            for (DoubleSolution s : population) {

                if(s.getObjective(0) < averageObjective) {
                    int randomIndex = this.randomGenerator.nextInt(population.size());
                    DoubleSolution randomSolution = population.get(randomIndex);

                    if(randomSolution.getObjective(0) > averageObjective) {
                        for (int k = 0; k < s.getNumberOfVariables(); k++) {
                            s.setVariableValue(k, randomSolution.getVariableValue(k));
                        }
                    }
                }
            }

        }

        return population;
    }

    private double calculateAverageObjective(List<DoubleSolution> population) {
        double sum = 0;
        for (DoubleSolution solution : population) {
            sum += solution.getObjective(0);
        }
        return sum/population.size();
    }
}
