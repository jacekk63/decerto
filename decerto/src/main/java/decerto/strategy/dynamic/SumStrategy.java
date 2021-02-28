package decerto.strategy.dynamic;

import decerto.strategy.ConnectStrategyIfc;

import java.util.Optional;

public class SumStrategy implements ConnectStrategyIfc {

    public final static String STRATEGY_ID = "SUM";

    @Override
    public String getIdentifier() {
        return STRATEGY_ID;
    }

    @Override
    public String connectData(String first, String second) {
        return first + second;
    }

    protected Optional<Integer> getInteger(String value) {
        try {
            return Optional.of(Integer.valueOf(value));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
