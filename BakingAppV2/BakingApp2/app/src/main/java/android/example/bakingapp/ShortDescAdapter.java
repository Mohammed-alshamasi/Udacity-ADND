package android.example.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.example.bakingapp.Data.Steps;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.example.bakingapp.DetailActivity.isTablet;

public class ShortDescAdapter extends RecyclerView.Adapter<ShortDescAdapter.ItemViewHolder> {
    Context context;
    ArrayList<Steps> steps;
    final static String KEY_STEP="step";
    public ShortDescAdapter(Context context, ArrayList<Steps> steps) {
        this.context = context;
        this.steps=steps;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_detail_recycle,viewGroup,false);
        return new ShortDescAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.textViewIntro.setText(steps.get(i).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();

    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView textViewIntro;


        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewIntro=itemView.findViewById(R.id.text_view_recycle_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (steps!=null){
                    Bundle bundle=new Bundle();
                    Steps step=steps.get(getAdapterPosition());
                    bundle.putParcelable(KEY_STEP,step);

                  if (isTablet){
                      DescFragment descFragment =new DescFragment();
                      VideoFragment videoFragment=new VideoFragment();
                      videoFragment.setStep(step);
                      descFragment.setStep(step);
                      AppCompatActivity activity = (AppCompatActivity) v.getContext();
                      activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_vid,videoFragment).commit();
                      activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_text,descFragment).commit();
                  }
                  else{
                      Intent intent= new Intent(context, VideoDescActivity.class);
                      intent.putExtras(bundle);
                      context.startActivity(intent);
                  }

                }

                }
            });
        }

    }
}
