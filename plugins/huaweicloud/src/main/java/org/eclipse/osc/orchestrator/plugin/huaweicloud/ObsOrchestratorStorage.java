package org.eclipse.osc.orchestrator.plugin.huaweicloud;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;

import com.obs.services.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.karaf.minho.boot.service.ConfigService;
import org.apache.karaf.minho.boot.service.ServiceRegistry;
import org.apache.karaf.minho.boot.spi.Service;
import org.eclipse.osc.orchestrator.OrchestratorStorage;

@Slf4j
public class ObsOrchestratorStorage implements OrchestratorStorage, Service {

    private static final String ACCESS_KEY = "HW_ACCESS_KEY";
    private static final String SECRET_KEY = "HW_SECRET_KEY";
    private static final String LOCATION = "HW_REGION_NAME";
    private static final String BUCKET_NAME = "huaweicloud-obs-storage-bucket";
    private static final String ENTERPRISE_PROJECT_ID = "HW_ENTERPRISE_PROJECT_ID";

    private static ObsClient obsClient;
    private static ObsBucket obsBucket;

    @Override
    public String name() {
        return "osc-huaweicloud-obs-orchestrator-storage";
    }

    @Override
    public void onRegister(ServiceRegistry serviceRegistry) {
        log.info("Registering Obs Orchestrator ...");
        if (serviceRegistry == null) {
            throw new IllegalStateException("ServiceRegistry is null");
        }

        ConfigService configService = serviceRegistry.get(ConfigService.class);
        if (configService == null) {
            throw new IllegalStateException("Config service is not present in the registry");
        }

        String accessKey = configService.getProperty(ACCESS_KEY, "obs");
        String secretKey = configService.getProperty(SECRET_KEY, "obsobs");
        String location = configService.getProperty(LOCATION, "cn-east-3");
        String enterpriseProjectId = configService.getProperty(ENTERPRISE_PROJECT_ID, "0");
        String endpoint = "";
        if (location != null && location.length() != 0) {
            endpoint = "obs." + location + ".myhuaweicloud.com";
        }
        try {
            log.info("Start to create OBS Client and OBS Bucket.");
            obsClient = new ObsClient(accessKey, secretKey, endpoint);
            obsBucket = createBucket(obsClient, location, enterpriseProjectId);
        } catch (Exception e) {
            log.error("Create OBS Client and OBS Bucket Error.",e);
            throw new IllegalStateException("Create OBS Client and OBS Bucket Error.", e);
        }
    }

    private ObsBucket createBucket(ObsClient obsClient, String location,
        String enterpriseProjectId) {
        ObsBucket obsBucket = new ObsBucket(BUCKET_NAME, location);
        try {
            boolean found = obsClient.headBucket(BUCKET_NAME);
            if (!found) {
                CreateBucketRequest request = new CreateBucketRequest();
                request.setBucketName(obsBucket.getBucketName());
                request.setLocation(obsBucket.getLocation());
                request.setEpid(enterpriseProjectId);
                obsClient.createBucket(request);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Can't create " + BUCKET_NAME + " bucket in OBS", e);
        }
        return obsBucket;
    }

    @Override
    public int priority() {
        return 900;
    }

    @Override
    public void store(String sid) {
        if (!exists(sid)) {
            try (ByteArrayInputStream input = new ByteArrayInputStream(sid.getBytes(
                StandardCharsets.UTF_8))) {
                obsClient.putObject(obsBucket.getBucketName(), sid, input);
            } catch (Exception e) {
                throw new IllegalStateException("Can't store " + sid + " in Obs", e);
            }
        }
    }

    @Override
    public void store(String sid, String pluginName, String key, String value) {
        String objectKey = getObjectKey(sid, pluginName, key);
        try (ByteArrayInputStream input = new ByteArrayInputStream(value.getBytes(
            StandardCharsets.UTF_8))) {
            obsClient.putObject(obsBucket.getBucketName(), objectKey, input);
        } catch (Exception e) {
            throw new IllegalStateException("Can't store " + objectKey + " in Obs", e);
        }
    }

    @Override
    public String getKey(String sid, String pluginName, String key) {
        String objectKey = getObjectKey(sid, pluginName, key);
        String value = "";
        if (exists(objectKey)) {
            try {
                ObsObject object = obsClient.getObject(obsBucket.getBucketName(), objectKey);
                InputStream objectContent = object.getObjectContent();
                value = new BufferedReader(new InputStreamReader(objectContent)).lines()
                    .parallel().collect(Collectors.joining("\n"));
            } catch (Exception e) {
                throw new IllegalStateException("Can't get " + objectKey + " from Obs", e);
            }
        }
        return value;
    }

    @Override
    public boolean exists(String sid) {
        try {
            return obsClient.doesObjectExist(obsBucket.getBucketName(), sid);
        } catch (ObsException e) {
            return false;
        } catch (Exception e) {
            throw new IllegalStateException("Can't check if " + sid + " exists in Obs", e);
        }
    }

    @Override
    public Set<String> services() {
        Set<String> services = new HashSet<>();
        try {
            ObjectListing objectListing = obsClient.listObjects(obsBucket.getBucketName());
            List<ObsObject> obsObjects = objectListing.getObjects();
            if (obsObjects == null || obsObjects.size() == 0) {
                return services;
            }
            for (ObsObject result : obsObjects) {
                services.add(result.getObjectKey());
            }
        } catch (Exception e) {
            throw new IllegalStateException("Can't get the services list from Obs", e);
        }
        return services;
    }

    @Override
    public void remove(String sid) {
        try {
            obsClient.deleteObject(obsBucket.getBucketName(), sid);
        } catch (Exception e) {
            throw new IllegalStateException("Can't remove " + sid + " from Obs", e);
        }
    }

    private String getObjectKey(String sid, String pluginName, String key) {
        return sid + "__" + pluginName + "__" + key;
    }
}
