package pl.agenty;

import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
import org.uma.jmetal.measure.Measurable;
import org.uma.jmetal.measure.MeasureManager;
import org.uma.jmetal.measure.PushMeasure;
import org.uma.jmetal.measure.impl.SimpleMeasureManager;
import org.uma.jmetal.measure.impl.SimplePushMeasure;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

import java.util.List;

public class MeasurableSteadyStateGeneticAlgorithm <S extends Solution<?>> extends SteadyStateGeneticAlgorithm<S> implements Measurable{
    private int maxEvaluations;
    private int evaluations;
    private SimpleMeasureManager measureManager;
    private SimplePushMeasure<Double> bestObjectiveMeasure;

    public MeasurableSteadyStateGeneticAlgorithm(Problem<S> problem, int maxEvaluations, int populationSize, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator) {
        super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator);
        this.maxEvaluations = maxEvaluations;

        this.measureManager = new SimpleMeasureManager();
        this.bestObjectiveMeasure = new SimplePushMeasure<Double>();
        this.measureManager.setPushMeasure("currentBestIndividual", (PushMeasure)bestObjectiveMeasure);
    }

    @Override
    public void initProgress() {
        this.evaluations = 1;
    }

    @Override
    public void updateProgress() {
        ++this.evaluations;
        Double bestObjective = this.getResult().getObjective(0);
        this.bestObjectiveMeasure.push(bestObjective);
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return this.evaluations >= this.maxEvaluations;
    }


    public MeasureManager getMeasureManager() {
        return this.measureManager;
    }
}
