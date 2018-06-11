package com.railway_services.indian.railway;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context mCtx;
    public static OnCategoryItemClick categoryItemClick;

    int icon[] = {R.drawable.train_44, R.drawable.station, R.drawable.livetrain,
            R.drawable.waiting_room,R.drawable.tickets,R.drawable.asking,
            R.drawable.route,R.drawable.trainstation,R.drawable.schedule,
            R.drawable.creditcard,R.drawable.speed,R.drawable.question};
    String[] category = {"Find my Train", "Train Between Station", "Live Train","Seat Availability","Fare Enquiry","PNR Enquiry","Train Route","Train Arrivals","Rescheduled Train","Cancelled Train","Train Speed","FAQ"};

    public CategoryAdapter(Context mCtx, OnCategoryItemClick categoryItemClick) {
        this.categoryItemClick = categoryItemClick;
        this.mCtx = mCtx;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.category_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.title.setText(category[position]);
        holder.icon.setImageResource(icon[position]);
    }

    @Override
    public int getItemCount() {
        return category.length;
    }

    interface OnCategoryItemClick {
        void onClick(int position);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView title;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.categoryIcon);
            title = itemView.findViewById(R.id.categoryTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            categoryItemClick.onClick(getAdapterPosition());
        }
    }

}
