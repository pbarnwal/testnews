package com.cgn.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cgn.android.R;
import com.cgn.android.models.NavItem;

import java.util.ArrayList;

/**
 * Created by anil on 30/4/15.
 */
public class MenuRvAdapter extends RecyclerView.Adapter<MenuRvAdapter.MenuViewHolder> {
    private ArrayList<NavItem> mNavItemList;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private int mCurrentScreenId;

    private int mUnreadMessageCount;

    public MenuRvAdapter(ArrayList<NavItem> mNavItemList, AdapterView.OnItemClickListener onItemClickListener) {
        this.mNavItemList = mNavItemList;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_drawer, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, final int position) {
        holder.dLayout.setBackgroundResource(mNavItemList.get(position).getmItemBg());
        holder.menuImg.setImageResource(mNavItemList.get(position).getmResId());
        holder.menuItem.setText(mNavItemList.get(position).getmName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(null, v, position, v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNavItemList.size();
    }

    public void setCurrentItem(int screenId) {
        mCurrentScreenId = screenId;
        notifyDataSetChanged();
    }

    public void setUnreadMessageCount(int unreadMessageCount) {
        mUnreadMessageCount = unreadMessageCount;
        notifyDataSetChanged();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout dLayout;
        private ImageView menuImg;
        private TextView menuItem;

        public MenuViewHolder(View itemView) {
            super(itemView);
            dLayout = (LinearLayout) itemView.findViewById(R.id.drawer_layout);
            menuImg = (ImageView) itemView.findViewById(R.id.MENU_img);
            menuItem = (TextView) itemView.findViewById(R.id.MENU_item_name);
        }
    }
}
