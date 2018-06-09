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
import com.trafficticketbuddy.lawyer.model.allbid.Response;
import com.trafficticketbuddy.lawyer.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class AcceptedCasesRecyclerAdapter extends RecyclerView.Adapter<AcceptedCasesRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private ItemClickListner _interface;
    private List<Response> dataList=new ArrayList<>();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linOpenCase;
        ImageView ivProfileImage;
        TextView tvCaseno,tvStateCity,tvDate,tvTime,tvBidCount,tvName;
        public MyViewHolder(View view) {
            super(view);
            linOpenCase = (LinearLayout)view.findViewById(R.id.linOpenCase);
            ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
            tvCaseno = (TextView)view.findViewById(R.id.tvCaseno);
            tvStateCity = (TextView)view.findViewById(R.id.tvStateCity);
            tvDate = (TextView)view.findViewById(R.id.tvDate);
            tvTime = (TextView)view.findViewById(R.id.tvTime);
            tvBidCount = (TextView)view.findViewById(R.id.tvBidCount);
            tvName = (TextView)view.findViewById(R.id.tvName);
        }
    }


    public AcceptedCasesRecyclerAdapter(Context mContext, List<Response> caseListData, ItemClickListner clickHandler) {
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
        Glide.with(mContext).load(Constant.BASE_URL+dataList.get(position).getClientProfileImage())
                .thumbnail(0.5f)
                .into(holder.ivProfileImage);
        holder.tvCaseno.setText(dataList.get(position).getCaseNumber());
        holder.tvStateCity.setText(dataList.get(position).getState()+" "+dataList.get(position).getCity());
        String[] splited = dataList.get(position).getCreatedAt().split("\\s+");
        holder.tvDate.setText(""+splited[0]);
        holder.tvTime.setText(""+splited[1]);
        holder.tvBidCount.setText(""+dataList.get(position).getBidAmount());
        holder.tvName.setText(""+dataList.get(position).getClientFirstName()+" "+dataList.get(position).getClientLastName());
        holder.linOpenCase.setTag(position);
        holder.linOpenCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object objects = v.getTag();
                _interface.onItemClick(objects,v.getId());
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
