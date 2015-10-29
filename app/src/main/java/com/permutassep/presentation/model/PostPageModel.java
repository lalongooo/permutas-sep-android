package com.permutassep.presentation.model;

import java.util.Collection;

/**
 * Class that represents a PostPage in the presentation layer
 */
public class PostPageModel {

    private long count;
    private String next;
    private String previous;
    private Collection<PostModel> results;

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

    public Collection<PostModel> getResults() {
        return results;
    }

    public void setResults(Collection<PostModel> postModelCollection) {
        this.results = postModelCollection;
    }
}
