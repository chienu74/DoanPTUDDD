package com.nhom24.doanptuddd;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ChapterActivity extends AppCompatActivity {
    private TextView textView;
    private ScrollView scrollView;
    private Button speakButton, stopButton;
    private SeekBar seekBar;
    private TextToSpeech textToSpeech;
    private List<String> sentences;
    private int currentSentenceIndex = 0;
    private boolean isSpeaking = false;
    private SpannableString spannableText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        textView = findViewById(R.id.tv_description);
        scrollView = findViewById(R.id.scrollView);
        speakButton = findViewById(R.id.btn_play);
        stopButton = findViewById(R.id.btn_stop);
        seekBar = findViewById(R.id.sb_chapter);


        String fullText = "Chương 317: Công pháp Tiên môn\n" +
                "\n" +
                "Nghe đến Bạch Chân Chân nói chuyện, Trương Vũ trong lòng hơi động một chút: \"Thập đại thi đấu vòng tròn sao?\" Đây cũng không phải là hắn lần đầu tiên nghe được bốn chữ này, bất quá Trương Vũ một mực vùi đầu việc học cùng công trường, đối với cái này thi đấu vòng tròn như cũ chỗ biết không nhiều.\n" +
                "Ở trong lý giải thô thiển của hắn, đây tựa hồ là một cái thi đấu thuộc về sinh viên đại học.\n" +
                "Nhưng nhìn lấy Bạch Chân Chân giờ ph·út này mặc lễ phục, còn có trong miệng nghi thức khai mạc, Trương Vũ phát hiện bản thân trước kia khả năng xem thường cái này thập đại thi đấu vòng tròn tầm quan trọng.\n" +
                "Mà Bạch Chân Chân giống như có thể đoán được Trương Vũ nghi hoặc trong lòng cùng tò mò, mở miệng giải thích: \"Thập đại thi đấu vòng tròn là chỉ có học sinh đại học của mười đại học đỉnh tiêm lớn mới có thể tham dự thi đấu.\"\n" +
                "\"Mà trong đó trừ mỗi cái thi đấu chuyên ngành của chuyên ngành bên ngoài, được coi trọng nhất, cũng bị người xem trọng nhất. . . Lại là thi đấu quân sự.\"\"Thi đấu quân sự từ sớm nhất thập đại đấu võ diễn hóa mà tới, trước mắt bao quát thi đấu kỹ năng quân sự cùng thi đấu diễn tập quân sự. . .\"\n" +
                "Nghe lấy giải thích của Bạch Chân Chân, Trương Vũ dần dần đối với cái này thập đại thi đấu vòng tròn có càng sâu hiểu rõ.Ở trong từng trận thi đấu quân sự, mỗi cái tuyển thủ cùng đội ngũ của đại học sẽ thể hiện ra từng người trường học cùng công ty kỹ thuật Tiên đạo mũi nhọn.\n" +
                "\n" +
                "Cái này đã là một trận quảng bá triển lãm kỹ thuật Tiên đạo, cũng là triển lãm vũ lực của mỗi cái đại học.";
        sentences = new ArrayList<>(Arrays.asList(fullText.split("\\\n")));

        seekBar.setMax(sentences.size() - 1);

        // Khởi tạo SpannableString để bôi đen
        spannableText = new SpannableString(fullText);
        textView.setText(spannableText);

        speakButton.setOnClickListener(v -> {
            if (!isSpeaking) {
                startSpeaking();
            }
        });

        stopButton.setOnClickListener(v -> stopSpeaking());

        // Xử lý sự kiện tua trên SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    currentSentenceIndex = progress;
                    highlightSentence(currentSentenceIndex);
                    if (isSpeaking) {
                        stopSpeaking();
                        startSpeaking();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(new Locale("vi", "VN"));
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        textToSpeech.setLanguage(Locale.US);
                        Toast.makeText(ChapterActivity.this, "Tiếng Việt không được hỗ trợ, sử dụng tiếng Anh.", Toast.LENGTH_SHORT).show();
                    }
                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            runOnUiThread(() -> highlightSentence(currentSentenceIndex));
                        }

                        @Override
                        public void onDone(String utteranceId) {
                            runOnUiThread(() -> {
                                currentSentenceIndex++;
                                if (currentSentenceIndex < sentences.size()) {
                                    seekBar.setProgress(currentSentenceIndex);
                                    clearHighlight();
                                    speakNextSentence();
                                } else {
                                    stopSpeaking();
//                                    clearHighlight();
                                }
                            });
                        }

                        @Override
                        public void onError(String utteranceId) {
                            runOnUiThread(() -> Toast.makeText(ChapterActivity.this, "Lỗi khi đọc văn bản!", Toast.LENGTH_SHORT).show());
                        }
                    });
                } else {
                    Toast.makeText(ChapterActivity.this, "Khởi tạo TextToSpeech thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startSpeaking() {
        isSpeaking = true;
        speakButton.setEnabled(false);
        stopButton.setEnabled(true);
        speakNextSentence();
    }

    private void stopSpeaking() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        isSpeaking = false;
        speakButton.setEnabled(true);
        stopButton.setEnabled(false);
        clearHighlight();
    }

    private void speakNextSentence() {
        if (currentSentenceIndex < sentences.size()) {
            String sentence = sentences.get(currentSentenceIndex);
            textToSpeech.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, "utterance_" + currentSentenceIndex);
            // Cuộn ScrollView đến câu hiện tại
            scrollToSentence(currentSentenceIndex);
        }
    }

    private void highlightSentence(int sentenceIndex) {
        // Xóa bôi đen trước đó
        spannableText.removeSpan(new BackgroundColorSpan(Color.YELLOW));

        // Tính vị trí bắt đầu và kết thúc của câu
        int start = 0;
        for (int i = 0; i < sentenceIndex; i++) {
            start += sentences.get(i).length();
        }
        int end = start + sentences.get(sentenceIndex).length();

        // Áp dụng bôi đen (background vàng)
        spannableText.setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableText);
    }

    private void clearHighlight() {
        spannableText.removeSpan(new BackgroundColorSpan(Color.YELLOW));
        textView.setText(spannableText);
    }

    private void scrollToSentence(int sentenceIndex) {
        int start = 0;
        for (int i = 0; i < sentenceIndex; i++) {
            start += sentences.get(i).length();
        }
        final int offset = start;
        textView.post(() -> {
            if (textView.getLayout() != null) {
                int line = textView.getLayout().getLineForOffset(offset);
                int y = textView.getLayout().getLineTop(line);
                scrollView.smoothScrollTo(0, y);
            }
        });
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