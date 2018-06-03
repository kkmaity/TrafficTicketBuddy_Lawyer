package com.trafficticketbuddy.lawyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trafficticketbuddy.lawyer.R;
import com.trafficticketbuddy.lawyer.interfaces.ItemClickListner;
import com.trafficticketbuddy.lawyer.model.cases.Response;
import com.trafficticketbuddy.lawyer.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class AcceptedCasesRecyclerAdapter extends RecyclerView.Adapter<AcceptedCasesRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private ItemClickListner _interface;
    private List<com.trafficticketbuddy.lawyer.model.fetchCase.Response> dataList=new ArrayList<>();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linOpenCase;
        ImageView ivLicense,ivBackImage,ivFontImage;
        TextView tvCaseno,tvDesc,tvStateCity,tvDate,tvTime;
        public MyViewHolder(View view) {
            super(view);
            linOpenCase = (LinearLayout)view.findViewById(R.id.linOpenCase);
            ivLicense = (ImageView)view.findViewById(R.id.ivLicense);
            ivBackImage = (ImageView)view.findViewById(R.id.ivBackImage);
            ivFontImage = (ImageView)view.findViewById(R.id.ivFontImage);
            tvCaseno = (TextView)view.findViewById(R.id.tvCaseno);
            tvDesc = (TextView)view.findViewById(R.id.tvDesc);
            tvStateCity = (TextView)view.findViewById(R.id.tvStateCity);
            tvDate = (TextView)view.findViewById(R.id.tvDate);
            tvTime = (TextView)view.findViewById(R.id.tvTime);
        }
    }


    public AcceptedCasesRecyclerAdapter(Context mContext, List<com.trafficticketbuddy.lawyer.model.fetchCase.Response> caseListData, ItemClickListner clickHandler) {
        this.mContext=mContext;
        this.dataList=caseListData;
        this._interface = clickHandler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_accepted_case, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       /* Glide.with(mContext).load(Constant.BASE_URL+dataList.get(position).getDrivingLicense())
                .thumbnail(0.5f)
                .into(holder.ivLicense);
        Glide.with(mContext).load(Constant.BASE_URL+dataList.get(position).getCaseFrontImg())
                .thumbnail(0.5f)
                .into(holder.ivFontImage);
        Glide.with(mContext).load(Constant.BASE_URL+dataList.get(position).getCaseRearImg())
                .thumbnail(0.5f)
                .into(holder.ivBackImage);
        holder.tvCaseno.setText(dataList.get(position).getCaseNumber());
        holder.tvStateCity.setText(dataList.get(position).getState()+" "+dataList.get(position).getCity());
        holder.tvDesc.setText(dataList.get(position).getCaseDetails());
        holder.linOpenCase.setTag(position);
        holder.linOpenCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object objects = v.getTag();
                _interface.onItemClick(objects,v.getId());
            }
        });*/
    }
    @Override
    public int getItemCount() {
        return /*dataList.size()*/10;
    }
}
