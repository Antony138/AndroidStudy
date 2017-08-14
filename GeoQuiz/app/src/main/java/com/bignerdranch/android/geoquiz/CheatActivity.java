package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    // extra的键
    private static final String EXTRA_ANSWER_IS_TURE = "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";

    // 用于保存传过来的数据
    private boolean mAnswerIsTure;

    private TextView mAnswerTextView;
    private Button mShowAnswer;

    // 其他acitvity没必要知道需要传什么Intent给你，所以，最好是自己创建好Intent(让其他activity调用这个方法即可)
    // 如果要传更多参数，加在answerIsTure后面即可。
    public static Intent newIntent(Context packageContext, boolean answerIsTure) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView()将activity和界面(layout)联系起来
        setContentView(R.layout.activity_cheat);

        // 第二个参数：如果找不到这个key，设置一个默认值false
        mAnswerIsTure = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TURE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTure) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }

                // 返回结果（表示使用者点解了按钮——看了答案？）
                setAnswerShownResult(true);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
