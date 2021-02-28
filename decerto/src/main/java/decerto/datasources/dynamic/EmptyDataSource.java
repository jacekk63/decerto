package decerto.datasources.dynamic;

import decerto.datasources.DataSourceParameters;
import decerto.datasources.IfcDataSource;

import java.util.ArrayList;
import java.util.List;

public class EmptyDataSource implements IfcDataSource {

    private static final String EMPTY = "EMPTY";

    @Override
    public String getIdentifier() {
        return EMPTY;
    }

    @Override
    public List<String> getData(DataSourceParameters dataSourceParameters) {
        return new ArrayList<>();
    }
}

