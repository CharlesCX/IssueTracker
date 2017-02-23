package com.charles.issuetracker.adapter;

/**
 * Created by charles on 2/19/17.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.charles.issuetracker.activity.MainActivity;
import com.charles.issuetracker.dialogs.Dialog;
import com.charles.issuetracker.R;
import com.charles.issuetracker.pojo.Comment;
import com.charles.issuetracker.pojo.Issues;
import com.charles.issuetracker.rest.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.charles.issuetracker.R.id.notice;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder>{
    List<Issues> issues = new ArrayList<>();
    FragmentManager fm;
    Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) v.findViewById(R.id.title);
            desc = (TextView) v.findViewById(R.id.desc);
        }
    }

    public IssueAdapter(List<Issues> issues, FragmentManager fm, Context context) {
        this.issues = issues;
        this.fm = fm;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.issue_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(issues.get(position).getTitle());
        if(issues.get(position).getBody().length() > 140) {
            holder.desc.setText(Html.fromHtml(issues.get(position).getBody().substring(0, 140)));
        } else
            holder.desc.setText(Html.fromHtml(issues.get(position).getBody()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] sec = issues.get(position).getCommentsUrl().split("/");
                String id = Arrays.asList(sec).get(sec.length-2);
                ((MainActivity)context).showProgress();
                RestClient.getIssueGateway().getComment(id, new Callback<List<Comment>>() {
                    @Override
                    public void success(List<Comment> comments, Response response) {
                        Dialog dialog = new Dialog();
                        dialog.setComments(comments);
                        dialog.show(fm, "comments");
                        ((MainActivity) context).hideProgress();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(context, "ops, something is wrong", Toast.LENGTH_SHORT).show();
                        ((MainActivity) context).hideProgress();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return issues.size();
    }
}