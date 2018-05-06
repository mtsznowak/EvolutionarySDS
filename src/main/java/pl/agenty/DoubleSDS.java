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
        List<DoubleSolution> new_population = new ArrayList<DoubleSolution>();
        for (DoubleSolution s : population) {
            new_population.add((DoubleSolution)s.copy());
        }

        for (int it = 0; it < this.iterations; it++) {
            Double averageObjective = calculateAverageObjective(new_population);

            for (DoubleSolution s : new_population) {
                int randomIndex = this.randomGenerator.nextInt(population.size());
                DoubleSolution randomSolution = population.get(randomIndex);

                if(s.getObjective(0) > averageObjective && randomSolution.getObjective(0) < averageObjective) {
                    for(int i=0; i<s.getNumberOfVariables(); i++) {
                        Double new_value = (randomSolution.getVariableValue(i) * sdsFactor + s.getVariableValue(i) * (1 - sdsFactor));
                        s.setVariableValue(i, new_value);
                    }
                }
            }
        }

        return new_population;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setSdsFactor(double sdsFactor) {
        this.sdsFactor = sdsFactor;
    }

    private double calculateAverageObjective(List<DoubleSolution> population) {
        double sum = 0;
        for (DoubleSolution solution : population) {
            sum += solution.getObjective(0);
        }
        return sum/population.size();
    }
}
