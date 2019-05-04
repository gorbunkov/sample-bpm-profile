package com.inteacc.bpm.web.procactions;

import com.haulmont.bpm.BpmConstants;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.bpm.form.ProcFormDefinition;
import com.haulmont.bpm.gui.action.CompleteProcTaskAction;
import com.haulmont.bpm.service.BpmEntitiesService;
import com.haulmont.bpm.service.ProcessFormService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.inteacc.bpm.entity.BpmProfile;
import com.inteacc.bpm.service.BpmProfileService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.haulmont.cuba.gui.screen.EditorScreen.WINDOW_COMMIT_AND_CLOSE;

@UiController("bp_ProcButtonsFragment")
@UiDescriptor("proc-buttons-fragment.xml")
public class ProcButtonsFragment extends ScreenFragment {

    @Inject
    protected VBoxLayout actionsBox;

    @Inject
    protected BpmEntitiesService bpmEntitiesService;

    @Inject
    protected BpmProfileService bpmProfileService;

    @Inject
    protected ProcessFormService processFormService;

    @Inject
    protected UiComponents uiComponents;

    @Inject
    protected Notifications notifications;

    /**
     * The method initializes the process buttons layout.
     * <p>
     * If entity is persisted and there is no related BpmProfile found, then no buttons are displayed.
     * <p>
     * If BpmProfile is found and there is no active ProcInstance related to the entity, then the "Start process" button is displayed.
     * <p>
     * If there are active user tasks for the current user, then corresponding buttons are displayed.
     * <p>
     * If theere is an active process and there are no tasks for the current user, then the editor is disabled.
     *
     * @param entity
     */
    public void initLayout(Entity entity) {
        actionsBox.removeAll();
        BpmProfile bpmProfile = bpmProfileService.findBpmProfileByEntityName(entity.getMetaClass().getName());
        if (bpmProfile == null) {
            return;
        }
        List<ProcInstance> activeProcessInstances = bpmEntitiesService.findActiveProcInstancesForEntity(
                bpmProfile.getProcDefinition().getCode(),
                entity,
                BpmConstants.Views.PROC_INSTANCE_FULL);
        if (activeProcessInstances.isEmpty()) {
            initStartProcessLayout(entity);
        } else {
            ProcInstance procInstance = activeProcessInstances.get(0);
            List<ProcTask> activeProcTasks = bpmEntitiesService.findActiveProcTasksForCurrentUser(procInstance,
                    BpmConstants.Views.PROC_TASK_COMPLETE);
            if (!activeProcTasks.isEmpty()) {
                initCompleteTaskLayout(entity, activeProcTasks);
            } else {
                makeEditorReadOnly();
            }
        }
    }

    private void initStartProcessLayout(Entity entity) {
        Button startProcessBtn = uiComponents.create(Button.class);
        startProcessBtn.setCaption("Start process");
        startProcessBtn.setWidth("100%");
        startProcessBtn.setAction(new BaseAction("startProcess") {
            @Override
            public void actionPerform(Component component) {
                getScreenData().getDataContext().commit();
                bpmProfileService.startProcessUsingBpmProfile(entity);
                notifications.create(Notifications.NotificationType.HUMANIZED)
                        .withCaption("Process started")
                        .show();
                initLayout(entity);
            }
        });
        actionsBox.add(startProcessBtn);
    }

    private void initCompleteTaskLayout(Entity entity, List<ProcTask> activeProcTasks) {
        ProcTask procTask = activeProcTasks.get(0);
        List<CompleteProcTaskAction> completeProcTaskActions = new ArrayList<>();
        Map<String, ProcFormDefinition> outcomesWithForms = processFormService.getOutcomesWithForms(procTask);
        if (!outcomesWithForms.isEmpty()) {
            for (Map.Entry<String, ProcFormDefinition> entry : outcomesWithForms.entrySet()) {
//                CompleteProcTaskAction action = new CompleteProcTaskAction(procTask, entry.getKey(), entry.getValue());
                CompleteProcTaskAction action = new CompleteProcTaskAction(procTask, entry.getKey(), null);
                completeProcTaskActions.add(action);
            }
        } else {
//            ProcFormDefinition form = processFormService.getDefaultCompleteTaskForm(procInstance.getProcDefinition());
//            CompleteProcTaskAction action = new CompleteProcTaskAction(procTask, BpmConstants.DEFAULT_TASK_OUTCOME, form);
            CompleteProcTaskAction action = new CompleteProcTaskAction(procTask, BpmConstants.DEFAULT_TASK_OUTCOME, null);
            action.setCaption("Complete task");
            completeProcTaskActions.add(action);
        }
        for (CompleteProcTaskAction completeProcTaskAction : completeProcTaskActions) {
            Button actionBtn = uiComponents.create(Button.class);
            actionBtn.setWidth("100%");
            completeProcTaskAction.addAfterActionListener(() -> {
                initLayout(entity);
                getScreenData().getDataContext().commit();
                notifications.create(Notifications.NotificationType.HUMANIZED)
                        .withCaption("Task completed")
                        .show();

            });
            actionBtn.setAction(completeProcTaskAction);
            actionsBox.add(actionBtn);
        }
    }

    private void makeEditorReadOnly() {
        Action commitCloseAction = getHostScreen().getWindow().getAction(WINDOW_COMMIT_AND_CLOSE);
        if (commitCloseAction != null) {
            commitCloseAction.setEnabled(false);
        }
    }
}