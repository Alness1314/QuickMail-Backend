package com.alness.quickmail.nodeknowledge.specification;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.alness.quickmail.nodeknowledge.entity.NodeKnowledgeEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class NodeKnowledgeSpecification implements Specification<NodeKnowledgeEntity>{

    @Override
    public Predicate toPredicate(Root<NodeKnowledgeEntity> arg0, CriteriaQuery<?> arg1, CriteriaBuilder arg2) {
       return null;
    }


    public Specification<NodeKnowledgeEntity> getSpecificationByFilters(Map<String, String> params) {

		Specification<NodeKnowledgeEntity> specification = Specification.where(null);
		for (Entry<String, String> entry : params.entrySet()) {

			switch (entry.getKey()) {
				case "id":
					specification = specification.and(this.filterById(entry.getValue()));
					break;
				case "articulo":
					specification = specification.and(this.hasArticulo(entry.getValue()));
					break;

				default:
					break;
			}
		}
		return specification;
	}

    private Specification<NodeKnowledgeEntity> filterById(String id) {
		return (root, query, cb) -> cb.equal(root.<UUID>get("id"), UUID.fromString(id));
	}

    // Filtra si el artículo es null o no, dependiendo del valor de `hasArticulo`
    private Specification<NodeKnowledgeEntity> hasArticulo(String hasArticulo) {
        return (root, query, criteriaBuilder) -> {
            if (Boolean.TRUE.equals(Boolean.valueOf(hasArticulo))) {
                // Si hasArticulo es true, devuelve solo los que tienen 'articulo' no null
                return criteriaBuilder.isNotNull(root.get("article"));
            } else {
                // Si hasArticulo es false, devuelve solo los que tienen 'articulo' null
                return criteriaBuilder.isNull(root.get("article"));
            }
        };
    }
    
}
