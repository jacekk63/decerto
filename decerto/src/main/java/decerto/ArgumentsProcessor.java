package decerto;

import decerto.datasources.DataSourceParameters;
import org.springframework.stereotype.Service;

@Service
public class ArgumentsProcessor {

    private DataSourceParameters dataSourceParameters;

    public void extractArgs(String[] args) {
        dataSourceParameters = new DataSourceParameters(args);
    }

    public DataSourceParameters getDataSourceParameters() {
        return dataSourceParameters;
    }
}
