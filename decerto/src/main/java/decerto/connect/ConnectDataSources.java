package decerto.connect;

import decerto.datasources.DataSourceParameters;
import decerto.methods.MethodIfc;

import java.util.List;

public class ConnectDataSources {

    public void applyStrategies(DataSourceParameters dataSourceParameters, OperationData operationData) {
        List<CommonDynamicIfc> listMethods = operationData.getListMethods();
        List<CommonDynamicIfc> listDataSources = operationData.getListDataSources();
        List<CommonDynamicIfc> listStrategies = operationData.getListStrategies();

        for (CommonDynamicIfc listMethod : listMethods) {
            MethodIfc method = (MethodIfc) listMethod;
            method.applyMethod(dataSourceParameters, listDataSources, listStrategies);
        }
    }
}
