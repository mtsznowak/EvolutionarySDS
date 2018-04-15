package pl.agenty;

import org.jfree.ui.RefineryUtilities;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.measure.Measurable;
import org.uma.jmetal.measure.PullMeasure;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.Rastrigin;
import org.uma.jmetal.runner.multiobjective.NSGAIIRunner;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import java.util.List;


public class App extends NSGAIIRunner {
    public static void main(String[] args) {
        long maxTime = 5000;
        int numberOfVariables = 500;
        int populationSize = 100;


        ProgressChart chart = new ProgressChart("Objective/Time", maxTime);
        chart.drawChart();
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

        Problem<DoubleSolution> problem;
        Algorithm<DoubleSolution> standardAlgorithm, sdsAlgorithm;
        CrossoverOperator<DoubleSolution> crossover;
        MutationOperator<DoubleSolution> mutation;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;

        problem = new Rastrigin(numberOfVariables);

        double crossoverProbability = 0.9;
        double crossoverDistributionIndex = 20.0;
        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

        double mutationProbability = 1.0 / problem.getNumberOfVariables();
        double mutationDistributionIndex = 20.0;
        mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);

        selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());

        SDSOperator<DoubleSolution> sdsOperator = new DoubleSDS(1, 0.2);

        standardAlgorithm = new MeasurableSteadyStateGeneticAlgorithm(problem, maxTime, populationSize, crossover, mutation, selection);
        sdsAlgorithm = new EvolutionarySDS(problem, maxTime, populationSize, sdsOperator, crossover, mutation, selection);

        runAlgorithm(standardAlgorithm, "SS-GA", chart);
        runAlgorithm(sdsAlgorithm, "SDS-SS-GA", chart);

    }

    public static void runAlgorithm(Algorithm<DoubleSolution> algorithm, String name, ProgressChart chart){
        Measurable measurable = (Measurable) algorithm;
        PullMeasure measure = measurable.getMeasureManager().getPullMeasure("currentBestIndividual");
        chart.initSerie("SS-GA", measure);


        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        DoubleSolution individual = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        JMetalLogger.logger.info("Total execution time ("+name+"): " + computingTime + "ms");
        JMetalLogger.logger.info("Best individual objective ("+name+"): " + individual.getObjective(0));
    }
}
