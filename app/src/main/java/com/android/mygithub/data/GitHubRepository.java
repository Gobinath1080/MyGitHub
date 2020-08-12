package com.android.mygithub.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.mygithub.api.RepoService;
import com.android.mygithub.dependencies.DaggerRetrofitComponent;
import com.android.mygithub.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.android.mygithub.utilities.Constants.DEFAULT_USERID;

/**
 * Repository module for handling data operations.
 */
public class GitHubRepository {

    @Inject
    Retrofit retrofit;

    public GitHubRepository() {
        DaggerRetrofitComponent.builder().build().inject(this);
    }

    /**
     * @return LiveData of list of github code repos of default user.
     */
    public LiveData<List<Repository>> getRepositaries() {
        RepoService repoService = retrofit.create(RepoService.class);
        MutableLiveData<List<Repository>> repositories = new MutableLiveData<>();
        repoService.getRepositories(DEFAULT_USERID).observeOn(Schedulers.io()
        ).flatMap(new Function<List<Repository>, ObservableSource<Repository>>() {
            @Override
            public ObservableSource<Repository> apply(List<Repository> repositories) throws Throwable {
                return Observable.fromIterable(repositories).observeOn(Schedulers.io()).subscribeOn(Schedulers.io());
            }
        }).flatMap(new Function<Repository, ObservableSource<Repository>>() {
            @Override
            public ObservableSource<Repository> apply(Repository repository) throws Throwable {
                return getRepoDetails(repoService, DEFAULT_USERID, repository);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Repository>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Repository repository) {
                if (repositories.getValue() == null) {
                    List<Repository> repoList = new ArrayList<>();
                    repositories.setValue(repoList);
                } else {
                    repositories.getValue().add(repository);
                    repositories.postValue(repositories.getValue());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        return repositories;
    }

    /**
     * Create and return Observable that fetch the branches and commits details for repository.
     *
     * @param repoService instance of RepoService
     * @param userId      default user id.
     * @param repository  the repository for which details need to be fetched.
     */
    ObservableSource<Repository> getRepoDetails(RepoService repoService, String userId, Repository repository) {
        return Observable.zip(repoService.getBranches(userId, repository.name).onErrorReturn(new Function<Throwable, List<Branch>>() {
            @Override
            public List<Branch> apply(Throwable throwable) throws Throwable {
                return new ArrayList<>();
            }
        }), repoService.getCommits(userId, repository.name).onErrorReturn(new Function<Throwable, List<CommitInfo>>() {
            @Override
            public List<CommitInfo> apply(Throwable throwable) throws Throwable {
                return new ArrayList<>();
            }
        }), new BiFunction<List<Branch>, List<CommitInfo>, Repository>() {
            @Override
            public Repository apply(List<Branch> branches, List<CommitInfo> commitInfos) throws Throwable {
                repository.branchList = branches;
                repository.noOfCommits = commitInfos.size() + 1;
                return repository;
            }
        }).observeOn(Schedulers.io()).subscribeOn(Schedulers.io());
    }
}
