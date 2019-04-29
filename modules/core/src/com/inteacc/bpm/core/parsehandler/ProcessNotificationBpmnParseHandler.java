package com.inteacc.bpm.core.parsehandler;

import com.inteacc.bpm.core.eventlistener.NotificationActivitiEventListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.AbstractBpmnParseHandler;

/**
 * The BPMN parse handler that registers the {@link NotificationActivitiEventListener} event listener for every process.
 */
public class ProcessNotificationBpmnParseHandler extends AbstractBpmnParseHandler<Process> {

    @Override
    protected Class<? extends BaseElement> getHandledType() {
        return Process.class;
    }

    @Override
    protected void executeParse(BpmnParse bpmnParse, Process process) {
        EventListener eventListener = new EventListener();
        eventListener.setEvents("TASK_ASSIGNED");
        eventListener.setImplementation(NotificationActivitiEventListener.class.getCanonicalName());
        eventListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);

        ActivitiEventType[] types = ActivitiEventType.getTypesFromString(eventListener.getEvents());
        bpmnParse.getCurrentProcessDefinition().getEventSupport().addEventListener(
                bpmnParse.getListenerFactory().createClassDelegateEventListener(eventListener),types);
    }
}
