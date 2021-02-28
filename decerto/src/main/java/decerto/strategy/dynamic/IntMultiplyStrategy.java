package decerto.strategy.dynamic;

import java.util.Optional;

public class IntMultiplyStrategy extends SumStrategy {

    private static final String INTMULTIPLY = "INTMULTIPLY";

    @Override
    public String getIdentifier() {
        return INTMULTIPLY;
    }

    @Override
    public String connectData(String first, String second) {
        Optional<Integer> optFirst = getInteger(first);
        Optional<Integer> optSecond = getInteger(second);
        if (optFirst.isPresent() && optSecond.isPresent()) {
            return Integer.toString(optFirst.get() * optSecond.get());
        }
        return super.connectData(first, second);
    }

}
