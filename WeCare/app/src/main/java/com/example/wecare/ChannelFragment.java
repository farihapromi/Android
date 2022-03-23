package com.example.wecaremain;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChannelFragment extends Fragment {

    private Button relaxActivitiesButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChannelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChannelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChannelFragment newInstance(String param1, String param2) {
        ChannelFragment fragment = new ChannelFragment();
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

    private void openRelaxActivities() {
        Intent intent = new Intent(getActivity(), antiburnout.class);
        startActivity(intent);
    }

    ArrayList<Pair<String, Pair<Integer, Integer>>> channels;
    RecyclerView rvStories;
    ChennlAdapter adapter ;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvStories = view.findViewById(R.id.rvPosts);
        channels = new ArrayList<>();

        channels.add(new Pair<>("Psychiatrist", new Pair<>(R.drawable.psyc, R.color.mind)));
        channels.add(new Pair<>("Therapist", new Pair<>(R.drawable.ther, R.color.light_blue)));
        channels.add(new Pair<>("Surgeon", new Pair<>(R.drawable.surge, R.color.yellow)));
        channels.add(new Pair<>("Nursing", new Pair<>(R.drawable.psyc, R.color.purple)));
        channels.add(new Pair<>("Psysician", new Pair<>(R.drawable.phys, R.color.mind)));
        channels.add(new Pair<>("EMT/Paramedic", new Pair<>(R.drawable.para, R.color.light_blue)));
        adapter = new ChennlAdapter(getContext(), channels);
        rvStories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvStories.setAdapter(adapter);
        relaxActivitiesButton = (Button) view.findViewById(R.id.relaxActivitiesButton);
        relaxActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRelaxActivities();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_channel, container, false);
    }
}