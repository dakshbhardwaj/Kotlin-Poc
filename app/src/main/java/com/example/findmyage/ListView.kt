package com.example.findmyage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class ListView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_view)

        val list = mutableListOf<OurData>()

        list.add(OurData(R.drawable.kohli, "Kohli", "Best Batsman"))
        list.add(OurData(R.drawable.dhawan, "Shikhar Dhawan", "Indain Opener"))
        list.add(OurData(R.drawable.bairstow, "Bairstow", "Opener Batsman"))
        list.add(OurData(R.drawable.mahi, "Mahendra Singh Dhoni", "Former Indian Caption"))
        list.add(OurData(R.drawable.dhawan, "Bales", "Bales"))
        list.add(OurData(R.drawable.kohli, "Hardik", "All Rounder"))
        list.add(OurData(R.drawable.mahi, "Indain Team", "Men in Blue"))
        list.add(OurData(R.drawable.bairstow, "Amla", "Best Batsman of SA"))
        list.add(OurData(R.drawable.kohli, "Kohli", "Best Batsman"))
        list.add(OurData(R.drawable.dhawan, "Shikhar Dhawan", "Indain Opener"))
        list.add(OurData(R.drawable.bairstow, "Bairstow", "Opener Batsman"))
        list.add(OurData(R.drawable.mahi, "Mahendra Singh Dhoni", "Former Indian Caption"))
        list.add(OurData(R.drawable.dhawan, "Bales", "Bales"))
        list.add(OurData(R.drawable.kohli, "Hardik", "All Rounder"))
        list.add(OurData(R.drawable.mahi, "Indain Team", "Men in Blue"))
        list.add(OurData(R.drawable.bairstow, "Amla", "Best Batsman of SA"))



        var customArrayAdapters = CustomArrayAdapter(this, list)

        val listView = findViewById<ListView>(R.id.our_list_view)

        listView.adapter = customArrayAdapters
    }
}