package com.example.Model;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ModelName  {
    String name;

    public ModelName( ) {
    }

    public ModelName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
