package com.example.wecaremain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_LOAD_IMAGE = 78;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfile newInstance(String param1, String param2) {
        UserProfile fragment = new UserProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView tvName;
    EditText tvPosition;
    TextView tvCare;
    TextView tvChangePosition;
    ImageView ivProfilePicture;
    EditText edNumCareToday;
    Button btnQuote;
    TextView tvLogout;
    Button btnSubmitCare;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ParseUser user = ParseUser.getCurrentUser();
        tvName = view.findViewById(R.id.tvName);
        tvPosition = view.findViewById(R.id.edPostition);
        tvCare = view.findViewById(R.id.tvnumSelfCare);
        tvChangePosition = view.findViewById(R.id.tvChangePostition);
        tvName.setText(user.getUsername());

        ivProfilePicture = view.findViewById(R.id.ivPicture);
        edNumCareToday = view.findViewById(R.id.edNumCareToday);
        btnSubmitCare = view.findViewById(R.id.btnSubmitCare);
        tvLogout = view.findViewById(R.id.tvLogOut);
        BarChart chart = (BarChart) view.findViewById(R.id.chart);

        ArrayList NoOfEmp = new ArrayList();

        JSONArray c = user.getJSONArray("care");
        for(int i = 0; i < 7; i++)
        {

            try {
                if(c.get(i) == null)
                    NoOfEmp.add(new BarEntry(i, 0));
                else
                    NoOfEmp.add(new BarEntry(i, c.getInt(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        BarDataSet bardataset = new BarDataSet(NoOfEmp, "Times of self care");
        chart.animateY(5000);
        BarData data = new BarData(bardataset);
        chart.setData(data);
        Description d = new Description();
        d.setText("Times of self care");
        chart.setDescription(d);
        chart.invalidate();


        tvPosition.setText(user.getString("position"));

        tvChangePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.put("position", tvPosition.getText().toString());
                user.saveInBackground();
            }
        });
        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        try {
            ParseFile p = (user.getParseFile("picture"));
            if (p != null) {
                Glide.with(this).load(p.getFile()).transform(new CircleCrop()).into(ivProfilePicture);
            } else {
                Glide.with(this).load(R.drawable.ic_launcher_background).transform(new CircleCrop()).into(ivProfilePicture);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StringBuilder sb = null;
        try {
            sb = setCare(user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvCare.setText(sb);
        btnSubmitCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray c = user.getJSONArray("care");
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                if(c == null) {
                    c = new JSONArray();
                    for(int i = 0; i < 7; i++)
                    {
                        try {
                            c.put(i, 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    c.put((day - 1) % 7, Integer.parseInt(edNumCareToday.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                user.put("care",c);
                try {
                    tvCare.setText(setCare(user));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                user.saveInBackground();

            }
        });
        btnQuote = view.findViewById(R.id.btnGetQuote);
        btnQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MotivationActivity.class);
                startActivity(i);
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });


    }


    @NotNull
    private StringBuilder setCare(ParseUser user) throws JSONException {
        StringBuilder sb = new StringBuilder();
        JSONArray care = user.getJSONArray("care");
        if(care == null)
        {
            care = new JSONArray();
            for (int i = 0; i < 7; i++)
            {

                care.put(0, i);
            }
            user.saveInBackground();
        }
        for (int i = 0; i < care.length(); i++)
        {
            try {
                String x = care.getString(i);
                if(x.equals("null"))
                    x = "0";
                sb.append(x);
                sb.append("     " );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sb;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null !=data){
            Uri selectedImageUri = data.getData();

            File f=new File(getContext().getCacheDir(),"file name");
            ivProfilePicture.setImageURI(selectedImageUri);
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Convert bitmap to byte array
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            ParseUser user = ParseUser.getCurrentUser();
            user.put("picture", new ParseFile(f));


            user.saveInBackground();


        } else {
            Toast.makeText(getContext(), "You have not selected and image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }
}