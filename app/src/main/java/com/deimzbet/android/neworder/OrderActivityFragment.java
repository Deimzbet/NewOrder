package com.deimzbet.android.neworder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

public class OrderActivityFragment extends Fragment {

    private static final String ARG_ID = "id";

    private Order mOrder;

    public static OrderActivityFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ID, id);
        OrderActivityFragment fragment = new OrderActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            UUID id = (UUID) args.getSerializable(ARG_ID);
            mOrder = OrderLab.get(getActivity()).getOrder(id);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        EditText titleField = view.findViewById(R.id.titleEditText);
        EditText typeField = view.findViewById(R.id.typeEditText);
        TextView dateLabel = view.findViewById(R.id.dateTextView);
        Button dateButton = view.findViewById(R.id.dateButton);
        CheckBox finishedCheckBox = view.findViewById(R.id.finishedCheckBox);

        titleField.setText(mOrder.getTitle());
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOrder.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        typeField.setText(mOrder.getType());
        typeField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOrder.setType(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String dateFormat = DateFormat.format("dd.MMMM.yyyy", mOrder.getDate()).toString();
        dateLabel.setText(dateFormat);
        dateButton.setEnabled(false);

        finishedCheckBox.setChecked(mOrder.isFinished());
        finishedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOrder.setFinished(isChecked);
            }
        });


        return view;
    }

}
