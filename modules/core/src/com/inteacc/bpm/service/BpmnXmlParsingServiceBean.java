package com.inteacc.bpm.service;

import com.haulmont.bali.util.Dom4j;
import org.dom4j.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(BpmnXmlParsingService.NAME)
public class BpmnXmlParsingServiceBean implements BpmnXmlParsingService {

    @Override
    public List<String> getUserTaskIds(String bpmnXml) {
        Document document = Dom4j.readDocument(bpmnXml);
        XPath xPath = createXPath("//bpmn:userTask/@id");
        List<Node> taskIds = xPath.selectNodes(document);
        return taskIds.stream()
                .map(node -> ((Attribute) node).getValue())
                .collect(Collectors.toList());
    }

    protected XPath createXPath(String expression) {
        XPath xPath = DocumentHelper.createXPath(expression);
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("bpmn", "http://www.omg.org/spec/BPMN/20100524/MODEL");
        xPath.setNamespaceURIs(namespaces);
        return xPath;
    }
}