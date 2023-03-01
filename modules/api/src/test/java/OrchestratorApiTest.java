import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.eclipse.xpanse.modules.database.ServiceStatusEntity;
import org.eclipse.xpanse.orchestrator.DatabaseOrchestratorStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrchestratorApiTest {
    private final DatabaseOrchestratorStorage databaseOrchestratorStorage;

    @Autowired
    public OrchestratorApiTest(
        DatabaseOrchestratorStorage databaseOrchestratorStorage) {
        this.databaseOrchestratorStorage = databaseOrchestratorStorage;
    }
    @Test
    public Integer addService(){
        ServiceStatusEntity statusEntity = new ServiceStatusEntity();
        statusEntity.setId(UUID.randomUUID());
        statusEntity.setServiceName("kafka");
        statusEntity.setPluginName("kafka");
        statusEntity.setStatusMessage("kafka");
        return 0;
    }


    @Test
    public List<ServiceStatusEntity> getServiceList(){
        List<ServiceStatusEntity> list = new ArrayList<>();
        return list;
    }

}
