package com.charles.issuetracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.charles.issuetracker.R;
import com.charles.issuetracker.R;
import com.charles.issuetracker.adapter.IssueAdapter;
import com.charles.issuetracker.pojo.Issues;
import com.charles.issuetracker.rest.RestClient;
import com.charles.issuetracker.adapter.IssueAdapter;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;



import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    IssueAdapter issueAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.issueList);
        loadData();
    }

    private void loadData() {
        RestClient.getIssueGateway().getIssues(new Callback<List<Issues>>() {
            @Override
            public void success(List<Issues> issues, Response response) {
                Collections.sort(issues, new Comparator<Issues>() {
                    @Override
                    public int compare(Issues o1, Issues o2) {
                        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        try{
                            Date d1 = format.parse(o1.getUpdatedAt());
                            Date d2 = format.parse(o2.getUpdatedAt());
                            return d1.compareTo(d2);
                        } catch (Exception e) {

                        }
                        return 0;
                    }
                });
                loadView(issues);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(error.getUrl(), error.toString());
            }
        });
    }

    private void loadView(List<Issues> issues) {
        issueAdapter = new IssueAdapter(issues, getSupportFragmentManager(), this);
        recyclerView.setAdapter(issueAdapter);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Loading...");
        }
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        progressDialog.hide();
    }
}