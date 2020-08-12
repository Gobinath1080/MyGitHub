package com.android.mygithub.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Models the github source repository.
 */
public class Repository {

    @SerializedName("name")
    String name;
    int noOfCommits;
    @SerializedName("description")
    String description;
    @SerializedName("url")
    String url;
    @SerializedName("open_issues_count")
    int openIssueCount;
    @SerializedName("updated_at")
    String lastUpdateDate;
    List<Branch> branchList;

    public String getName() {
        return name;
    }

    public int getNoOfCommits() {
        return noOfCommits;
    }

    public int getBranchCount(){
        return branchList == null ? 0 : branchList.size();
    }

    public int getOpenIssueCount() {
        return openIssueCount;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public static class Owner{

        @SerializedName("login")
        String name;

        @SerializedName("avatar_url")
        String avatarUrl;
    }
}
