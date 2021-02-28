package decerto.datasources.dynamic;

import decerto.datasources.DataSourceParameters;

import java.util.List;
import java.util.stream.Collectors;

public class StringJavaDataSource extends JavaDataSource {

    public static final String STRING = "STRING";

    @Override
    public String getIdentifier() {
        return STRING;
    }

    private String getCharAsString(String num) {
        return String.valueOf((char) Integer.parseInt(num));
    }

    @Override
    public List<String> getData(DataSourceParameters dataSourceParameters) {

        int asciiAIdx = 65;
        int asciiZIdx = 90;
        int min = 1;
        int max = 10;

        int len = getRandomNumberUsingInts(min, max);
        List<String> intList = getData(dataSourceParameters,asciiAIdx,asciiZIdx, len);
        return intList.stream().map(this::getCharAsString).collect(Collectors.toList());
    }
}
