package com.example.rioger.cliente;

/**
 * Created by Rioger on 07/11/2016.
 */

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.rioger.cliente.MovieContract.tablaMovie;

public class MainActivity extends Activity {

    private static final String CONTENT_URI = "content://com.example.rioger.Gestor_Informacion.model.MoviesProvider/MovieEntity";

    private static final String[] PROJECTION = new String[] {
            tablaMovie.COL_NAME_ID,
            tablaMovie.COL_TITLE,
            tablaMovie.COL_OVERVIEW,
            tablaMovie.COL_RELEASE_DATE,
            tablaMovie.COL_VOTE_AVERAGE
    };

    List<Movie> movies= new ArrayList<>();
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    void showMovies(View v) {
        Uri uriContenido =  Uri.parse(CONTENT_URI);

        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(
                uriContenido,
                PROJECTION,
                null,
                null,
                null
        );

        if (cursor != null) {
            String title, releasedate, overview;
            int id;
            double voteaverage;

            while (cursor.moveToNext()) {
                id              = cursor.getInt(cursor.getColumnIndex(tablaMovie.COL_NAME_ID));
                title   = cursor.getString(cursor.getColumnIndex(tablaMovie.COL_TITLE));
                overview      = cursor.getString(cursor.getColumnIndex(tablaMovie.COL_OVERVIEW));
                releasedate         = cursor.getString(cursor.getColumnIndex(tablaMovie.COL_RELEASE_DATE));
                voteaverage          = cursor.getDouble(cursor.getColumnIndex(tablaMovie.COL_VOTE_AVERAGE));

                movie = new Movie(overview, releasedate, id, title, voteaverage);
                movies.add(movie);
            }
            cursor.close();
        }


        if(movies.size()==0){
            Toast.makeText(
                    this,
                    getString(R.string.dataNotExist),
                    Toast.LENGTH_SHORT
            ).show();
        }else if (movies.size()>0){
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            for (int i = 0; i < movies.size(); i++) {
                Log.e("Title", movies.get(i).getTitle());
                Log.e("ReleaseDate", movies.get(i).getReleaseDate());
                Log.e("Overview", movies.get(i).getOverview());

                Log.e("Id", movies.get(i).getId() + "");
                Log.e("VoteAverage", movies.get(i).getVoteAverage() + "");
                Log.e("Separator", "--------------------");
            }

            recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            Toast.makeText(
                    this,
                    getString(R.string.dataExist),
                    Toast.LENGTH_SHORT
            ).show();
        }

    }
}
