package com.ultimatix.cashrich.ui;


import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ultimatix.cashrich.MainViewModel;
import com.ultimatix.cashrich.R;
import com.ultimatix.cashrich.customview.PieChart;
import com.ultimatix.cashrich.data.entity.Article;
import com.ultimatix.cashrich.datamodels.sip.SIPData;
import com.ultimatix.cashrich.ui.adapters.ItemAdater;
import com.ultimatix.cashrich.viewmodels.SIPViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicSIPFragment extends Fragment implements ItemAdater.ItemAdaterListner {


    private SIPViewModel sipViewModel;

    private FragmentInteractionListner mListener;
    private ItemAdater itemApdater;
    private Observer<List<SIPData>> sipDataObserver;
    private MainViewModel mainViewModel;

    @BindView(R.id.recyclerview)
    RecyclerView itemsRV;

    @BindView(R.id.equity)
    TextView equity;

    @BindView(R.id.detail)
    TextView detail;

    @BindView(R.id._equity)
    TextView _equity;

    @BindView(R.id.main_layout)
    ScrollView mainLayout;

    @BindView(R.id.graph)
    RelativeLayout graph;

    @BindView(R.id.inner_circle)
    LinearLayout innerCircle;

    private ValueAnimator colorAnimation;


    public DynamicSIPFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListner) {
            mListener = (FragmentInteractionListner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sipViewModel = ViewModelProviders.of(this).get(SIPViewModel.class);
        sipDataObserver = new Observer<List<SIPData>>() {
            @Override
            public void onChanged(List<SIPData> sipData) {
                if(!sipData.isEmpty()){
                    itemApdater.onDataChange((ArrayList) sipData);
                    bindData(sipData.get(0));
                }
            }
        };
        itemApdater = new ItemAdater(getContext(), this, ItemAdater.SIP);


    }

    private void bindData(SIPData sipData) {

        if(Integer.valueOf(sipData.getEquity())>=50){
            setBackground(mainLayout, R.color.blue);
        }
        else{
            setBackground(mainLayout, R.color.yellow);
        }

        equity.setText(sipData.getEquity() + "%");
        _equity.setText(String.valueOf(100-Integer.valueOf(sipData.getEquity())) + "%");
        detail.setText(sipData.getPoint());

        int[] data={Integer.valueOf(sipData.getEquity()), 100-Integer.valueOf(sipData.getEquity())};
        int[] color={Color.RED,Color.BLUE};

        View view = new PieChart(getContext(),2,data,color, innerCircle.getWidth(), innerCircle.getHeight());
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        graph.addView(view, layoutParams);
    }

    private void setBackground(View view, int to) {

        int colorFrom = Color.TRANSPARENT;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable){
            colorFrom = ((ColorDrawable) background).getColor();
        }
        int colorTo = getResources().getColor(to);
        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });

        colorAnimation.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dynamic_si, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        itemsRV.setAdapter(itemApdater);
        itemsRV.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sipViewModel.getSipLiveData().observe(this, sipDataObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sipViewModel.getSipLiveData().removeObserver(sipDataObserver);
    }

    @Override
    public void onArticleSelected(Article article) {

    }

    @Override
    public void onDaeSelected(SIPData sipData) {
        bindData(sipData);
    }
}

