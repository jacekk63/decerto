package decerto.connect;

import java.util.List;

public class OperationData {

    private final List<CommonDynamicIfc> listMethods;
    private final List<CommonDynamicIfc> listDataSources;
    private final List<CommonDynamicIfc> listStrategies;

    public OperationData(List<CommonDynamicIfc> methods, List<CommonDynamicIfc> dataSources, List<CommonDynamicIfc> strategies) {
        this.listMethods = methods;
        this.listDataSources = dataSources;
        this.listStrategies = strategies;
    }

    public List<CommonDynamicIfc> getListMethods() {
        return listMethods;
    }

    public List<CommonDynamicIfc> getListDataSources() {
        return listDataSources;
    }

    public List<CommonDynamicIfc> getListStrategies() {
        return listStrategies;
    }
}
