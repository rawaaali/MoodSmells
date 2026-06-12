package com.example.moodsmells.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.moodsmells.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScemtGameFragment extends Fragment {

    private TextView tvScentQuestion, tvScoree;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;


    private static class Question {
        String scent;
        String correctAnswer;
        List<String> wrongAnswers;

        Question(String scent, String correctAnswer, String w1, String w2, String w3) {
            this.scent = scent;
            this.correctAnswer = correctAnswer;
            this.wrongAnswers = new ArrayList<>();
            this.wrongAnswers.add(w1);
            this.wrongAnswers.add(w2);
            this.wrongAnswers.add(w3);
        }
    }

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scemt_game, container, false);


        tvScentQuestion = view.findViewById(R.id.tvScentQuestion);
        tvScoree = view.findViewById(R.id.tvScoree);
        btnOption1 = view.findViewById(R.id.btnOption1);
        btnOption2 = view.findViewById(R.id.btnOption2);
        btnOption3 = view.findViewById(R.id.btnOption3);
        btnOption4 = view.findViewById(R.id.btnOption4);

        // إعداد بيانات اللعبة
        prepareQuestions();
        displayQuestion();

        // إعداد المستمعين للأزرار
        View.OnClickListener optionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                checkAnswer(clickedButton.getText().toString());
            }
        };

        btnOption1.setOnClickListener(optionClickListener);
        btnOption2.setOnClickListener(optionClickListener);
        btnOption3.setOnClickListener(optionClickListener);
        btnOption4.setOnClickListener(optionClickListener);

        return view;
    }


    private void prepareQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("رائحة المطر 🌧️", "الجلوس في حديقة المنزل القديم", "ذكرى أول يوم مدرسة", "رحلة التخييم الجبلية", "المطبخ يوم العيد"));
        questionList.add(new Question("رائحة القهوة والكرواسون ☕", "صباحات الشتاء في الجامعة", "زيارة طبيب الأسنان", "شاطئ البحر في الصيف", "تنظيف غرفتي"));
        questionList.add(new Question("رائحة الياسمين 🌸", "بيت جدتي في المساء", "الذهاب للتسوق", "شراء كتب جديدة", "مباراة كرة القدم"));
        questionList.add(new Question("رائحة القرفة والهيل 🥐", "المطبخ صباح يوم العيد", "المكتبة العامة", "رحلة الطائرة", "الركض في النادي"));

        Collections.shuffle(questionList);
    }


    private void displayQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question currentQuestion = questionList.get(currentQuestionIndex);
            tvScentQuestion.setText(currentQuestion.scent);


            List<String> allOptions = new ArrayList<>(currentQuestion.wrongAnswers);
            allOptions.add(currentQuestion.correctAnswer);
            Collections.shuffle(allOptions);


            btnOption1.setText(allOptions.get(0));
            btnOption2.setText(allOptions.get(1));
            btnOption3.setText(allOptions.get(2));
            btnOption4.setText(allOptions.get(3));
        } else {

            tvScentQuestion.setText("تهانينا! أنهيت الاختبار بنجاح 🎉");
            tvScoree.setText("النتيجة النهائية: " + score + " من " + questionList.size());
            hideButtons();
        }
    }


    private void checkAnswer(String selectedAnswer) {
        Question currentQuestion = questionList.get(currentQuestionIndex);

        if (selectedAnswer.equals(currentQuestion.correctAnswer)) {
            score++;
            Toast.makeText(getContext(), "إجابة صحيحة! تنشطت الذاكرة ✨", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "إجابة خاطئة، حاول استرجاع الرائحة 🧠", Toast.LENGTH_SHORT).show();
        }


        tvScoree.setText("النتيجة: " + score);
        currentQuestionIndex++;
        displayQuestion();
    }


    private void hideButtons() {
        btnOption1.setVisibility(View.GONE);
        btnOption2.setVisibility(View.GONE);
        btnOption3.setVisibility(View.GONE);
        btnOption4.setVisibility(View.GONE);
    }
}