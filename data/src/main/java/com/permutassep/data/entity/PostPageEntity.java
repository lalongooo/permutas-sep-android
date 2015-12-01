package com.permutassep.data.entity;

import com.google.gson.annotations.Expose;

import java.util.List;

public class PostPageEntity {

    @Expose
    private long count;
    @Expose
    private String next;
    @Expose
    private String previous;
    @Expose
    private List<PostEntity> results;

    public PostPageEntity() {
        // empty
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
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

    public List<PostEntity> getResults() {
        return results;
    }

    public void setResults(List<PostEntity> results) {
        this.results = results;
    }
}
