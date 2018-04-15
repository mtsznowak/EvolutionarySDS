package pl.agenty;

import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

import java.util.List;

public class EvolutionarySDS<S extends Solution<?>> extends MeasurableSteadyStateGeneticAlgorithm<S> {
    private SDSOperator<S> sdsOperator;

    public EvolutionarySDS(Problem<S> problem, long maxTime, int populationSize, SDSOperator<S> sdsOperator, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator) {
        super(problem, maxTime, populationSize, crossoverOperator, mutationOperator, selectionOperator);
        this.sdsOperator = sdsOperator;
    }

    @Override
    protected List<S> evaluatePopulation(List<S> population){
        population = super.evaluatePopulation(population);
        population = this.sdsOperator.execute(population);
        return super.evaluatePopulation(population);
    }
}
