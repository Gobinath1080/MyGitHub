package com.android.mygithub.data;

import com.google.gson.annotations.SerializedName;

/**
 * Models the code commit details
 */
public class CommitInfo {

    @SerializedName("sha")
    String id;

    @SerializedName("node_id")
    String nodeId;
}
