package decerto.strategy;

import decerto.connect.CommonDynamicIfc;

public interface ConnectStrategyIfc extends CommonDynamicIfc {
    String connectData(String first, String second);
}
