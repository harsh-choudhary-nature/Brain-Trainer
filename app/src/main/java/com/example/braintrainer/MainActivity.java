package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
//    boolean gameIsActive=true;
    char operations[]={'+','-','*'};
    int number1,number2,correctAns;
    int correct,asked;
    int total=15;
    CountDownTimer timer;
    //0=> operation is +, both numbers in range of [1,100]
    //1=> operation is -, number 1 in [50,99], number 2 in range [1,100]
    //2=> operation is *, both numbers in [1,20]
    public void check(View view){
        int selected=Integer.parseInt(((TextView)view).getText().toString());
        if(selected==correctAns){
            correct++;
        }
        ((TextView)findViewById(R.id.scoreValueId)).setText(Integer.toString(correct)+"/"+Integer.toString(asked));
        if(correct==total){
            timer.cancel();
            GameOver("You did it all correct!");
        }else if(asked==total){
            timer.cancel();
            GameOver("You lost!");
        }
        setQuestion();
    }
    public void reset(View view){
        correct=0;
        startGame();
    }
    public void GameOver(String toDisplay){
        ((TextView)findViewById(R.id.questionTextId)).setVisibility(View.INVISIBLE);
        ((RelativeLayout)findViewById(R.id.relativeLayoutId)).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.scoreValueId)).setText(Integer.toString(correct)+"/"+Integer.toString(asked));
        ((TextView)findViewById(R.id.messageId)).setText(toDisplay);
        ((TextView)findViewById(R.id.finalScoreId)).setText(Integer.toString(correct)+"/"+Integer.toString(total));
        ((LinearLayout)findViewById(R.id.resultId)).setVisibility(View.VISIBLE);
    }
    public void setQuestion(){
        asked++;
        int operation= (int)Math.floor(Math.random()*3);
        if(operation==0){
            number1=(int)Math.floor(1+Math.random()*(101-1));
            number2=(int)Math.floor(1+Math.random()*(101-1));
            correctAns=number1+number2;
        }else if(operation==1){
            number1=(int)Math.floor(1+Math.random()*(101-1));
            number2=(int)Math.floor(1+Math.random()*(101-1));
            correctAns=number1-number2;
        }else{
            //operation is 2
            number1=(int)Math.floor(1+Math.random()*(21-1));
            number2=(int)Math.floor(1+Math.random()*(21-1));
            correctAns=number1*number2;
        }
        String question_string=Integer.toString(number1)+" "+operations[operation]+" "+Integer.toString(number2)+" = ";
        ((TextView)findViewById(R.id.questionTextId)).setText(question_string);
        int correctAnsIndex=(int)Math.floor(Math.random()*4);
        int i=0;
        int options[]={-1,-1,-1};
        int option;
        while(i<3){
            option=(int)Math.floor((correctAns-10)+Math.random()*(20));
            while(option==correctAns) {
                option=(int)Math.floor((correctAns-10)+Math.random()*(20));
            }
            options[i++]=option;
        }
        i=0;
        ((TextView) findViewById(R.id.option0Id)).setText(correctAnsIndex==0?""+correctAns:""+options[i++]);
        ((TextView) findViewById(R.id.option1Id)).setText(correctAnsIndex==1?""+correctAns:""+options[i++]);
        ((TextView) findViewById(R.id.option2Id)).setText(correctAnsIndex==2?""+correctAns:""+options[i++]);
        ((TextView) findViewById(R.id.option3Id)).setText(correctAnsIndex==3?""+correctAns:""+options[i++]);
    }
    public void startGame(){
        ((TextView)findViewById(R.id.questionTextId)).setVisibility(View.VISIBLE);
        ((RelativeLayout)findViewById(R.id.relativeLayoutId)).setVisibility(View.VISIBLE);
        ((LinearLayout)findViewById(R.id.resultId)).setVisibility(View.INVISIBLE);
        correct=0;
        asked=0;
        ((TextView)findViewById(R.id.scoreValueId)).setText(Integer.toString(correct)+"/"+Integer.toString(asked));
        setQuestion();
        timer=new CountDownTimer(30100,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                ((TextView)findViewById(R.id.timeLeftValueId)).setText(String.valueOf(millisUntilFinished/1000)+"s");
            }
            @Override
            public void onFinish() {
                ((TextView)findViewById(R.id.timeLeftValueId)).setText("0s");
                if(correct==total){
                    GameOver("You did it all correct!");
                }else{
                    GameOver("OOPs! Time is Up!");
                }
            }
        }.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startGame();
    }
}