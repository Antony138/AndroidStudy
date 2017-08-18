package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sae_antony on 15/08/2017.
 */

public class CrimeListFragment extends Fragment {

    private static final String TAG = "CrimeListFragment";

    // 声明表格视图对象
    private RecyclerView mCrimeRecyclerView;

    // 声明一个Adapter
    // Adapter介于表格和数据集之间。是链接后端数据和前端显示的适配接口(相当于iOS的DataSource？)
    private CrimeAdapter mAdapter;

    // 初始化视图(表格)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    // 刷新表格
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    // ViewHolder：只负责UI部分(简单的UI可能不没什么用，复杂的UI才能体现ViewHolder的价值)
    // RecyclerView不会自己创建views，这是ViewHolder的任务
    // 链接UI，并赋值给UI(TextView)
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime mCrime;

        private TextView mTtileTextView;
        private TextView mDateTextView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            // 添加点击事件
            itemView.setOnClickListener(this);

            // 链接表格中的两个textView
            mTtileTextView = (TextView)itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView)itemView.findViewById(R.id.crime_date);
        }

        public void bind(Crime crime) {

            // 赋值对象(在onBindViewHolder拿到值并传过来)
            mCrime = crime;
            // 打印mCrime.getTitle()有内容，证明是没有正确创建mTtileTextView
            // 最终原因list_item_crime.xml没有布局mTtileTextView和mDateTextView(没有UI，也没有id，所以两个控件为null)
            Log.d(TAG, "打印" + mCrime.getTitle());

            // 设置表格中两个view的文本内容
            mTtileTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }

        @Override
        // 方法名，一定要是onClick
        public void onClick(View view) {
            Toast.makeText(getActivity(), mCrime.getTitle() + "clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    // Adapter，是链接后端数据和前端显示的适配接口(相当于iOS的DataSource？)
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        // 声明数组,并拿到数据源
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        // 调用onCreateViewHolder()方法创建viewHolder
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater, parent);
        }

        // Bind(绑定数据)
        // 拿数据并传数据(相当于tableView:cellForRowAtIndexPath:方法中拿数据)
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            // 创建Crime对象(用于数据源的)
            Crime crime = mCrimes.get(position);
            // 传递Crime对象
            holder.bind(crime);
        }

        // 拿到表格的条数(相当于iOS的tableView:numberOfRowsInSection:方法)
        @Override
        public int getItemCount() {
            Log.d(TAG, "表格的item数量是" + mCrimes.size());
            return mCrimes.size();
        }
    }
}