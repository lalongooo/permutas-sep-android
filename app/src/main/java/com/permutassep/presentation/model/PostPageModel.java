package com.permutassep.presentation.model;

import com.permutassep.domain.Post;

import java.util.List;

/**
 * Class that represents a PostPage in the presentation layer
 */
public class PostPageModel {

    private long count;
    private String next;
    private String previous;
    private List<Post> results;

    public PostPageModel() {
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

    public List<Post> getResults() {
        return results;
    }

    public void setResults(List<Post> results) {
        this.results = results;
    }
}
