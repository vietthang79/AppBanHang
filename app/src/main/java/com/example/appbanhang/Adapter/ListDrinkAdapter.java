package com.example.appbanhang.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.Activity.DienThoai;
import com.example.appbanhang.Activity.Drink_Beer;
import com.example.appbanhang.Activity.Food;
import com.example.appbanhang.Activity.Ice_Cream;
import com.example.appbanhang.Activity.Juice;
import com.example.appbanhang.Activity.LapTop;
import com.example.appbanhang.Activity.MainActivity;
import com.example.appbanhang.Activity.ThuocLa;
import com.example.appbanhang.R;
import com.example.appbanhang.model.ListDrink_Horizol;

import java.util.ArrayList;
import java.util.List;

public class ListDrinkAdapter extends RecyclerView.Adapter<ListDrinkAdapter.ViewHolder> {
    UpdateVerticalRecy updateVerticalRecy;
    Context context;
    List<ListDrink_Horizol> list;

    boolean check = true;
    boolean select = true;
    int row_index = -1;


    public ListDrinkAdapter(Context context, List<ListDrink_Horizol> list) {
        this.updateVerticalRecy = updateVerticalRecy;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horozontal_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img_view.setImageResource(list.get(position).getImg());
        holder.txt_view.setText(list.get(position).getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
//                notifyDataSetChanged();
                if (position == 0){
                    Intent trangchu = new Intent(context, MainActivity.class);
                    trangchu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(trangchu);
                }
                else if (position == 1 ){
                    Intent coffee = new Intent(context, DienThoai.class);
                    coffee.putExtra("type", 3);
                    coffee.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(coffee);
                }
                else if (position == 2 ){
                    Intent milktea = new Intent(context, LapTop.class);
                    milktea.putExtra("type", 4);
                    milktea.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(milktea);
                }
                else if (position == 3 ){
                    Intent Beverage = new Intent(context, Drink_Beer.class);
                    Beverage.putExtra("type", 2);
                    Beverage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(Beverage);
                }
                else if (position == 4 ){
                    Intent Cigarette = new Intent(context, ThuocLa.class);
                    Cigarette.putExtra("type", 5);
                    Cigarette.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(Cigarette);
                }
                else if (position == 5 ){
                    Intent juicedrink = new Intent(context, Juice.class);
                    juicedrink.putExtra("type", 6);
                    juicedrink.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(juicedrink);
                }
                else if (position == 6 ){
                    Intent cream = new Intent(context, Ice_Cream.class);
                    cream.putExtra("type", 7);
                    cream.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(cream);
                }
                else{
                    Intent food = new Intent(context, Food.class);
                    food.putExtra("type", 1);
                    food.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(food);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_view;
        TextView txt_view;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_view = itemView.findViewById(R.id.hor_img);
            txt_view = itemView.findViewById(R.id.hor_text);
            cardView = itemView.findViewById(R.id.cardview_hor);
        }
    }
}
