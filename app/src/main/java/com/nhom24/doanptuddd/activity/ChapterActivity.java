package com.nhom24.doanptuddd;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ChapterActivity extends AppCompatActivity {
    private TextView textView;
    private ScrollView scrollView;
    private Button speakButton, stopButton;
    private SeekBar seekBar;
    private TextToSpeech textToSpeech;

    private String[] paragraphs;
    private SpannableString spannableText;

    private int currentParagraphIndex = 0;
    private boolean isSpeaking = false;
    private int start = 0;
    private int end = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        textView = findViewById(R.id.tv_description);
        scrollView = findViewById(R.id.scrollView);
        speakButton = findViewById(R.id.btn_play);
        stopButton = findViewById(R.id.btn_stop);
        seekBar = findViewById(R.id.sb_chapter);

        String fullText = "Chương 317: Công pháp Tiên môn\n" + "\n" + "Nghe đến Bạch Chân Chân nói chuyện, Trương Vũ trong lòng hơi động một chút: \"Thập đại thi đấu vòng tròn sao?\" Đây cũng không phải là hắn lần đầu tiên nghe được bốn chữ này, bất quá Trương Vũ một mực vùi đầu việc học cùng công trường, đối với cái này thi đấu vòng tròn như cũ chỗ biết không nhiều.\n" + "Ở trong lý giải thô thiển của hắn, đây tựa hồ là một cái thi đấu thuộc về sinh viên đại học.\n" + "Nhưng nhìn lấy Bạch Chân Chân giờ ph·út này mặc lễ phục, còn có trong miệng nghi thức khai mạc, Trương Vũ phát hiện bản thân trước kia khả năng xem thường cái này thập đại thi đấu vòng tròn tầm quan trọng.\n" + "Mà Bạch Chân Chân giống như có thể đoán được Trương Vũ nghi hoặc trong lòng cùng tò mò, mở miệng giải thích: \"Thập đại thi đấu vòng tròn là chỉ có học sinh đại học của mười đại học đỉnh tiêm lớn mới có thể tham dự thi đấu.\"\n" + "\"Mà trong đó trừ mỗi cái thi đấu chuyên ngành của chuyên ngành bên ngoài, được coi trọng nhất, cũng bị người xem trọng nhất. . . Lại là thi đấu quân sự.\"\"Thi đấu quân sự từ sớm nhất thập đại đấu võ diễn hóa mà tới, trước mắt bao quát thi đấu kỹ năng quân sự cùng thi đấu diễn tập quân sự. . .\"\n" + "Nghe lấy giải thích của Bạch Chân Chân, Trương Vũ dần dần đối với cái này thập đại thi đấu vòng tròn có càng sâu hiểu rõ.Ở trong từng trận thi đấu quân sự, mỗi cái tuyển thủ cùng đội ngũ của đại học sẽ thể hiện ra từng người trường học cùng công ty kỹ thuật Tiên đạo mũi nhọn.\n" + "\n" + "Cái này đã là một trận quảng bá triển lãm kỹ thuật Tiên đạo, cũng là triển lãm vũ lực của mỗi cái đại học.";
//        String fullText = "aaaaaaaaaa\n" + "bbbbbbbbbb\n" + "cccccccccc\n" + "dddddddddd\n" + "e e e e e e e e e e\n";
        paragraphs = fullText.split("\n");
        spannableText = new SpannableString(fullText);
        textView.setText(fullText);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("vi", "VN"));
                }
            }
        });
        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                currentParagraphIndex++;
                cleanHightLight(start, end);

                if (currentParagraphIndex < paragraphs.length) {
                    seekBar.setProgress(currentParagraphIndex);
                } else {
                    isSpeaking = false;
                    currentParagraphIndex = 0;
                    start = 0;
                    end = paragraphs[0].length();
                    runOnUiThread(() -> {
                        speakButton.setEnabled(true);
                        stopButton.setEnabled(false);
                        seekBar.setProgress(0);
                    });
                }
            }

            @Override
            public void onError(String utteranceId) {

            }
        });

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakText();
            }
        });
        stopButton.setEnabled(false);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSpeaking();
                speakButton.setEnabled(true);
                stopButton.setEnabled(false);
            }
        });

        seekBar.setMax(paragraphs.length - 1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cleanHightLight(start, end);
                currentParagraphIndex = progress;
                if (isSpeaking) {
                    stopSpeaking();
                    speakText();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void stopSpeaking() {
        textToSpeech.stop();
        isSpeaking = false;
    }

    private void speakText() {
        speakButton.setEnabled(false);
        stopButton.setEnabled(true);
        isSpeaking = true;

        if (currentParagraphIndex < paragraphs.length) {
            start = 0;
            for (int i = 0; i < currentParagraphIndex; i++) {
                start += paragraphs[i].length() + 1;
            }
            end = start + paragraphs[currentParagraphIndex].length();
            highlightsParagraph(start, end);

            textToSpeech.speak(paragraphs[currentParagraphIndex], TextToSpeech.QUEUE_FLUSH, null, "utterance_" + currentParagraphIndex);
            scrollToSentence(currentParagraphIndex);
        }
    }

    private void scrollToSentence(int currentParagraphIndex) {
        textView.post(() -> {
            if (textView.getLayout() != null) {
                int line = textView.getLayout().getLineForOffset(start);
                int y = textView.getLayout().getLineTop(line);
                scrollView.smoothScrollTo(0, y);
            }
        });
    }

    private void highlightsParagraph(int start, int end) {
//        Log.d("TAG", "highlightsParagraph: "+start+" "+end);
        spannableText.setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableText.setSpan(new ForegroundColorSpan(Color.BLACK), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableText);
    }

    private void cleanHightLight(int start, int end) {
        spannableText.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableText);
    }


    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}