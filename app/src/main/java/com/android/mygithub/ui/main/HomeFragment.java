package com.android.mygithub.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mygithub.R;
import com.android.mygithub.adapters.RepoListAdapter;
import com.android.mygithub.data.Repository;
import com.android.mygithub.viewmodels.HomeViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    private RecyclerView repoListView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repoListView = view.findViewById(R.id.repoListView);
        repoListView.setAdapter(new RepoListAdapter(new ArrayList<>()));
        repoListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        repoListView.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.getRepositoryList().observe(getViewLifecycleOwner(), new Observer<List<Repository>>() {
            @Override
            public void onChanged(List<Repository> repositories) {
                ((RepoListAdapter)repoListView.getAdapter()).setRepositoryList(repositories);
                (repoListView.getAdapter()).notifyDataSetChanged();
            }
        });
    }
}
