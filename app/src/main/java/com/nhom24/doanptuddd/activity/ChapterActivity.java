package com.nhom24.doanptuddd.activity;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.model.NovelChapter;
import com.nhom24.doanptuddd.repository.NovelChapterRepository;

import java.util.Locale;

public class ChapterActivity extends AppCompatActivity {
    private TextView textView;
    private ScrollView scrollView;
    private Button speakButton, stopButton;
    private SeekBar seekBar;
    private TextToSpeech textToSpeech;

    private String[] paragraphs = new String[0]; // Khởi tạo mặc định
    private SpannableString spannableText;
    private int currentParagraphIndex = 0;
    private boolean isSpeaking = false;
    private int start = 0;
    private int end = 0;
    private int chapterId, bookId;
    private String fullText = ""; // Khởi tạo chuỗi rỗng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        // Khởi tạo views
        textView = findViewById(R.id.tv_description);
        scrollView = findViewById(R.id.scrollView);
        speakButton = findViewById(R.id.btn_play);
        stopButton = findViewById(R.id.btn_stop);
        seekBar = findViewById(R.id.sb_chapter);

        // Lấy bookId và chapterId từ Intent
        Intent intent = getIntent();
        bookId = intent.getIntExtra("book_id", -1);
        chapterId = intent.getIntExtra("chapter_id", 1);
        if (bookId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy ID tiểu thuyết", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Khởi tạo TextToSpeech
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("vi", "VN"));
            } else {
                Log.e("ChapterActivity", "TextToSpeech initialization failed");
            }
        });
        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // Không cần xử lý
            }

            @Override
            public void onDone(String utteranceId) {
                currentParagraphIndex++;
                cleanHighLight(start, end);

                if (currentParagraphIndex < paragraphs.length) {
                    seekBar.setProgress(currentParagraphIndex);
                    speakText(); // Tiếp tục đọc đoạn tiếp theo
                } else {
                    isSpeaking = false;
                    currentParagraphIndex = 0;
                    start = 0;
                    end = paragraphs.length > 0 ? paragraphs[0].length() : 0;
                    runOnUiThread(() -> {
                        speakButton.setEnabled(true);
                        stopButton.setEnabled(false);
                        seekBar.setProgress(0);
                    });
                }
            }

            @Override
            public void onError(String utteranceId) {
                Log.e("ChapterActivity", "TextToSpeech error: " + utteranceId);
                runOnUiThread(() -> Toast.makeText(ChapterActivity.this, "Lỗi đọc văn bản", Toast.LENGTH_SHORT).show());
            }
        });

        // Thiết lập sự kiện cho nút
        speakButton.setOnClickListener(v -> speakText());
        stopButton.setEnabled(false);
        stopButton.setOnClickListener(v -> {
            stopSpeaking();
            speakButton.setEnabled(true);
            stopButton.setEnabled(false);
        });

        // Thiết lập SeekBar
        seekBar.setMax(paragraphs.length - 1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    cleanHighLight(start, end);
                    currentParagraphIndex = progress;
                    if (isSpeaking) {
                        stopSpeaking();
                        speakText();
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

        // Tải dữ liệu từ API
        loadChapterContent();
    }

    private void loadChapterContent() {
        fullText = "Chương " + chapterId + " ";
        NovelChapterRepository repository = new NovelChapterRepository();
        repository.getNovelChapter(bookId, chapterId).observe(this, novelChapter -> {
            if (novelChapter != null && novelChapter.getContent() != null) {
                fullText += novelChapter.getContent();
                fullText = fullText.replace(". ", ".\n");
                Log.d("ChapterActivity", "fullText updated: " + fullText);
                updateTextView();
            } else {
                Log.e("ChapterActivity", "novelChapter hoặc content là null");
                Toast.makeText(this, "Không thể tải nội dung chương", Toast.LENGTH_SHORT).show();
                updateTextView(); // Cập nhật với fullText hiện tại
            }
        });
    }

    private void updateTextView() {
        // Cập nhật TextView và paragraphs
        spannableText = new SpannableString(fullText);
        textView.setText(spannableText);
        paragraphs = fullText.split("\n");
        seekBar.setMax(paragraphs.length - 1);
        if (paragraphs.length > 0) {
            end = paragraphs[0].length();
        }
    }

    private void stopSpeaking() {
        textToSpeech.stop();
        isSpeaking = false;
    }

    private void speakText() {
        if (paragraphs.length == 0) {
            Toast.makeText(this, "Không có nội dung để đọc", Toast.LENGTH_SHORT).show();
            return;
        }

        speakButton.setEnabled(false);
        stopButton.setEnabled(true);
        isSpeaking = true;

        if (currentParagraphIndex < paragraphs.length) {
            start = 0;
            for (int i = 0; i < currentParagraphIndex; i++) {
                start += paragraphs[i].length() + 1; // +1 cho ký tự \n
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
        spannableText.setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableText);
    }

    private void cleanHighLight(int start, int end) {
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