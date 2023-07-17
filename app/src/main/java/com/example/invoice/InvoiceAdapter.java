package com.example.invoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.invoice.enity.invoicemessage;

import java.util.List;

public class InvoiceAdapter extends BaseAdapter {
    private List<invoicemessage> mInvoiceList;
    private LayoutInflater mInflater;

    public InvoiceAdapter(Context context, List<invoicemessage> invoiceList) {
        mInflater = LayoutInflater.from(context);
        mInvoiceList = invoiceList;
    }

    @Override
    public int getCount() {
        return mInvoiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return mInvoiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.codeTextView = convertView.findViewById(R.id.code_text_view);
            holder.numberTextView = convertView.findViewById(R.id.number_text_view);
            holder.timeTextView = convertView.findViewById(R.id.time_text_view);
            holder.priceTextView = convertView.findViewById(R.id.price_text_view);
            holder.typeTextView = convertView.findViewById(R.id.type_text_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        invoicemessage invoice = mInvoiceList.get(position);
        holder.codeTextView.setText("发票代码："+invoice.getCode());
        holder.numberTextView.setText("发票号码："+invoice.getNumber());
        holder.timeTextView.setText("开票时间："+invoice.getTime());
        holder.priceTextView.setText("发票价格："+invoice.getPrice());
        holder.typeTextView.setText(invoice.getType());
        return convertView;
    }
    private static class ViewHolder {
        TextView codeTextView;
        TextView numberTextView;
        TextView timeTextView;
        TextView priceTextView;
        TextView typeTextView;
    }
}