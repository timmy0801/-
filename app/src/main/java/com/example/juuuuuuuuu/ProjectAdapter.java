package com.example.juuuuuuuuu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> implements Filterable {
    private Context mContext;
    private ArrayList<Project> projects,projectsfull;
    private OnItemClickListener clickListener;
    public ProjectAdapter(Context mContext, ArrayList<Project> project) {
        this.mContext = mContext;
        this.projects = project;
        projectsfull=new ArrayList<>(projects);

    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnclicklistener(OnItemClickListener projectlisrener){
        clickListener=projectlisrener;
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_single_project,parent,false);
        return new ProjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        Project project=projects.get(position);
        holder.projectname.setText(project.getName());
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Project").child(project.getProject_id());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("friends")){
                    int count=(int)dataSnapshot.child("friends").getChildrenCount();
                    holder.project_people.setText(Integer.toString(count+1));
                }else{
                    holder.project_people.setText("1");
                }
                if (dataSnapshot.hasChild("description")){
                    holder.project_note_single.setText(dataSnapshot.child("description").getValue().toString());
                }else {
                    holder.project_note_single.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return projects.size();
    }
    @Override
    public Filter getFilter() {
        return Projectfilter;
    }
    private Filter Projectfilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Project> filterlist=new ArrayList<>();
            if (charSequence==null || charSequence.length()==0){
                filterlist.addAll(projectsfull);
            }else {
                String FilterPattern=charSequence.toString().toLowerCase().trim();
                for (Project project:projectsfull){
                    if (project.getName().toLowerCase().contains(FilterPattern)){
                        filterlist.add(project);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterlist;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            projects.clear();
            projects.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Project)resultValue).getName();
        }
    };

    public  class ProjectHolder extends RecyclerView.ViewHolder{
        public ImageView projectImage,people_image;
        public TextView projectname,project_people,project_note_single;

        public ProjectHolder(@NonNull View itemView) {
            super(itemView);
            projectImage=itemView.findViewById(R.id.project_image);
            projectname=itemView.findViewById(R.id.display_project_name);
            project_people=itemView.findViewById(R.id.project_people);
            people_image=itemView.findViewById(R.id.people_image);
            project_note_single=itemView.findViewById(R.id.project_note_single);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            clickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
