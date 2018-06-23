package com.trafficticketbuddy.lawyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.trafficticketbuddy.lawyer.BaseActivity;
import com.trafficticketbuddy.lawyer.R;
import com.trafficticketbuddy.lawyer.interfaces.ItemClickListner;
import com.trafficticketbuddy.lawyer.model.fetchCase.Response;
import com.trafficticketbuddy.lawyer.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class MadeBidRecyclerAdapter extends RecyclerView.Adapter<MadeBidRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private ItemClickListner _interface;
    private List<Response<R>> dataList=new ArrayList<>();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linAllCase;
        ImageView ivLicense,ivBackImage,ivFontImage;
        TextView tvCaseno,tvDesc,tvStateCity,tvDate,tvTime,tvBidCount;
        public MyViewHolder(View view) {
            super(view);
            linAllCase = (LinearLayout)view.findViewById(R.id.linAllCase);
            ivLicense = (ImageView)view.findViewById(R.id.ivLicense);
            ivBackImage = (ImageView)view.findViewById(R.id.ivBackImage);
            ivFontImage = (ImageView)view.findViewById(R.id.ivFontImage);
            tvCaseno = (TextView)view.findViewById(R.id.tvCaseno);
            tvDesc = (TextView)view.findViewById(R.id.tvDesc);
            tvStateCity = (TextView)view.findViewById(R.id.tvStateCity);
            tvDate = (TextView)view.findViewById(R.id.tvDate);
            tvTime = (TextView)view.findViewById(R.id.tvTime);
            tvBidCount = (TextView)view.findViewById(R.id.tvBidCount);

        }
    }


    public MadeBidRecyclerAdapter(Context mContext, List<Response<R>> caseListData, ItemClickListner clickHandler) {
        this.mContext=mContext;
        this.dataList=caseListData;
        this._interface = clickHandler;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_allcases, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(Constant.BASE_URL+dataList.get(position).getDrivingLicense(), holder.ivLicense, BaseActivity.cacheOptions);
        ImageLoader.getInstance().displayImage(Constant.BASE_URL+dataList.get(position).getCaseFrontImg(), holder.ivFontImage, BaseActivity.cacheOptions);
        ImageLoader.getInstance().displayImage(Constant.BASE_URL+dataList.get(position).getCaseRearImg(), holder.ivBackImage, BaseActivity.cacheOptions);
        String[] splited = dataList.get(position).getCreatedAt().split("\\s+");
        holder.tvDate.setText(""+splited[0]);
        holder.tvTime.setText(""+splited[1]);
        holder.tvBidCount.setText(""+dataList.get(position).getBidCount());
        holder.tvCaseno.setText(dataList.get(position).getCaseNumber());
        holder.tvStateCity.setText(dataList.get(position).getCity()+" "+dataList.get(position).getState());
        holder.tvDesc.setText(dataList.get(position).getCaseDetails());
        holder.linAllCase.setTag(position);
        holder.linAllCase.setOnClickListener(new View.OnClickListener() {
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
