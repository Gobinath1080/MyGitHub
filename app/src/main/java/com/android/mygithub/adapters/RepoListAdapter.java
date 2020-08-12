package com.android.mygithub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mygithub.R;
import com.android.mygithub.data.Repository;

import java.util.List;

import static com.android.mygithub.utilities.Constants.PREFIX_LAST_UPDATED;
import static com.android.mygithub.utilities.Constants.PREFIX_OPEN_ISSUE_COUNT;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder> {

    private List<Repository> repositoryList;

    public RepoListAdapter(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
    }

    public void setRepositoryList(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bind(repositoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {

        TextView repoNameView;
        TextView repoDescriptionView;
        TextView repoUrlView;
        TextView lastUpdatedDateView;
        TextView openIssueCountView;


        RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            repoNameView = itemView.findViewById(R.id.repoName);
            repoDescriptionView = itemView.findViewById(R.id.repoDescription);
            repoUrlView = itemView.findViewById(R.id.repoUrl);
            lastUpdatedDateView = itemView.findViewById(R.id.lastUpdateDate);
            openIssueCountView = itemView.findViewById(R.id.openIssueCount);
        }

        void bind(Repository repository) {
            repoNameView.setText(repository.getName());
            repoDescriptionView.setText(repository.getDescription());
            repoUrlView.setText(repository.getUrl());
            openIssueCountView.setText(PREFIX_OPEN_ISSUE_COUNT + repository.getOpenIssueCount());
            lastUpdatedDateView.setText(PREFIX_LAST_UPDATED + repository.getLastUpdateDate());
        }
    }
}
