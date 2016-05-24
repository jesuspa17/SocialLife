package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_seguidos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Pallares on 22/05/2016.
 */
public class SeguidosSimpleList {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private Object next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<SeguidoSimple> results = new ArrayList<SeguidoSimple>();

    /**
     * No args constructor for use in serialization
     */
    public SeguidosSimpleList() {
    }

    /**
     * @param results
     * @param previous
     * @param count
     * @param next
     */
    public SeguidosSimpleList(Integer count, Object next, Object previous, List<SeguidoSimple> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return The next
     */
    public Object getNext() {
        return next;
    }

    /**
     * @param next The next
     */
    public void setNext(Object next) {
        this.next = next;
    }

    /**
     * @return The previous
     */
    public Object getPrevious() {
        return previous;
    }

    /**
     * @param previous The previous
     */
    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    /**
     * @return The results
     */
    public List<SeguidoSimple> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<SeguidoSimple> results) {
        this.results = results;
    }

}

