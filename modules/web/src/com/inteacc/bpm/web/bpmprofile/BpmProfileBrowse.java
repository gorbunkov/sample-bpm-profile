package com.inteacc.bpm.web.bpmprofile;

import com.haulmont.cuba.gui.screen.*;
import com.inteacc.bpm.entity.BpmProfile;

@UiController("bp_BpmProfile.browse")
@UiDescriptor("bpm-profile-browse.xml")
@LookupComponent("bpmProfilesTable")
@LoadDataBeforeShow
public class BpmProfileBrowse extends StandardLookup<BpmProfile> {
}