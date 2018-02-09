package com.example.nhut.mytestapp123456

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Nhut on 11/21/2017.
 */
class  ForecastListAdapter(val  items:  List<String>) :
         RecyclerView.Adapter<ForecastListAdapter.ViewHolder>()  {
     override  fun  onCreateViewHolder(parent: ViewGroup, viewType:  Int): ViewHolder  {
         return  ViewHolder(TextView(parent.context))
     }

     override  fun  onBindViewHolder(holder:  ViewHolder,  position:  Int)  {
//         holder.textView.text = items[position]
         with(items[position]) {
            holder.textView.text = "$position ." + items[position]
         }
     }

     override  fun  getItemCount():  Int = items.size

     class  ViewHolder(val  textView:  TextView)  :  RecyclerView.ViewHolder(textView)
}