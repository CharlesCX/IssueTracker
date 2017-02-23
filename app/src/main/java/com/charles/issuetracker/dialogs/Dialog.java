package com.charles.issuetracker.dialogs;

/**
 * Created by charles on 2/19/17.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.charles.issuetracker.R;
import com.charles.issuetracker.adapter.CommentAdapter;
import com.charles.issuetracker.pojo.Comment;
import com.charles.issuetracker.rest.RestClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Dialog extends DialogFragment {

    private RecyclerView recyclerView;
    private TextView notice;
    private List<Comment> comments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        notice = (TextView) view.findViewById(R.id.notice);
        CommentAdapter adapter = new CommentAdapter(getActivity(), comments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (comments == null || comments.size() == 0) {
            notice.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        android.app.Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        return dialog;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
