package com.charles.issuetracker.adapter;

/**
 * Created by charles on 2/19/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charles.issuetracker.R;
import com.charles.issuetracker.pojo.Comment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;



public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> comments;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView mUserName;
        private TextView mDate;
        private TextView mComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mDate = (TextView) itemView.findViewById(R.id.comment_date);
            mComment = (TextView) itemView.findViewById(R.id.comment_content);
        }
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_view, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.mUserName.setText(comment.getUser().getLogin());
        holder.mDate.setText(convertDateFormat(comment.getCreatedAt()));
        holder.mComment.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    private String convertDateFormat(String original) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        DateFormat resultFormat = new SimpleDateFormat("yyyy-MMM-dd, HH:mm:ss", Locale.US);
        return resultFormat.format(format.parse(original, new ParsePosition(0)));
    }
}
