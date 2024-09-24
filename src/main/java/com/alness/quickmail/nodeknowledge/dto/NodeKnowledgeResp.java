package com.alness.quickmail.nodeknowledge.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeKnowledgeResp implements Serializable{
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String tag;
    private String description;
    private List<Map<String, Object>> article;

    @Override
    public String toString() {
        return "NodeKnowledgeResp [id=" + id + ", name=" + name + ", tag=" + tag + "]";
    }
    
}
