package android.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.example.bakingapp.Data.Ingredients;
import android.example.bakingapp.Data.Recipe;
import android.example.bakingapp.Data.Steps;
import android.example.bakingapp.Widget.AppWidget;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.ItemViewHolder> {

    Context context;
    ArrayList<Recipe> Recipes,recipeArrayList;
    ArrayList<Steps> steps;
    ArrayList<Ingredients> ingredients;
    Recipe recipe;
    public static final String KEY_DETAILS="Details";
    public static final String KEY_POS="position";
    public static final String KEY_ING="ingredients";
    public static final String KEY_REC="recipeArrayList";

    public MasterListAdapter(Context context, ArrayList<Recipe> r){
        this.context=context;
        this.Recipes= r;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_main_recycle,viewGroup,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int i) {
        if (!TextUtils.isEmpty(Recipes.get(i).getImage())) {
            Picasso.get().load(Recipes.get(i).getImage()).into(itemViewHolder.imageView);
        } else {


            switch (i) {
                case 0:
                    itemViewHolder.imageView.setImageResource(R.drawable.nutella_pie);
                    break;
                case 1:
                    itemViewHolder.imageView.setImageResource(R.drawable.brownies);
                    break;
                case 2:
                    itemViewHolder.imageView.setImageResource(R.drawable.yellow_cake);
                    break;
                case 3:
                    itemViewHolder.imageView.setImageResource(R.drawable.cheesecake);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return Recipes.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
       ImageView imageView;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_recycle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Recipes!=null){
                    int i=getAdapterPosition();
                         recipe = Recipes.get(i);
                         ingredients =new ArrayList<>();
                           steps= new ArrayList<>();
                           steps.addAll(Recipes.get(i).getSteps());
                           ingredients.addAll(Recipes.get(i).getIngredients());
                    }
                    Bundle bundle=new Bundle();
                    setUpWidget(context,ingredients);

                    Intent intent=new Intent(context,DetailActivity.class);

                    bundle.putParcelable(KEY_REC,recipe);
                    bundle.putParcelableArrayList(KEY_DETAILS, steps);
                    bundle.putParcelableArrayList(KEY_ING,ingredients);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }

    }
        private void setUpWidget(Context context,ArrayList<Ingredients>ingredients){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            ComponentName thisWidget = new ComponentName(context, AppWidget.class);

            remoteViews.setTextViewText(R.id.recipe_name
                    , recipe.getName());

            for (Ingredients ingredient:ingredients) {
                RemoteViews ingredientView=new RemoteViews(context.getPackageName(),R.layout.list_widget);
                ingredientView.setTextViewText(R.id.tv_widget_ingredients,
                        String.valueOf(ingredient.getQuantity())+" "+
                                String.valueOf(ingredient.getMeasure())+" "+
                                String.valueOf(ingredient.getIngredient()));
                remoteViews.addView(R.id.widget_container,ingredientView);
            }
            appWidgetManager.updateAppWidget(thisWidget, remoteViews);
        }
}



