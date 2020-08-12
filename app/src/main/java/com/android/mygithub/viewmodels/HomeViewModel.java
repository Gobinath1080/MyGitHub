package com.android.mygithub.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.mygithub.data.GitHubRepository;
import com.android.mygithub.data.Repository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    LiveData<List<Repository>> repositoryList;

    GitHubRepository gitHubRepository;

    public HomeViewModel() {
        gitHubRepository = new GitHubRepository();
    }

    public LiveData<List<Repository>> getRepositoryList() {
        repositoryList = gitHubRepository.getRepositaries();
        return repositoryList;
    }
}
