package org.eclipse.xpanse.modules.engine.terraform;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.engine.BuilderContext;
import org.eclipse.xpanse.modules.engine.XpanseDeployEngine;
import org.eclipse.xpanse.modules.engine.XpanseTask;
import org.eclipse.xpanse.modules.engine.ocl.XpanseResource;
import org.eclipse.xpanse.modules.engine.xpresource.XpResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

@Slf4j
@Component
public class TFDeployEngine implements XpanseDeployEngine {

    private BuilderContext context;

    @Autowired
    public TFDeployEngine(BuilderContext context) {
        this.context = context;
    }

    @Override
    public List<XpResource> execute(Queue<XpanseTask> taskQueue) {
        List<XpResource> xpanseResources = new ArrayList<>();
        if (taskQueue.isEmpty()) {
            log.warn("xpanse task queue is empty");
            return xpanseResources;
        }
        Iterator<XpanseTask> iterator = taskQueue.iterator();
        while (iterator.hasNext()) {
            XpanseTask xpanseTask = iterator.next();
            List<XpResource> xpanseResource = xpanseTask.execute(context);
            xpanseResources.addAll(xpanseResource);
        }
        return xpanseResources;
    }


}
