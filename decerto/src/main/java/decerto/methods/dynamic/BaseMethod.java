package decerto.methods.dynamic;

import decerto.connect.CommonDynamicIfc;
import decerto.datasources.DataSourceParameters;
import decerto.datasources.IfcDataSource;
import decerto.datasources.JavaFromListDataSource;
import decerto.methods.MethodIfc;
import decerto.strategy.ConnectStrategyIfc;

import java.util.ArrayList;
import java.util.List;

public class BaseMethod implements MethodIfc {

    public static final String BASE_METHOD = "BASE_METHOD";

    @Override
    public String getIdentifier() {
        return BASE_METHOD;
    }

    /**
     * BaseMethod will apply data from DataSources using defined Strategies
     * by connecting data from each DataSource to result list
     * @param dataSourceParameters parameters processed from command-line arguments
     * @param listDataSources list of Data Sources to connect elements
     * @param listStrategies list of Strategies to deploy connections
     */
    @Override
    public void applyMethod(DataSourceParameters dataSourceParameters,
                            List<CommonDynamicIfc> listDataSources,
                            List<CommonDynamicIfc> listStrategies) {
        System.out.println();
        System.out.println();
        System.out.println("######Applying " + getIdentifier() + " method######");

        //data from data-sources will be connected to result list
        List<String> result = new ArrayList<>();

        for (CommonDynamicIfc strategy : listStrategies) {
            System.out.println();
            System.out.println("###Applying " + strategy.getIdentifier() + " strategy###");
            for (CommonDynamicIfc listDataSource : listDataSources) {
                CommonDynamicIfc firstDs = getDataSourceFromList(result);
                result = connectDataSources(dataSourceParameters, firstDs, listDataSource, strategy);
            }
        }
    }

    private CommonDynamicIfc getDataSourceFromList(List<String> list) {
        JavaFromListDataSource jflds = new JavaFromListDataSource();
        jflds.setSource(list);
        return jflds;
    }

    private List<String> connectDataSources(DataSourceParameters dataSourceParameters,
                                            CommonDynamicIfc first,
                                            CommonDynamicIfc second,
                                            CommonDynamicIfc strategy) {
        List<String> result = new ArrayList<>();
        List<String> dataFromFirst = ((IfcDataSource) first).getData(dataSourceParameters);
        List<String> dataFromSecond = ((IfcDataSource) second).getData(dataSourceParameters);

        System.out.println("Connecting data from " + first.getIdentifier() + " and " + second.getIdentifier() + " data sources.");
        displayData("First data source ", first.getIdentifier(), dataFromFirst);
        displayData("Second data source ", second.getIdentifier(), dataFromSecond);

        for (int idxFirst=0, idxSecond=0;
             idxFirst < dataFromFirst.size() || idxSecond < dataFromSecond.size();
             idxFirst++, idxSecond++  ) {

            String firstValue="", secondValue="";
            if (idxFirst < dataFromFirst.size()) {
                firstValue = dataFromFirst.get(idxFirst);
            }
            if (idxSecond < dataFromSecond.size()) {
                secondValue = dataFromSecond.get(idxSecond);
            }

            result.add(((ConnectStrategyIfc)strategy).connectData(firstValue, secondValue));
        }

        displayData("Result data: ", "", result);
        System.out.println();

        return result;
    }

    private void displayData(String description, String identifier, List<String> data) {
        StringBuilder sb = new StringBuilder(description).append(identifier);
        for (int i=sb.toString().length(); i<35; i++) {
            sb.append(" ");
        }
        data.stream().map(a -> a + " ").forEach(sb::append);
        System.out.println(sb.toString());
    }
}
