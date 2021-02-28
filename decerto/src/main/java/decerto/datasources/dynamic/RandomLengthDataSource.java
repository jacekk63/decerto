package decerto.datasources.dynamic;

import decerto.datasources.DataSourceParameters;

import java.util.List;

public class RandomLengthDataSource extends JavaDataSource {

    public static final String RANDOMLEN = "RANDOMLEN";

    @Override
    public String getIdentifier() {
        return RANDOMLEN;
    }

    @Override
    public List<String> getData(DataSourceParameters dataSourceParameters) {
        int min = 1;
        int max = 10;
        int maxLen = 30;

        int len = getRandomNumberUsingInts(min, maxLen);
        return getData(dataSourceParameters,min,max, len);
    }
}
