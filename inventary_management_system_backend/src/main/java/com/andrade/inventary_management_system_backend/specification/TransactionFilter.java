package com.andrade.inventary_management_system_backend.specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.andrade.inventary_management_system_backend.domain.Transaction;

public class TransactionFilter {

    public static Specification<Transaction> filterByValue(String searchValue) {
        return ((root, query, criterialBuilder) -> {
            if (searchValue == null || searchValue.isEmpty()) {
                return criterialBuilder.conjunction(); // basically where 1=1, return all
            }
            String seachPattern = "%" + searchValue.toLowerCase() + "%";

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criterialBuilder
                    .like(criterialBuilder.lower(root.get("description")), seachPattern));
            predicates.add(criterialBuilder
                    .like(criterialBuilder.lower(root.get("transactionStatus").as(String.class)), seachPattern));
            predicates.add(criterialBuilder
                    .like(criterialBuilder.lower(root.get("note")), seachPattern));
            predicates.add(criterialBuilder
                    .like(criterialBuilder.lower(root.get("transactionType").as(String.class)), seachPattern));

            // user
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("user"))) { // checking if
                                                                                                      // there is a
                                                                                                      // joint already
                root.join("user", JoinType.LEFT);
            }

            var userJoin = root.join("user", JoinType.LEFT);

            predicates.add(criterialBuilder
                    .like(criterialBuilder
                            .lower(userJoin.get("name")), seachPattern));

            predicates.add(criterialBuilder
                    .like(criterialBuilder
                            .lower(userJoin.get("phoneNumber")), seachPattern));

            predicates.add(criterialBuilder
                    .like(criterialBuilder
                            .lower(userJoin.get("email")), seachPattern));

            // supplier
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("supplier"))) { // checking if
                                                                                                          // there is a
                                                                                                          // joint
                                                                                                          // already
                root.join("supplier", JoinType.LEFT);
            }

            var supplierJoin = root.join("supplier", JoinType.LEFT);

            predicates.add(criterialBuilder
                    .like(criterialBuilder
                            .lower(supplierJoin.get("name")), seachPattern));

            predicates.add(criterialBuilder
                    .like(criterialBuilder
                            .lower(supplierJoin.get("contactInfo")), seachPattern));

            predicates.add(criterialBuilder
                    .like(criterialBuilder
                            .lower(supplierJoin.get("adress")), seachPattern));

            // product
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("product"))) { // checking if
                                                                                                         // there is a
                                                                                                         // joint
                                                                                                         // already
                root.join("product", JoinType.LEFT);
            }

            var produtJoin = root.join("product", JoinType.LEFT);

            predicates.add(criterialBuilder
                    .like(criterialBuilder
                            .lower(produtJoin.get("expireDate")), seachPattern));

            predicates.add(criterialBuilder
                    .like(criterialBuilder
                            .lower(produtJoin.get("sku")), seachPattern));

            predicates.add(criterialBuilder
                    .like(criterialBuilder
                            .lower(produtJoin.get("name")), seachPattern));

            predicates.add(criterialBuilder
                    .like(criterialBuilder
                            .lower(produtJoin.get("price")), seachPattern));

            return criterialBuilder.or(predicates.toArray(new Predicate[0]));
        }

        );
    }

}
