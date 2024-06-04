package com.example.simple_blog_quarkus.dto;

import java.util.List;

public class PageOutput<T> {
    private List<T> content;
    private Long totalPages;
    private Long totalElements;
    private Long size;
    private Long number;
    private Long numberOfElements;
    private boolean first;
    private boolean last;
    private boolean empty;

    public PageOutput(List<T> content, Long totalPages, Long totalElements, int size, int number, Long numberOfElements, boolean first, boolean last, boolean empty) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = (long) size;
        this.number = (long) number;
        this.numberOfElements = numberOfElements;
        this.first = first;
        this.last = last;
        this.empty = empty;
    }

    public static <T> PageOutput<T> of(List<T> content, int pageIndex, int pageSize, Long totalElements) {
        Long totalPages = (totalElements + pageSize - 1) / pageSize; // Calculate total pages
        Long numberOfElements = (long) content.size();
        boolean first = pageIndex == 0;
        boolean last = pageIndex == totalPages - 1;
        boolean empty = numberOfElements == 0;

        return new PageOutput<>(content, totalPages, totalElements, pageSize, pageIndex, numberOfElements, first, last, empty);
    }

    public List<T> getContent() {
        return content;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Long getSize() {
        return size;
    }

    public Long getNumber() {
        return number;
    }

    public Long getNumberOfElements() {
        return numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public boolean isEmpty() {
        return empty;
    }
}
