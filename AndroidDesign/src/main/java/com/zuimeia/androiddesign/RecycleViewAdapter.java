package com.zuimeia.androiddesign;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyong on 15/11/18.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.Holder> {

    private List<String> list = new ArrayList<>();
    private LayoutInflater inflater;

    public RecycleViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        list.add("A-Bomb (HAS)");
        list.add("A.I.M.");
        list.add("Abe");
        list.add("Abin");
        list.add("Abomination");
        list.add("Abraxas");
        list.add("Absorbing");
        list.add("Adam");
        list.add("Agent Bob");
        list.add("Agent Zero");
        list.add("Air Walker");
        list.add("Ajax");
        list.add("Alan Scott");
        list.add("Alex Mercer");
        list.add("Alex Woolsly");
        list.add("Alfred Pennyworth");
        list.add("Allan Quartermain");
        list.add("Amazo");
        list.add("Ammo Ando");
        list.add("Masahashi Angel");
        list.add("Angel Dust");
        list.add("Angel Salvadore");
        list.add("A-Bomb");
        list.add("Abe");
        list.add("Abin");
        list.add("Abomination");
        list.add("Abraxas");
        list.add("Absorbing");
        list.add("Adam");
        list.add("Agent Bob");
        list.add("Agent Zero");
        list.add("Air Walker");
        list.add("Ajax");
        list.add("Alan Scott");
        list.add("Alex Mercer");
        list.add("Alex Woolsly");
        list.add("Alfred Pennyworth");
        list.add("Allan Quartermain");
        list.add("Amazo");
        list.add("Ammo Ando");
        list.add("Masahashi Angel");
        list.add("Angel Dust");
        list.add("Angel Salvadore");
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = inflater.inflate(R.layout.recycle_view_item, parent, false);
        Holder holder = new Holder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt);
        }
    }
}
