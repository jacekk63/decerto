package decerto.datasources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataSourceParameters {

    private static final String MIN = "min";
    private static final String MAX = "max";
    private static final String ELEMENTS = "elements";

    private static final int MIN_INT_DEFAULT = 1;
    private static final int MAX_INT_DEFAULT = 10;
    private static final int ELEMENTS_INT_DEFAULT = 5;

    private int minInt  = MIN_INT_DEFAULT;
    private int maxInt  = MAX_INT_DEFAULT;
    private int numbers = ELEMENTS_INT_DEFAULT;

    private final List<String> parameters;

    public DataSourceParameters(String[] args) {
        this.parameters = new ArrayList<>();
        extractCommonParameters(args);
    }

    private void extractCommonParameters(String[] args) {

        displayIntro();
        if (args == null || args.length == 0) {
            return;
        }

        System.out.println("Parsing program arguments. ");
        int min = minInt;
        int max = maxInt;
        int num = numbers;

        for (String arg : args) {
            if (arg.startsWith(MIN)) {
                Optional<Integer> opt = extractIntParam(arg);
                if (opt.isPresent()) {
                    min = opt.get();
                }
            } else if (arg.startsWith(MAX)) {
                Optional<Integer> opt = extractIntParam(arg);
                if (opt.isPresent()) {
                    max = opt.get();
                }
            } else if (arg.startsWith(ELEMENTS)) {
                Optional<Integer> opt = extractIntParam(arg);
                if (opt.isPresent()) {
                    num = opt.get();
                }
            } else {
                parameters.add(arg);
                System.out.println("Specific parameter: " + arg + " will be passed to the consumer.");
            }
        }

        if (min >= max) {
            System.out.println("Incorrect parameters min value(" + min
                    +" is greater or equal max value(" + max + "). Default values will be used.");
        } else {
            minInt = min;
            maxInt = max;
        }

        if (num <= 0) {
            System.out.println("Incorrect parameter numbers (" + numbers
                    + "). Default value will be used.");
        } else {
            numbers = num;
        }
    }

    private void displayIntro() {
        System.out.println();
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Methods, Strategies and Data Sources are instantiated by Reflection (each level in system dependent order).");
        System.out.println("Program arguments are separated by Space character. Unknown arguments will be ignored.");
        System.out.println("Program arguments will be passed to Methods, Strategies and Data Sources.");
        System.out.println("Each Method, Strategy and Data Source can use own specific command-line parameters.");
        System.out.println();
        System.out.println("Example program arguments: ");
        System.out.println("   min=5 max=20 elements=10 dataSources=REST,JAVA,FILE,RANDOMLEN strategy=INTSUM method=BASE_METHOD");
        System.out.println("This argument set will generate data from Data Sources with identifiers: REST,JAVA,FILE,RANDOMLEN");
        System.out.println("All data will be connected by strategy INTSUM (elements from different Data Sources will be summed if possible).");
        System.out.println("All data will be connected by BASE_METHOD.");
        System.out.println("Data Sources that generates random int values (now JavaDataSource and RestDataSource) will produce values from 5 to 20 end returns 10 elements.");
        System.out.println("RandomLengthDataSource (identifier RANDOMLEN) will produce values from 1 to 10 end returns up to 30 elements (hardcoded).");
        System.out.println("StringJavaDataSource (identifier STRING) will produce letters and will return up to 10 elements (hardcoded).");
        System.out.println("EmptyDataSource (identifier EMPTY) will produce empty Data Source.");
        System.out.println();
        System.out.println("Defined following strategies:");
        System.out.println("SUM that will connect two values by string concatenation,");
        System.out.println("INTSUM that will sum two values as int values if possible,");
        System.out.println("INTMULTIPLY that will multiply two values as int values if possible,");
        System.out.println();
        System.out.println("Defined following method:");
        System.out.println("BASE_METHOD that will produce one Data Result,");
        System.out.println("each element from Data Source will be connected to corresponding element from different Data Sources,");
        System.out.println("if there is more than two Data Sources then elements from each will be connected to corresponding element from previous result.");
        System.out.println();
        System.out.println("Example: ");
        System.out.println(" min=5 REST bleble elements=10 max=20 dataSources=REST,JAVA,EMPTY,RANDOMLEN,STRING strategy=INTMULTIPLY");
        System.out.println();
        System.out.println("Example: ");
        System.out.println(" strategy=INTSUM,INTMULTIPLY min=5 elements=10 max=20 dataSources=REST,JAVA,EMPTY,RANDOMLEN,STRING");
        System.out.println();
        System.out.println("We can define more Methods, Strategies and Data Sources.");
        System.out.println();
    }

    public List<String> getParameters() {
        return parameters;
    }

    private Optional<Integer> extractIntParam(String param) {
        param = param.replaceAll("[a-z]", "");
        param = param.replaceAll("=", "");

        try {
            return Optional.of(Integer.valueOf(param.trim()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public int getMinInt() {
        return minInt;
    }

    public int getMaxInt() {
        return maxInt;
    }

    public int getElements() {
        return numbers;
    }
}
