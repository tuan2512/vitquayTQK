package com.isd.vitquaytqk.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isd.vitquaytqk.Interface.CheckProduct;
import com.isd.vitquaytqk.R;
import com.isd.vitquaytqk.entity.ProductOrder;
import com.isd.vitquaytqk.untils.Untils;

import java.util.List;

/**
 * Created by Admin on 11/28/2017.
 */

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.Holder> {
    Context context;
    List<ProductOrder> data;
    CheckProduct checkProduct;

    public ProductOrderAdapter(Context context, List<ProductOrder> data) {
        this.context = context;
        this.data = data;
        this.checkProduct = (CheckProduct) context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_product_recyclerview, parent, false);
        return (new Holder(view));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.txtName.setText(data.get(position).getProductName());
        holder.txtType.setText(data.get(position).getType());
        holder.txtNum.setText((data.get(position).getProductNum() + "").replace(".0", ""));
        holder.txtPrice.setText(Untils.convertToVnd(data.get(position).getProductPrice()) + "");
        holder.txtTotalMoney.setText(Untils.convertToVnd(data.get(position).getProductTotalMoney()) + "");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtNum;
        TextView txtType;
        TextView txtPrice;
        TextView txtTotalMoney;

        public Holder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txt_Name);
            txtNum = (TextView) itemView.findViewById(R.id.txt_Num);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_Price);
            txtTotalMoney = (TextView) itemView.findViewById(R.id.txt_TotalMoney);
            txtType = (TextView) itemView.findViewById(R.id.txt_TypeId);
        }
    }
}
