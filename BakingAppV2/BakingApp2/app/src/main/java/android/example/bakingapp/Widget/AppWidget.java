package android.example.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.example.bakingapp.Data.Ingredients;
import android.example.bakingapp.Data.Steps;
import android.example.bakingapp.MainActivity;
import android.example.bakingapp.R;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import java.util.ArrayList;

import javax.xml.transform.Result;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName, ArrayList<Ingredients> ingredients) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.recipe_name,recipeName);
        views.removeAllViews(R.id.widget_container);

        for (Ingredients ingredient:ingredients) {
            RemoteViews ingredientView=new RemoteViews(context.getPackageName(),R.layout.list_widget);
            ingredientView.setTextViewText(R.id.tv_widget_ingredients,
                    String.valueOf(ingredient.getQuantity())+" "+
                    String.valueOf(ingredient.getMeasure())+" "+
                    String.valueOf(ingredient.getIngredient()));
            views.addView(R.id.widget_container,ingredientView);
        }


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,String name,ArrayList<Ingredients> ingredients) {


        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,name,ingredients);
        }
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

