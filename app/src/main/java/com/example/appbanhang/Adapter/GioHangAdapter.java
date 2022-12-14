package com.example.appbanhang.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.IImageClickListenner;
import com.example.appbanhang.R;
import com.example.appbanhang.model.EventBus.TinhTongEvent;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
        Context context;
        List<GioHang> giohangList;

    public GioHangAdapter(Context context, List<GioHang> giohangList) {
        this.context = context;
        this.giohangList = giohangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = giohangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSpluong()+"");

        if (gioHang.getHinhsp().contains("http")) {
            Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        }else{
            String hinh = Utils.BASE_URL+"images/"+gioHang.getHinhsp();
            Glide.with(context).load(hinh).into(holder.item_giohang_image);
        }

//        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.item_giohang_gia.setText("Gia :" +decimalFormat.format(gioHang.getGiasp()));
        long gia = gioHang.getSpluong() *gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gioHang.getGiasp()));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        Utils.mangmuahang.add(gioHang);
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else{
                        for (int i = 0; i<Utils.mangmuahang.size(); i++){
                            if (Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()){
                                Utils.mangmuahang.remove(i);
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        }
                    }
            }
        });

        holder.setListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG", "onImageClick: "+pos+"..."   +giatri);
                if (giatri == 1){
                    if (giohangList.get(pos). getSpluong() > 1){
                        int soluongmoi = giohangList.get(pos).getSpluong()-1;
                        giohangList.get(pos).setSpluong(soluongmoi);

                        holder.item_giohang_soluong.setText(giohangList.get(pos).getSpluong()+"");
                        long gia = giohangList.get(pos).getSpluong() * giohangList.get(pos).getGiasp();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else if (giohangList.get(pos). getSpluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Th??ng b??o");
                        builder.setMessage("B???n c?? mu???n x??a s???n ph???m n??y kh???i gi??? h??ng");
                        builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                            }
                        });
                        builder.show();


                    }
                }else if (giatri == 2){
                    if (giohangList.get(pos). getSpluong() < 100){
                        int soluongmoi = giohangList.get(pos).getSpluong()+1;
                        giohangList.get(pos).setSpluong(soluongmoi);
                    }
                    holder.item_giohang_soluong.setText(giohangList.get(pos).getSpluong()+"");
                    long gia = giohangList.get(pos).getSpluong() * giohangList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhTongEvent()  );
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return giohangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, imgtru, imgcong;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong, item_giohang_giasp2;
        IImageClickListenner listenner;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
//            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);
            checkBox = itemView.findViewById(R.id.item_giohang_check);

            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }


        public void setListenner(IImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru){
                    listenner.onImageClick(view, getAdapterPosition(), 1);
            }else if (view == imgcong){
                listenner.onImageClick(view, getAdapterPosition(), 2);

            }
        }
    }
}
