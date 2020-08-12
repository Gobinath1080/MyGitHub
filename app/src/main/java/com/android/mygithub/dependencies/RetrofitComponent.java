package com.android.mygithub.dependencies;

import com.android.mygithub.data.GitHubRepository;

import dagger.Component;
import retrofit2.Retrofit;

@Component(modules = {RetrofitModule.class})
public interface RetrofitComponent {

    void inject(GitHubRepository repository);

    Retrofit getRetrofit();
}
