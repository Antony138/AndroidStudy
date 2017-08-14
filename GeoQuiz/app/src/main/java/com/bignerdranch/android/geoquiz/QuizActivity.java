package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    // 声明一个tag?
    private static final String TAG = "QuizActivity";

    //
    private static final String KEY_INDEX = "index";

    private static final int REQUEST_CODE_CHEAT = 0;

    // 保存CheatActivity返回的结果：使用者是否看了答案
    private boolean mIsCheater;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    // 声明并且赋值一个数组
    // 在复杂的项目中，这些数据是不应该放在controller这一层的，而是应该放在model层。
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTure) {
        boolean answerIsTure = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTure == answerIsTure) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 进行打印，第一个参数：一个字符串tag，第二个参数，要打印的字符串信息
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        // iOS中通过nib文件拖线把UI和代码链接起来，Android则通过「resource ID」来进行绑定。
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        // 通过updateQuestion()方法来设置textView的文本了，所以注释了下面的内容
//        // 拿到question的ID
//        int question = mQuestionBank[mCurrentIndex].getTextResId();
//        // setText()方法的参数，传入的是一个ID，而不是文本
//        mQuestionTextView.setText(question);

        // 为TextView增加点击事件
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        // listener自己作为参数？
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个弹出框T:oast
                // first parameter: the instance of QuizActivity as the Context
                // second parameter: resource ID of the string that the toast should display
                // third parameter: how long the toast should be visible
//                Toast.makeText(QuizActivity.this,
//                        R.string.incorrect_toast,
//                        Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.flse_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(QuizActivity.this,
//                        R.string.correct_toast,
//                        Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1/5的余数就是1，2/5的余数是2……
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
//                int question = mQuestionBank[mCurrentIndex].getTextResId();
//                mQuestionTextView.setText(question);
                mIsCheater = false;
                updateQuestion();
            }
        });

        mPrevButton = (Button) findViewById(R.id.previou_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取绝对值/absolute, 再求余
                mCurrentIndex =  Math.abs(mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });


        // 连接控件
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        // 添加点击事件
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CheatActivity
                // 直接跳转,没有传数据
                // 创建Intent对象
//                Intent i = new Intent(QuizActivity.this, CheatActivity.class);

                // 跳转并传参数
                // 拿到数据
                boolean answerIsTure = mQuestionBank[mCurrentIndex].isAnswerTrue();
                // 创建Intent(不需要导入CheatActivity头文件，也能调用CheatActivity里面的方法？)
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTure);

                // 启动activity
                //
//                startActivity(i);
                //
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();
    }

    // 点击返回按钮，会调用onActivityResult()方法？
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    // 以下几个方法，再加上上面的onCreate()，可以看到一个Activity的Lifecyle(所以，Activity更类似iOS的Controller)
    // @Override的作用：确保你重写方法时，没有写错方法名字
    @Override
    public void onStart() {
        // 调用父类实现，是必须的。为什么？
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestory() called");
    }
}
