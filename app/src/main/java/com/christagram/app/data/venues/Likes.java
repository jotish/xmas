
package com.christagram.app.data.venues;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Likes {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("groups")
    @Expose
    private List<Object> groups = null;
    @SerializedName("summary")
    @Expose
    private String summary;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Object> getGroups() {
        return groups;
    }

    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
