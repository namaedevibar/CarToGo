package com.activity.devibar.cartogo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activity.devibar.cartogo.R;
import com.activity.devibar.cartogo.ShoppingCart.ShoppingCart;

import java.util.List;

/**
 * Created by namai on 9/6/2016.
 */
public class ShoppingAdapter extends ArrayAdapter<ShoppingCart> {
    private Context mContext;
    private int mResLayoutId;
    private List<ShoppingCart> mList;


    public ShoppingAdapter(Context context, int resLayoutId, List<ShoppingCart> list) {
        super(context, resLayoutId, list);

        mContext = context;
        mResLayoutId = resLayoutId;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(mResLayoutId, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder)convertView.getTag();
        }


        ShoppingCart item = mList.get(position);
        if (item!=null){
            if (holder.txtItem!=null){
                holder.txtItem.setText(item.getItem());
            }
            if (holder.txtQuantity!=null){
                holder.txtQuantity.setText(item.getQuantity()+" item(s)");
            }
            if (holder.txtPrice!=null){
                holder.txtPrice.setText(item.getPrice()+"");
            }
            if (holder.txtTotal!=null){
                holder.txtTotal.setText(item.getPrice()*item.getQuantity()+"");
            }

        }

        return convertView;
    }

    private class ViewHolder{
        TextView txtPrice;
        TextView txtItem;
        TextView txtQuantity;
        TextView txtTotal;


        public ViewHolder(View view){
            txtItem = (TextView) view.findViewById(R.id.txtItem);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            txtQuantity = (TextView) view.findViewById(R.id.txtQuantity);
            txtTotal = (TextView) view.findViewById(R.id.txtTotal);


        }

    }
}

