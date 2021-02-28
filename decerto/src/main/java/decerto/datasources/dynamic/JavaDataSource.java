package decerto.datasources.dynamic;

import decerto.datasources.DataSourceParameters;
import decerto.datasources.IfcDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JavaDataSource implements IfcDataSource {

    public static final String JAVA = "JAVA";

    @Override
    public String getIdentifier() {
        return JAVA;
    }

    @Override
    public List<String> getData(DataSourceParameters dataSourceParameters) {
        return getData(dataSourceParameters,
                dataSourceParameters.getMinInt(),
                dataSourceParameters.getMaxInt(),
                dataSourceParameters.getElements());
    }

    protected List<String> getData(DataSourceParameters dataSourceParameters, int min, int max, int elements) {
        List<String> list = new ArrayList<>(elements);
        for(int i=0; i<elements; i++) {
            int val = getRandomNumberUsingInts(min, max);
            list.add(Integer.toString(val));
        }
        return list;
    }

    protected int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }
}
