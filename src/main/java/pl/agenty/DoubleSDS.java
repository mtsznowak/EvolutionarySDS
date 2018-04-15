package pl.agenty;
import org.uma.jmetal.solution.DoubleSolution;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DoubleSDS implements SDSOperator<DoubleSolution> {
    private int iterations;
    private double sdsFactor;
    private Random randomGenerator;


    public DoubleSDS(int iterations, double sdsFactor) {
        this.iterations = iterations;
        this.sdsFactor = sdsFactor;

        this.randomGenerator = new Random();
    }

    public List<DoubleSolution> execute(List<DoubleSolution> population) {

        for (int it = 0; it < this.iterations; it++) {
            Double averageObjective = calculateAverageObjective(population);
            List<DoubleSolution> new_population = new ArrayList<DoubleSolution>();

            for (DoubleSolution s : population) {
                int randomIndex = this.randomGenerator.nextInt(population.size());
                DoubleSolution randomSolution = population.get(randomIndex);

                if(s.getObjective(0) > averageObjective && randomSolution.getObjective(0) < averageObjective) {
                    DoubleSolution new_individual = (DoubleSolution)s.copy();

                    for(int i=0; i<s.getNumberOfVariables(); i++) {
                        Double new_value = (randomSolution.getVariableValue(i) * sdsFactor + s.getVariableValue(i) * (1 - sdsFactor));
                        new_individual.setVariableValue(i, new_value);
                    }
                    new_population.add(new_individual);
                } else {
                    new_population.add((DoubleSolution)s.copy());
                }
            }
            population = new_population;
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
