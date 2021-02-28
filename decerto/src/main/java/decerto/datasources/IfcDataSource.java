package decerto.datasources;

import decerto.connect.CommonDynamicIfc;

import java.util.List;

public interface IfcDataSource extends CommonDynamicIfc {
    String getIdentifier();
    List<String> getData(DataSourceParameters dataSourceParameters);
}
