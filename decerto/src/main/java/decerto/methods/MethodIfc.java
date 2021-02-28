package decerto.methods;

import decerto.connect.CommonDynamicIfc;
import decerto.datasources.DataSourceParameters;

import java.util.List;

public interface MethodIfc extends CommonDynamicIfc {
    void applyMethod(DataSourceParameters dataSourceParameters,
                     List<CommonDynamicIfc> listDataSources,
                     List<CommonDynamicIfc> listStrategies);
}
