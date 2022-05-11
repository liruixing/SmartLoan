package com.lrx.module_ui.View.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lrx.module_ui.R;

/**
 * create by rgh
 * on 2020-05-07
 * description：
 **/
public class DialogCentreAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mData;

    public DialogCentreAdapter(Context context, String[] data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData == null?null:mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DialogViewHolder viewHolder ;
        if(convertView == null){
            viewHolder = new DialogViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_list_centre,null);
            viewHolder.mTVContent = convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (DialogViewHolder) convertView.getTag();
        }
        String content = mData[position];
        viewHolder.mTVContent.setText(content);
        return convertView;
    }

    static class  DialogViewHolder{
        TextView mTVContent;

    }

}
