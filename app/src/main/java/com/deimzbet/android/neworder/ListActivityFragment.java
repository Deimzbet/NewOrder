package com.deimzbet.android.neworder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListActivityFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        Log.d("TAGG", "onCreateView");
        mRecyclerView = view.findViewById(R.id.listRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        Log.d("TAGG", "onResume");
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        List<Order> orders = OrderLab.get(getActivity()).getOrders();
        if (mAdapter == null) {
            mAdapter = new OrderAdapter(orders);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(orders);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Order order = new Order();
        OrderLab.get(getActivity()).addOrder(order);
        Intent intent = OrderActivity.createIntent(getActivity(), order.getId());
        startActivity(intent);
        return true;
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

        private List<Order> mOrders;

        public OrderAdapter(List<Order> orders) {
            mOrders = orders;
        }

        @NonNull
        @Override
        public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("TAGG", "onCreateViewHolder");
            View view = getLayoutInflater().inflate(R.layout.item_list, parent, false);
            return new OrderHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
            Log.d("TAGG", "onBindViewHolder");
            Order order = mOrders.get(position);
            holder.bind(order);
        }

        @Override
        public int getItemCount() {
            Log.d("TAGG", "getItemCount");
            return mOrders.size();
        }

        public void setCrimes(List<Order> orders) {
            mOrders = orders;
        }
    }

    private class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mFinishedImageView;

        private Order mOrder;

        public OrderHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.itemTitleTextView);
            mDateTextView = itemView.findViewById(R.id.itemDateTextView);
            mFinishedImageView = itemView.findViewById(R.id.finishedImageView);
        }

        public void bind(Order order) {
            mOrder = order;

            mTitleTextView.setText(mOrder.getTitle());
            String dateFormat = DateFormat.format("dd.MMMM.yyyy", mOrder.getDate()).toString();
            mDateTextView.setText(dateFormat);
            mFinishedImageView.setVisibility(mOrder.isFinished() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            Intent intent = OrderActivity.createIntent(getActivity(), mOrder.getId());
            startActivity(intent);
        }
    }
}
