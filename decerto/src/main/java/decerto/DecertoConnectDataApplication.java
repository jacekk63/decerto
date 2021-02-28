package decerto;

import decerto.connect.ConnectDataSources;
import decerto.connect.OperationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.util.Optional;

@SpringBootApplication
public class DecertoConnectDataApplication {

    private static String[] arguments;
    private ArgumentsProcessor argumentsProcessor;
    private final RegisterDataSource registerDataSource;

    @Autowired
    public DecertoConnectDataApplication(RegisterDataSource registerDataSource) {
        this.registerDataSource = registerDataSource;
    }

    @Autowired
    public void setArgumentsProcessor(ArgumentsProcessor argumentsProcessor) {
        this.argumentsProcessor = argumentsProcessor;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void processCounting() {
        argumentsProcessor.extractArgs(arguments);
        Optional<OperationData> optOperationData =
                registerDataSource.prepareDataSources(argumentsProcessor.getDataSourceParameters());
        if (optOperationData.isPresent()) {
            OperationData operationData = optOperationData.get();
            ConnectDataSources connectDataSources = new ConnectDataSources();
            connectDataSources.applyStrategies(argumentsProcessor.getDataSourceParameters(), operationData);
        } else {
            System.out.println("Improper configuration. Program will not connect data.");
        }

        System.out.println();
        System.out.println("Program finished.");
        System.out.println();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//         System.exit(0);
    }

    public static void main(String[] args) {
        arguments = args;
        SpringApplication.run(DecertoConnectDataApplication.class, args);
    }
}
