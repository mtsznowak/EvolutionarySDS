package pl.agenty;

import org.uma.jmetal.operator.Operator;
import org.uma.jmetal.solution.Solution;

import java.util.List;

public interface SDSOperator<S extends Solution<?>> extends Operator<List<S>,List<S>> {
}
