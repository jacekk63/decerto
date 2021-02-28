package decerto;

import decerto.connect.CommonDynamicIfc;
import decerto.connect.OperationData;
import decerto.datasources.DataSourceParameters;
import decerto.datasources.dynamic.JavaDataSource;
import decerto.datasources.dynamic.RestDataSource;
import decerto.datasources.dynamic.rest.RestEnv;
import decerto.methods.dynamic.BaseMethod;
import decerto.strategy.dynamic.SumStrategy;
import lombok.val;
import org.reflections.Reflections;
import org.reflections.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Controller
public class RegisterDataSource {

    /**
     * Package for new data sources.
     * Note that each class of data source has to implement IfcDataSource interface
     */
    private static final String DECERTO_METHODS_DYNAMIC_PACKAGE = "decerto.methods.dynamic";

    /**
     * Package for new data sources.
     * Note that each class of data source has to implement IfcDataSource interface
     */
    private static final String DECERTO_DATASOURCES_DYNAMIC_PACKAGE = "decerto.datasources.dynamic";

    /**
     * Package for new strategies.
     * Note that each class of data source has to implement ConnectStrategyIfc interface
     */
    private static final String DECERTO_STRATEGY_DYNAMIC_PACKAGE = "decerto.strategy.dynamic";

    private static final String SET_REST_ENV_METHOD = "setRestEnv";

    private static final String SET_METHODS_PARAM = "methods";
    private static final String SET_DATA_SOURCE_PARAM = "dataSources";
    private static final String SET_STRATEGY_PARAM = "strategy";

    /**
     * environment for RestCallerDataSource
     */
    public RestEnv restEnv;

    @Autowired
    public RegisterDataSource(RestEnv restEnv) {
        this.restEnv = restEnv;
    }

    public Optional<OperationData> prepareDataSources(DataSourceParameters dataSourceParameters) {

        // retrieve methods, data sources and strategies
        Set<String> methods = getIdsToInstantiate(dataSourceParameters, SET_METHODS_PARAM,
                BaseMethod.BASE_METHOD, "method");
        Set<String> dataSources = getIdsToInstantiate(dataSourceParameters, SET_DATA_SOURCE_PARAM,
                JavaDataSource.JAVA + " and " + RestDataSource.REST, "data source");
        Set<String> strategies = getIdsToInstantiate(dataSourceParameters, SET_STRATEGY_PARAM,
                                SumStrategy.STRATEGY_ID, "strategy");

        List<CommonDynamicIfc> listMethods = instantiateDataSources(DECERTO_METHODS_DYNAMIC_PACKAGE, methods);
        if (listMethods.size() == 0) {
            System.out.println("ERROR: Undefined methods!");
            return Optional.empty();
        }

        List<CommonDynamicIfc> listDataSources = instantiateDataSources(DECERTO_DATASOURCES_DYNAMIC_PACKAGE, dataSources);
        if (listDataSources.size() == 0) {
            System.out.println("ERROR: Undefined data sources!");
            return Optional.empty();
        }

        List<CommonDynamicIfc> listStrategies = instantiateDataSources(DECERTO_STRATEGY_DYNAMIC_PACKAGE, strategies);
        if (listStrategies.size() == 0) {
            System.out.println("ERROR: Undefined strategies!");
            return Optional.empty();
        }

        OperationData operationData = new OperationData(listMethods, listDataSources, listStrategies);
        return  Optional.of(operationData);
    }

    private Set<String> getIdsToInstantiate(DataSourceParameters dataSourceParameters, String paramName,
                                            String defaultParameters, String shortDescription) {
        String[] array = getDataSourceIdentifiers(dataSourceParameters, paramName);
        if (array.length == 0) {
            System.out.println("ERROR: Undefined " + shortDescription + " identifiers. WIll be used " +
                    defaultParameters + " as " + shortDescription + " identifier.");
            array = new String[] { defaultParameters };
        }
        Set<String> hs = new HashSet<>();
        for (String val : array) {
            hs.add(val.toUpperCase());
        }
        return hs;
    }

    private String[] getDataSourceIdentifiers(DataSourceParameters dataSourceParameters, String type) {
        List<String> parameters = dataSourceParameters.getParameters();
        for (String param : parameters) {
            if (param.startsWith(type)) {
                String values = param.substring(type.length() + 1);
                if (values.length() > 0) {
                    String[] splited = values.split(",");
                    if (splited.length > 0) {
                        return splited;
                    }
                }
            }
        }
        return new String[] {};
    }

    private List<CommonDynamicIfc> instantiateDataSources(String prefix, Set<String> toInstantiate) {
        List<CommonDynamicIfc> result = new ArrayList<>();
        try {
            Reflections reflections = new Reflections(prefix);
            Store store = reflections.getStore();
            Set<String> set = store.values("SubTypesScanner");
            for (String className : set) {
                if (className.startsWith(prefix)) {
                    val t = Class.forName(className);
                    Object obj = t.getDeclaredConstructor().newInstance();
                    if (obj instanceof CommonDynamicIfc) {
                        CommonDynamicIfc ids = (CommonDynamicIfc) obj;
                        if (toInstantiate.contains(ids.getIdentifier().toUpperCase())) {
                            Method[] methods = t.getDeclaredMethods();
                            for (Method method : methods) {
                                if (method.getName().equalsIgnoreCase(SET_REST_ENV_METHOD)) {
                                    method.invoke(ids, restEnv);
                                }
                            }
                            result.add(ids);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("Instantiated object not found. " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Illegal access exception during instantiate object. " + e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Instantiation exception during instantiate object. " + e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("Invocation target exception during instantiate object. " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
