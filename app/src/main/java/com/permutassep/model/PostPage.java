package com.permutassep.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class PostPage {

    @Expose
    private Long count;
    @Expose
    private String next;
    @Expose
    private String previous;
    @Expose
    private List<Post> results;


    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Post> getResults() {
        return results;
    }

    public void setResults(List<Post> results) {
        this.results = results;
    }
}