package com.qll.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qll.demo.R;
import com.qll.demo.ui.fragment.HomeFragment;
import com.qll.demo.ui.fragment.MoreFragment;
import com.qll.demo.ui.fragment.OrderFragment;
import com.qll.demo.ui.fragment.PersonFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.fl_main_fragments)
    FrameLayout flMainFragments;
    @InjectView(R.id.ll_tab)
    LinearLayout llTab;
    private int childCount;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setListener();
        init();
    }

    private void init() {
        fragments.add(new HomeFragment());
        fragments.add(new OrderFragment());
        fragments.add(new PersonFragment());
        fragments.add(new MoreFragment());

        //设置默认选中
        onclick.onClick(llTab.getChildAt(0));

    }

    private void setListener() {
        childCount = llTab.getChildCount();
        for (int i = 0; i < childCount; i++){
            FrameLayout childAt = (FrameLayout) llTab.getChildAt(i);
            childAt.setOnClickListener(onclick);
        }
    }
    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = llTab.indexOfChild(v);
            changeUI(index);
            changeFragment(index);
        }
    };

    private void changeFragment(int index) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main_fragments,fragments.get(index),index+"")
                .commit();
    }

    private void changeUI(int index) {
        Toast.makeText(this,index+"",Toast.LENGTH_SHORT).show();
        for (int i = 0; i < childCount ; i++ ){
            if (i == index){
                setEnableStatus(llTab.getChildAt(i),false);
            }else {
                setEnableStatus(llTab.getChildAt(i),true);
            }
        }
    }

    private void setEnableStatus(View v,boolean b) {
        v.setEnabled(b);
        if (v instanceof ViewGroup){
            int childCount = ((ViewGroup) v).getChildCount();
            for (int i = 0; i < childCount ; i++){
                setEnableStatus(((ViewGroup) v).getChildAt(i),b);
            }
        }
    }


}
