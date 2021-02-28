package decerto.datasources;

import java.util.List;

public class JavaFromListDataSource implements IfcDataSource {

    private List<String> source;

    @Override
    public String getIdentifier() {
        return "INTERNAL";
    }

    @Override
    public List<String> getData(DataSourceParameters dataSourceParameters) {
        return source;
    }

    public void setSource(List<String> list) {
        source = list;
    }
}
