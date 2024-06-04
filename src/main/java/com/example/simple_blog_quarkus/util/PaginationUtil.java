package com.example.simple_blog_quarkus.util;

import io.quarkus.panache.common.Sort;

import java.util.List;

public final class PaginationUtil {
    private PaginationUtil(){}

    public static Sort getSortFromQuery(List<String> sortQuery) {
        if (sortQuery == null || sortQuery.isEmpty()) {
            return Sort.empty();
        }

        Sort sort = null;
        for (String sortEntry : sortQuery) {
            String[] parts = sortEntry.split(",");
            String field = parts[0];
            Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) ? Sort.Direction.Descending : Sort.Direction.Ascending;

            if (sort == null) {
                sort = Sort.by(field, direction);
            } else {
                sort.and(field, direction);
            }
        }
        return sort;
    }
}
