package com.inteacc.bpm.core;

import com.haulmont.cuba.core.sys.events.AppContextStartedEvent;
import com.inteacc.bpm.core.parsehandler.ProcessNotificationBpmnParseHandler;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Class listens for the {@link AppContextStartedEvent} application event and adds the {@link ProcessNotificationBpmnParseHandler} to the BPMN parse
 * handlers list
 */
@Component
public class AppLifecycleBean {

    @Inject
    protected ProcessEngineConfiguration processEngineConfiguration;

    @EventListener
    protected void appStarted(AppContextStartedEvent event) {
        if (processEngineConfiguration instanceof ProcessEngineConfigurationImpl) {
            ((ProcessEngineConfigurationImpl) processEngineConfiguration).getBpmnDeployer()
                    .getBpmnParser().getBpmnParserHandlers().addHandler(new ProcessNotificationBpmnParseHandler());
        }
    }
}
