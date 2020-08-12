package com.android.mygithub.api;

import com.android.mygithub.data.Branch;
import com.android.mygithub.data.CommitInfo;
import com.android.mygithub.data.Repository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RepoService {

    @GET("/users/{userId}/repos")
    Observable<List<Repository>> getRepositories(@Path("userId") String userId);

    @GET("repos/{userId}/{repoName}/branches")
    Observable<List<Branch>> getBranches(@Path("userId") String userId, @Path("repoName") String repoName);

    @GET("repos/{userId}/{repoName}/commits")
    Observable<List<CommitInfo>> getCommits(@Path("userId") String userId, @Path("repoName") String repoName);
}
