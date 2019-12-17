package com.mag.digikala.Model.Adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.digikala.Model.Product;
import com.mag.digikala.R;
import com.mag.digikala.Var.Constants;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.List;

public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.FilterListViewHolder> {


    private Activity activity;
    private List<Product> products;

    public FilterListAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public FilterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_filter_list_item, parent, false);
        return new FilterListAdapter.FilterListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterListViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setData(List<Product> products) {
        this.products = products;
    }

    public class FilterListViewHolder extends RecyclerView.ViewHolder {

        private TextView productTitle, productDescription, regularPrice, salesPrice;
        private ImageView productImage;

        public FilterListViewHolder(@NonNull View itemView) {
            super(itemView);
            regularPrice = itemView.findViewById(R.id.filter_list_item_layout__product_regular_price);
            salesPrice = itemView.findViewById(R.id.filter_list_item_layout__product_sales_price);
            productTitle = itemView.findViewById(R.id.filter_list_item_layout__product_title);
            productDescription = itemView.findViewById(R.id.filter_list_item_layout__product_description);
            productImage = itemView.findViewById(R.id.filter_list_item_layout__product_image);
        }

        public void bind(Product product) {

            String MONEY_STRING = Constants.SPACE_CHAR + activity.getResources().getString(R.string.tomans);
            String priceString;
            String priceInvalidString = "";

            if (product.getSalePrice().equals(Constants.EMPTY_CHAR))
                priceString = product.getPrice() + MONEY_STRING;
            else {
                priceString = product.getSalePrice() + MONEY_STRING;
                priceInvalidString = product.getPrice() + MONEY_STRING;
            }

            regularPrice.setText(priceInvalidString);
            regularPrice.setPaintFlags(salesPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            salesPrice.setText(priceString);

            productTitle.setText(product.getName() + " " + product.getId());
            productDescription.setText(Jsoup.parse(product.getShortDescription()).text());

            Picasso.get().load(product.getImages()[0].getSrc()).placeholder(activity.getResources().getDrawable(R.drawable.place_holder)).into(productImage);

        }

    }

}
