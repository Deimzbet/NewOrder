package com.deimzbet.android.neworder;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderActivityFragment extends Fragment {

    private static final String ARG_ID = "id";
    private static final String DATE_DIALOG = "date_dialog";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;

    private TextView dateLabel;
    private ImageView mPhotoImageView;
    private ImageButton mCameraImageButton;

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

        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        OrderLab.get(getActivity()).updateOrder(mOrder);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);


        EditText titleField = view.findViewById(R.id.titleEditText);
        EditText typeField = view.findViewById(R.id.typeEditText);
        dateLabel = view.findViewById(R.id.dateTextView);
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
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    OrderDateDialog dialog = OrderDateDialog.newInstance(mOrder.getDate());
                    dialog.setTargetFragment(OrderActivityFragment.this, REQUEST_DATE);
                    dialog.show(fm, DATE_DIALOG);
                }
            }
        });

        finishedCheckBox.setChecked(mOrder.isFinished());
        finishedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOrder.setFinished(isChecked);
            }
        });

        mPhotoImageView = view.findViewById(R.id.photoImageView);
        mCameraImageButton = view.findViewById(R.id.cameraImageButton);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_order, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            OrderLab.get(getActivity()).deleteOrder(mOrder.getId());
            if (getActivity() != null) {
                getActivity().finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(OrderDateDialog.EXTRA_DATE);
            mOrder.setDate(date);
            String dateFormat = DateFormat.format("dd.MMMM.yyyy", date).toString();
            dateLabel.setText(dateFormat);
        }
    }
}
