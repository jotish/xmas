
package com.christagram.app.data.venues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeenHere {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("marked")
    @Expose
    private Boolean marked;
    @SerializedName("lastCheckinExpiredAt")
    @Expose
    private Integer lastCheckinExpiredAt;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getMarked() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }

    public Integer getLastCheckinExpiredAt() {
        return lastCheckinExpiredAt;
    }

    public void setLastCheckinExpiredAt(Integer lastCheckinExpiredAt) {
        this.lastCheckinExpiredAt = lastCheckinExpiredAt;
    }

}
