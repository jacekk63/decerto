package decerto.datasources.dynamic;

import decerto.datasources.DataSourceParameters;
import decerto.datasources.IfcDataSource;
import decerto.datasources.dynamic.rest.RestEnv;

import java.util.Arrays;
import java.util.List;

public class RestDataSource implements IfcDataSource {

    public static final String REST = "REST";

    public RestEnv restEnv;

    public void setRestEnv(RestEnv restEnv) {
        this.restEnv = restEnv;
    }

    @Override
    public String getIdentifier() {
        return REST;
    }

    @Override
    public List<String> getData(DataSourceParameters dataSourceParameters) {

        String resString = "";
        if (restEnv != null && restEnv.getRestTemplate() != null) {
            resString = restEnv.getRestTemplate().getForObject(
                    "https://www.random.org/integers/?num="+dataSourceParameters.getElements()+
                            "&min="+dataSourceParameters.getMinInt()+
                            "&max="+dataSourceParameters.getMaxInt()+
                            "&col=1&base=10&format=plain&rnd=new", String.class);
        } else {
            System.out.println("Error. REST call cannot be executed. Spring dependency injection issue.");
        }

        assert resString != null;
        String[] array = resString.split("\n");
        return Arrays.asList(array);
    }
}
