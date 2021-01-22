package com.example.egran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.SearchView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    String[] htmlPages;
    ArrayList<urlForPages> arraylist = new ArrayList<urlForPages>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String path = Environment.getExternalStorageDirectory().toString() + "/android_assets/";
//        Log.d("Files", "Path: " + path);
//        File directory = new File(path);
//        File[] files = directory.listFiles();

//        String path = Environment.getExternalStorageDirectory().toString() + "/android_assets/";
//        File directory = new File(path);
//        htmlPages = new String[]{
//           "/a" + directory.listFiles()
//        };

//        File f = new File(path);
//        File file[] = f.listFiles();
//        htmlPages = new String[]{
//           "/a" + file[1]
//        };


        htmlPages = new String[]{"Book_scanning.html", "CKEditor.html",
        "Comparison_of_graphics_file_formats.html", "Content_management_system.html",
        "Content_Management_System-2.html", "File_100x100_black_and_white_pixels.html",
        "File_1951_USAF_Resolution_Test_Target.html", "File_Andi_Gutmans_1.html",
        "File_CKEditor.html", "File_Crystal_Clear_device_cdrom_unmount.html",
        "File_Distinguishable_squares.html"};

        list = (ListView) findViewById(R.id.listview);

        for(int i = 0; i < htmlPages.length; i++){
            urlForPages urls = new urlForPages(htmlPages[i]);
            arraylist.add(urls);
        }

        adapter = new ListViewAdapter(this, arraylist);

        list.setAdapter(adapter);

        editsearch = (SearchView) findViewById(R.id.search);
        //editsearch.setOnQueryTextListener(this);

        SearchView simpleSearchView = (SearchView) findViewById(R.id.simpleSearchView); // inititate a search view
        CharSequence query = simpleSearchView.getQuery();
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do something on text submit
                openNewActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                adapter.filter(text);
                return false;
            }
        });
    }

//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // do something on text submit
//                openNewActivity(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                String text = newText;
//                adapter.filter(text);
//                return false;
//            }

    public void openNewActivity(String query){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }

}