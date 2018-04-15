package pl.agenty;

import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
import org.uma.jmetal.measure.Measurable;
import org.uma.jmetal.measure.MeasureManager;
import org.uma.jmetal.measure.PushMeasure;
import org.uma.jmetal.measure.impl.SimpleMeasureManager;
import org.uma.jmetal.measure.impl.SimplePullMeasure;
import org.uma.jmetal.measure.impl.SimplePushMeasure;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

import java.util.List;

public class MeasurableSteadyStateGeneticAlgorithm <S extends Solution<?>> extends SteadyStateGeneticAlgorithm<S> implements Measurable{
    private SimpleMeasureManager measureManager;
    private SimplePullMeasure<Double> bestObjectiveMeasure;
    private long initTime;
    private Double lastBestObjective = new Double(0);
    private long maxTime;

    public MeasurableSteadyStateGeneticAlgorithm(Problem<S> problem, long maxTime, int populationSize, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator) {
        super(problem, 0, populationSize, crossoverOperator, mutationOperator, selectionOperator);
        this.maxTime = maxTime;

        this.measureManager = new SimpleMeasureManager();
        this.bestObjectiveMeasure = new SimplePullMeasure() {
            public Object get() {
                return lastBestObjective;
            }
        };
        this.measureManager.setPullMeasure("currentBestIndividual", bestObjectiveMeasure);

    }

    @Override
    public void initProgress() {
        this.initTime = System.currentTimeMillis();;
    }

    @Override
    public void updateProgress() {
        this.lastBestObjective = getResult().getObjective(0);
        return;
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return System.currentTimeMillis() >= this.initTime + this.maxTime;
    }


    public MeasureManager getMeasureManager() {
        return this.measureManager;
    }
}
