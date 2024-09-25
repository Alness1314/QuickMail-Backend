package com.alness.quickmail.history.specification;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.alness.quickmail.history.entity.HistoryEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class HistorySpecification implements Specification<HistoryEntity> {

    @Override
    public Predicate toPredicate(Root<HistoryEntity> arg0, CriteriaQuery<?> arg1, CriteriaBuilder arg2) {
        return null;
    }

    public Specification<HistoryEntity> getSpecificationByFilters(Map<String, String> params) {

        Specification<HistoryEntity> specification = Specification.where(null);
        for (Entry<String, String> entry : params.entrySet()) {

            switch (entry.getKey()) {
                case "id":
                    specification = specification.and(this.filterById(entry.getValue()));
                    break;
                case "file":
                    specification = specification.and(this.filterByFileId(entry.getValue()));
                    break;
                case "valid":
                    specification = specification.and(this.filterByIsValid(entry.getValue()));
                    break;
                case "send":
                    specification = specification.and(this.filterByIsSend(entry.getValue()));
                    break;

                default:
                    break;
            }
        }
        return specification;
    }

    private Specification<HistoryEntity> filterById(String id) {
        return (root, query, cb) -> cb.equal(root.<UUID>get("id"), UUID.fromString(id));
    }

    private Specification<HistoryEntity> filterByFileId(String fileId) {
        return (root, query, cb) -> cb.equal(root.<UUID>get("fileId"), UUID.fromString(fileId));
    }

    private Specification<HistoryEntity> filterByIsValid(String valid) {
        return (root, query, cb) -> cb.equal(root.<Boolean>get("isValid"), Boolean.parseBoolean(valid));
    }

    private Specification<HistoryEntity> filterByIsSend(String sendAlert) {
        return (root, query, cb) -> cb.equal(root.<Boolean>get("sendAlert"), Boolean.parseBoolean(sendAlert));
    }

}
