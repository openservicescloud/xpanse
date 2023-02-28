package org.eclipse.xpanse.api;


import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.api.response.Response;
import org.eclipse.xpanse.modules.database.v2.ServiceTaskEntity;
import org.eclipse.xpanse.modules.ocl.v2.Context;
import org.eclipse.xpanse.modules.ocl.v2.OclResource;
import org.eclipse.xpanse.orchestrator.v2.XpanseTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/xpanse/v2")
public class XpanseDeployApi {

    @Autowired
    private XpanseTaskService xpanseTaskService;

    /**
     * Add service.
     *
     * @param serviceName name of service.
     * @param oclResource object of oclResource.
     * @return response
     */
    @PostMapping(value = "/service/{serviceName}",
            consumes = {"application/x-yaml", "application/yaml", "application/yml"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Response createService(@PathVariable("serviceName") String serviceName,
            @RequestBody OclResource oclResource) {
        String successMsg = String.format("add service %s success.", serviceName);
        xpanseTaskService.createService(serviceName, oclResource);
        return Response.successResponse(successMsg);
    }

    /**
     * Delete service.
     *
     * @param taskId      delete of service.
     * @param oclResource object of oclResource.
     * @return response
     */
    @DeleteMapping(value = "/service/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Response deleteService(@PathVariable("taskId") String taskId,
            @Valid @RequestBody OclResource oclResource) {
        String successMsg = String.format("delete service %s success.", taskId);
        xpanseTaskService.deleteService(taskId, oclResource);
        return Response.successResponse(successMsg);
    }

    /**
     * Get service list.
     *
     * @return response
     */
    @GetMapping(value = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response listServices() {
        String successMsg = "List services success.";
        List<ServiceTaskEntity> tasks = xpanseTaskService.listServiceTasks();

        return Response.resultResponse(successMsg, tasks);
    }

    /**
     * Get service monitor data
     *
     * @return response
     */
    @GetMapping(value = "/service/monitor/{vmId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response monitorData(@PathVariable("vmId") String vmId) {
        String successMsg = "Get monitor data success.";
        String monitor = xpanseTaskService.showMonitorData(vmId);
        return Response.resultResponse(successMsg, monitor);
    }

    /**
     * Get status of service with id.
     *
     * @return Status of the service.
     */
    @GetMapping(value = "/service/state/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response getServiceState(@PathVariable("taskId") String taskId) {
        String successMsg = String.format("Get state of service with id %s success.",
                taskId);
        ServiceTaskEntity task = xpanseTaskService.showServiceState(taskId);
        return Response.resultResponse(successMsg, task);
    }


    /**
     * List contexts of the service.
     *
     * @return Contexts of the service.
     */
    @GetMapping(value = "/service/{serviceName}/contexts", produces =
            MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response listServiceContexts(@PathVariable("serviceName") String serviceName) {
        String successMsg = String.format("List contexts of service with name %s success.",
                serviceName);
        List<Context> contexts = xpanseTaskService.listServiceContexts(serviceName);

        return Response.resultResponse(successMsg, contexts);
    }


    /**
     * List flavors of the service.
     *
     * @return Flavors of the service.
     */
    @GetMapping(value = "/service/{serviceName}/flavors", produces =
            MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response listServiceFlavors(@PathVariable("serviceName") String serviceName) {
        String successMsg = String.format("List flavors of service with name %s success.",
                serviceName);
        return Response.resultResponse(successMsg,
                xpanseTaskService.listServiceFlavors(serviceName));
    }

}
