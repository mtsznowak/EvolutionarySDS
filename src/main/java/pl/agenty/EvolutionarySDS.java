package pl.agenty;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import java.util.List;

public class EvolutionarySDS<S extends Solution<?>> extends NSGAII<S> {
    private SDSOperator<S> sdsOperator;

    public EvolutionarySDS(Problem<S> problem, int maxEvaluations, int populationSize, SDSOperator<S> sdsOperator, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
        super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, evaluator);
        this.sdsOperator = sdsOperator;
    }

    @Override
    protected List<S> evaluatePopulation(List<S> population){
        population = super.evaluatePopulation(population);
        population = this.sdsOperator.execute(population);
        return super.evaluatePopulation(population);
    }
}
