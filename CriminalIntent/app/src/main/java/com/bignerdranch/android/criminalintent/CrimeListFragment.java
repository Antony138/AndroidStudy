package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 写了这个menu(加号)才能显示出来。
        setHasOptionsMenu(true);
    }

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

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    // 创建添加按钮
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    // 刷新表格
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    // ViewHolder：只负责UI部分(简单的UI可能不没什么用，复杂的UI才能体现ViewHolder的价值)
    // RecyclerView不会自己创建views，这是ViewHolder的任务
    // 链接UI，并赋值给UI(TextView)
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime mCrime;

        private TextView mTtileTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            // 添加点击事件
            itemView.setOnClickListener(this);

            // 链接表格中的两个textView
            mTtileTextView = (TextView)itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView)itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView)itemView.findViewById(R.id.crime_solved);
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
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        // 方法名，一定要是onClick
        public void onClick(View view) {

            // 点击cell，弹一个提示框
//            Toast.makeText(getActivity(), mCrime.getTitle() + "clicked!", Toast.LENGTH_SHORT).show();

            // 页面跳转: 从fragment跳activity
//            Intent intent = new Intent(getActivity(), CrimeActivity.class);
            // 页面跳转 + 传递数据
            // 创建新Intent的方法，由CrimeActivity负责实现。这里只负责传递id(使用者点击了哪个cell)
//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());

            // 跳到可以滑动的PagerView
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());

            // 跳转(from fragment to activity)
            startActivity(intent);
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
