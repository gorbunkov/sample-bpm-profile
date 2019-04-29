package com.inteacc.bpm.service;


import java.util.List;

public interface BpmnXmlParsingService {
    String NAME = "bp_BpmnXmlParsingService";

    List<String> getUserTaskIds(String bpmnXml);
}