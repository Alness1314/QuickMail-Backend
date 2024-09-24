package com.alness.quickmail.users.specification;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.alness.quickmail.users.entity.UserEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserSpecification implements Specification<UserEntity>{

    @Override
    public Predicate toPredicate(Root<UserEntity> arg0, CriteriaQuery<?> arg1, CriteriaBuilder arg2) {
       return null;
    }

    public Specification<UserEntity> getSpecificationByFilters(Map<String, String> params) {

		Specification<UserEntity> specification = Specification.where(null);
		for (Entry<String, String> entry : params.entrySet()) {

			switch (entry.getKey()) {
				case "id":
					specification = specification.and(this.filterById(entry.getValue()));
					break;
				case "expiration":
					specification = specification.and(this.filterByExpiration(entry.getValue()));
					break;

				default:
					break;
			}
		}
		return specification;
	}


    private Specification<UserEntity> filterById(String id) {
		return (root, query, cb) -> cb.equal(root.<UUID>get("id"), UUID.fromString(id));
	}

    private Specification<UserEntity> filterByExpiration(String expiration) {
		return (root, query, cb) -> cb.equal(root.<Boolean>get("sendExpirationAlert"), Boolean.valueOf(expiration));
	}
    
}
