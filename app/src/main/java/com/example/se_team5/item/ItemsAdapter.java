package com.example.se_team5.item;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.se_team5.R;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    private ArrayList<Item> myDataList;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);


    public interface OnListItemLongSelectedInterface {
        void onItemLongSelected(View v, int position);
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;
    private OnListItemLongSelectedInterface mLongListener;


    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView imageView;
        TextView name;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    toggleItemSelected(position);

                    Log.d("test", "position = " + position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongListener.onItemLongSelected(v, getAdapterPosition());
                    return false;
                }
            });

        }

        private void toggleItemSelected(int position) {

            if (mSelectedItems.get(position, false) == true) {
                mSelectedItems.delete(position);
                notifyItemChanged(position);
            } else {
                mSelectedItems.put(position, true);
                notifyItemChanged(position);
            }
        }

        private boolean isItemSelected(int position) {
            return mSelectedItems.get(position, false);
        }

        public void clearSelectedItem() {
            int position;

            for (int i = 0; i < mSelectedItems.size(); i++) {
                position = mSelectedItems.keyAt(i);
                mSelectedItems.put(position, false);
                notifyItemChanged(position);
            }

            mSelectedItems.clear();
        }
    }

    public ItemsAdapter(ArrayList<Item> dataList)
    {
        myDataList = dataList;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewer_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        viewHolder.img.setImageResource(myDataList.get(position).getImageResourceID());
        viewHolder.name.setText(myDataList.get(position).getName1() + " " + myDataList.get(position).getName2());
    }

    @Override
    public int getItemCount()
    {
        return (null != myDataList ? myDataList.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
