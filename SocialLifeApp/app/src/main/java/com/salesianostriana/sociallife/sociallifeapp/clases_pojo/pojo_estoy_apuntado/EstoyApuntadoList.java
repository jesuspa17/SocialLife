package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Pallares on 08/05/2016.
 */
public class EstoyApuntadoList {

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
    private List<EstoyApuntado> results = new ArrayList<EstoyApuntado>();

    /**
     * No args constructor for use in serialization
     *
     */
    public EstoyApuntadoList() {
    }

    /**
     *
     * @param results
     * @param previous
     * @param count
     * @param next
     */
    public EstoyApuntadoList(Integer count, Object next, Object previous, List<EstoyApuntado> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The next
     */
    public Object getNext() {
        return next;
    }

    /**
     *
     * @param next
     * The next
     */
    public void setNext(Object next) {
        this.next = next;
    }

    /**
     *
     * @return
     * The previous
     */
    public Object getPrevious() {
        return previous;
    }

    /**
     *
     * @param previous
     * The previous
     */
    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    /**
     *
     * @return
     * The results
     */
    public List<EstoyApuntado> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<EstoyApuntado> results) {
        this.results = results;
    }

}
