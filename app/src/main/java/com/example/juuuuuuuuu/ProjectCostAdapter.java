package com.example.juuuuuuuuu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juuuuuuuuu.Post;
import com.example.juuuuuuuuu.Project;
import com.example.juuuuuuuuu.ProjectAdapter;
import com.example.juuuuuuuuu.R;

import java.util.ArrayList;

public class ProjectCostAdapter extends  RecyclerView.Adapter<ProjectCostAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Post> postlist;
    private OnItemClickListener clickListener;
    public ProjectCostAdapter(Context mContext, ArrayList<Post> postlist) {
        this.mContext = mContext;
        this.postlist = postlist;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnclicklistener(OnItemClickListener toto){
        clickListener=toto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_project_category,parent,false);
        return new ProjectCostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post=postlist.get(position);
        holder.project_cost_type.setText(post.getType());
        holder.project_cost_date.setText(post.getDate());
        holder.project_singlecost.setText("$"+post.getCost());
    }

    @Override
    public int getItemCount() {
        return postlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView project_cost_type,project_singlecost,project_cost_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            project_cost_type=itemView.findViewById(R.id.project_cost_type);
            project_singlecost=itemView.findViewById(R.id.project_singlecost);
            project_cost_date=itemView.findViewById(R.id.project_cost_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
