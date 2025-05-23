package com.nhom24.doanptuddd.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.fragment.BottomSheetFragment;
import com.nhom24.doanptuddd.model.NovelChapter;
import com.nhom24.doanptuddd.repository.NovelChapterRepository;

import java.util.ArrayList;
import java.util.Locale;

public class NovelChapterActivity extends AppCompatActivity {
    private TextView textView;
    private ScrollView scrollView;
    private Button speakButton, nextButton, previousButton, settingButton;
    private SeekBar seekBar;
    private ProgressBar progressBar;
    private TextToSpeech textToSpeech;

    private ArrayList<NovelChapter> chapters;
    private String fullText = "";
    private String[] paragraphs = new String[0];
    private SpannableString spannableText;
    private boolean isSpeaking = false;
    private int nextChapterId, previousChapterId;
    private int currentParagraphIndex = 0;
    private int start = 0;
    private int end = 0;
    private int chapterId, novelId, indexChapterId;
    private int textColor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_chapter);
        initView();

        Intent intent = getIntent();
        novelId = intent.getIntExtra("book_id", -1);
        chapterId = intent.getIntExtra("chapter_id", -1);

        chapters = intent.getParcelableArrayListExtra("chapters");
        if (chapters == null || chapters.isEmpty()) {
            Toast.makeText(this, "Không có danh sách chương", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        for (int i = 0; i < chapters.size(); i++) {
            if (chapters.get(i).getId() == chapterId) {
                indexChapterId = i;
                fullText = chapters.get(i).getTitle() + ".\n";
                updateButtonStates();
                break;
            }
        }

        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(new Locale("vi", "VN"));
                textToSpeech.setSpeechRate(1.0f);
            } else {
                Toast.makeText(this, "Không thể khởi tạo TextToSpeech", Toast.LENGTH_SHORT).show();
                Log.e("ChapterActivity", "TextToSpeech initialization failed");
            }
        });

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
            }

            @Override
            public void onDone(String utteranceId) {
                currentParagraphIndex++;
                cleanHighLight(start, end);

                if (currentParagraphIndex < paragraphs.length) {
                    seekBar.setProgress(currentParagraphIndex);
                    speakText();
                } else {
                    if (indexChapterId < chapters.size() - 1 && isSpeaking) {
                        runOnUiThread(() -> loadNextChapter());
                    } else {
                        isSpeaking = false;
                        currentParagraphIndex = 0;
                        start = 0;
                        end = paragraphs.length > 0 ? paragraphs[0].length() : 0;
                        runOnUiThread(() -> {
                            updateSpeakButtonState();
                            seekBar.setProgress(0);
                            if (indexChapterId >= chapters.size() - 1) {
                                Toast.makeText(NovelChapterActivity.this, "Đã đọc hết truyện", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(String utteranceId) {
                Log.e("ChapterActivity", "TextToSpeech error: " + utteranceId);
                runOnUiThread(() -> {
                    Toast.makeText(NovelChapterActivity.this, "Lỗi đọc văn bản", Toast.LENGTH_SHORT).show();
                    isSpeaking = false;
                    updateSpeakButtonState();
                });
            }
        });

        speakButton.setOnClickListener(v -> {
            if (isSpeaking) {
                stopSpeaking();
            } else {
                speakText();
            }
            updateSpeakButtonState();
        });

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

        settingButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment existingFragment = fragmentManager.findFragmentByTag("BottomSheetFragment");
            if (existingFragment == null) {
                float currentSpeed = 1.0f;
                SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
                currentSpeed = prefs.getFloat("speech_rate", 1.0f);
                BottomSheetFragment bottomSheet = BottomSheetFragment.newInstance(currentSpeed);
                bottomSheet.setOnSpeedChangeListener(speed -> {
                    stopSpeaking();
                    textToSpeech.setSpeechRate(speed);
                    speakText();

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putFloat("speech_rate", speed);
                    editor.apply();
                });
                bottomSheet.show(fragmentManager, "BottomSheetFragment");
            } else {
                Log.d("ChapterActivity", "Bottom Sheet is already shown");
            }
        });

        nextButton.setOnClickListener(v -> {
            stopSpeaking();
            if (indexChapterId < chapters.size() - 1) {
                chapterId = nextChapterId;
                indexChapterId++;
                fullText = chapters.get(indexChapterId).getTitle() + ".\n";
                updateButtonStates();
                loadChapterContent();
            }
        });

        previousButton.setOnClickListener(v -> {
            stopSpeaking();
            if (indexChapterId > 0) {
                chapterId = previousChapterId;
                indexChapterId--;
                fullText = chapters.get(indexChapterId).getTitle() + ".\n";
                updateButtonStates();
                loadChapterContent();
            }
        });

        loadChapterContent();
    }

    private void initView() {
        textView = findViewById(R.id.tv_description);
        scrollView = findViewById(R.id.scrollView);
        speakButton = findViewById(R.id.btn_play);
        seekBar = findViewById(R.id.sb_chapter);
        nextButton = findViewById(R.id.btn_next);
        previousButton = findViewById(R.id.btn_previous);
        settingButton = findViewById(R.id.btn_settings);
        progressBar = findViewById(R.id.progress_bar_novel_chapter);

        speakButton.setContentDescription("Phát đọc");
        nextButton.setContentDescription("Chương tiếp theo");
        previousButton.setContentDescription("Chương trước");
        settingButton.setContentDescription("Cài đặt");
    }

    private void updateButtonStates() {
        previousButton.setEnabled(indexChapterId > 0);
        nextButton.setEnabled(indexChapterId < chapters.size() - 1);
        previousChapterId = indexChapterId > 0 ? chapters.get(indexChapterId - 1).getId() : -1;
        nextChapterId = indexChapterId < chapters.size() - 1 ? chapters.get(indexChapterId + 1).getId() : -1;
        Log.d("ChapterActivity", "previousChapterId: " + previousChapterId + ", nextChapterId: " + nextChapterId);
    }

    private void loadChapterContent() {
        progressBar.setVisibility(View.VISIBLE);
        NovelChapterRepository repository = new NovelChapterRepository();
        repository.getNovelChapter(novelId, chapterId).observe(this, novelChapter -> {
            progressBar.setVisibility(View.GONE);
            if (novelChapter != null && novelChapter.getContent() != null) {
                fullText += novelChapter.getContent();
                fullText = fullText.replace(". ", ".\n");
                updateTextView();
            } else {
                Log.e("ChapterActivity", "novelChapter hoặc nội dung là null");
                Toast.makeText(this, "Không thể tải nội dung chương", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNextChapter() {
        if (indexChapterId < chapters.size() - 1) {
            chapterId = nextChapterId;
            indexChapterId++;
            fullText = chapters.get(indexChapterId).getTitle() + ".\n";
            updateButtonStates();
            loadChapterContent();
            currentParagraphIndex = 0;
            seekBar.setProgress(0);
        }
    }

    private void updateTextView() {
        if (fullText.isEmpty()) {
            Toast.makeText(this, "Nội dung trống", Toast.LENGTH_SHORT).show();
            paragraphs = new String[]{""};
            seekBar.setMax(0);
            spannableText = new SpannableString("");
            textView.setText(spannableText);
            return;
        }

        spannableText = new SpannableString(fullText);
        textView.setText(spannableText);
        paragraphs = fullText.split("\n+");
        seekBar.setMax(Math.max(0, paragraphs.length - 1));
        if (paragraphs.length > 0) {
            end = paragraphs[0].length();
        }
    }

    private void stopSpeaking() {
        textToSpeech.stop();
        isSpeaking = false;
        updateSpeakButtonState();
    }

    private void speakText() {
        if (paragraphs.length == 0 || paragraphs[0].isEmpty()) {
            Toast.makeText(this, "Không có nội dung để đọc", Toast.LENGTH_SHORT).show();
            stopSpeaking();
            return;
        }

        isSpeaking = true;
        if (currentParagraphIndex < paragraphs.length) {
            start = 0;
            for (int i = 0; i < currentParagraphIndex; i++) {
                start += paragraphs[i].length() + 1;
            }
            end = start + paragraphs[currentParagraphIndex].length();
            highlightsParagraph(start, end);
            textToSpeech.speak(paragraphs[currentParagraphIndex], TextToSpeech.QUEUE_FLUSH, null,
                    chapterId + "_utterance_" + currentParagraphIndex);
            scrollToSentence(currentParagraphIndex);
        }
    }

    private void scrollToSentence(int currentParagraphIndex) {
        textView.post(() -> {
            if (textView.getLayout() != null && start < spannableText.length()) {
                int line = textView.getLayout().getLineForOffset(start);
                int y = textView.getLayout().getLineTop(line);
                scrollView.smoothScrollTo(0, y);
            }
        });
    }

    private void highlightsParagraph(int start, int end) {
        if (start >= spannableText.length() || end > spannableText.length()) return;

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isDarkMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
        int highlightColor = isDarkMode ? getResources().getColor(R.color.highlight_dark, null) : getResources().getColor(R.color.highlight_light, null);
        int textColor = isDarkMode ? Color.WHITE : Color.BLACK;
        textColor2 = textColor;

        spannableText.setSpan(new BackgroundColorSpan(highlightColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableText.setSpan(new ForegroundColorSpan(textColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableText);
    }

    private void cleanHighLight(int start, int end) {
        if (start >= spannableText.length() || end > spannableText.length()) return;

        spannableText.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableText.setSpan(new ForegroundColorSpan(textColor2), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableText);
    }

    private void updateSpeakButtonState() {
        speakButton.setBackgroundResource(isSpeaking ? R.drawable.ic_pause : R.drawable.ic_play);
        speakButton.setContentDescription(isSpeaking ? "Tạm dừng đọc" : "Phát đọc");
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