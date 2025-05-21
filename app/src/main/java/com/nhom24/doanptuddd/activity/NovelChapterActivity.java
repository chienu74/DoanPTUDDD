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
    private TextToSpeech textToSpeech;

    private String fullText = "";
    private String[] paragraphs = new String[0];
    private SpannableString spannableText;
    private boolean isSpeaking = false;
    private int nextChapterId, previousChapterId;
    private int currentParagraphIndex = 0;
    private int start = 0;
    private int end = 0;
    private int chapterId, bookId;
    private int textColor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_chapter);

        textView = findViewById(R.id.tv_description);
        scrollView = findViewById(R.id.scrollView);
        speakButton = findViewById(R.id.btn_play);
        seekBar = findViewById(R.id.sb_chapter);
        nextButton = findViewById(R.id.btn_next);
        previousButton = findViewById(R.id.btn_previous);
        settingButton = findViewById(R.id.btn_settings);

        Intent intent = getIntent();
        bookId = intent.getIntExtra("book_id", -1);
        chapterId = intent.getIntExtra("chapter_id", -1);

        ArrayList<NovelChapter> chapters = intent.getParcelableArrayListExtra("chapters");
        if (chapters != null) {
            for (int i = 0; i < chapters.size(); i++) {
                if (chapters.get(i).getId() == chapterId) {
                    if (i == 0) {
                        previousButton.setEnabled(false);
                    }
                    if (i > 0) {
                        previousChapterId = chapters.get(i - 1).getId();
                    }
                    if (i < chapters.size() - 1) { // Chỉ truy cập next nếu không phải chapter cuối
                        nextChapterId = chapters.get(i + 1).getId();
                    } else {
                        nextButton.setEnabled(false);
                    }
                    fullText += chapters.get(i).getTitle() + ". ";
                    break;
                }
            }
        }

        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("vi", "VN"));
                textToSpeech.setSpeechRate(1.0f);
            } else {
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
                    isSpeaking = false;
                    currentParagraphIndex = 0;
                    start = 0;
                    end = paragraphs.length > 0 ? paragraphs[0].length() : 0;
                    runOnUiThread(() -> {
                        updateSpeakButtonState();
                        seekBar.setProgress(0);
                    });
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

        loadChapterContent();

        Log.e("ChapterActivity", "previousChapterId: " + previousChapterId);
        Log.e("ChapterActivity", "nextChapterId: " + nextChapterId);

        nextButton.setOnClickListener(v -> {
            Intent i = new Intent(NovelChapterActivity.this, NovelChapterActivity.class);
            i.putExtra("book_id", bookId);
            i.putExtra("chapter_id", nextChapterId);
            i.putExtra("chapters", intent.getParcelableArrayListExtra("chapters"));
            startActivity(i);
        });
        previousButton.setOnClickListener(v -> {
            Intent i = new Intent(NovelChapterActivity.this, NovelChapterActivity.class);
            i.putExtra("book_id", bookId);
            i.putExtra("chapter_id", previousChapterId);
            i.putExtra("chapters", intent.getParcelableArrayListExtra("chapters"));
            startActivity(i);
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    private void loadChapterContent() {
        NovelChapterRepository repository = new NovelChapterRepository();
        repository.getNovelChapter(bookId, chapterId).observe(this, novelChapter -> {
            if (novelChapter != null) {
                String content = novelChapter.getContent();
                if (content != null) {
                    fullText += content;
                    fullText = fullText.replace(". ", ".\n");
                } else {
                    Log.e("ChapterActivity", "Content is null");
                    Toast.makeText(this, "Không thể tải nội dung chương", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("ChapterActivity", "novelChapter là null");
                Toast.makeText(this, "Không thể tải nội dung chương", Toast.LENGTH_SHORT).show();
            }
            updateTextView();
        });
    }

    private void updateTextView() {
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
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isDarkMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
        int highlightColor = isDarkMode ? Color.WHITE : Color.YELLOW;
        int textColor = Color.BLACK;
        textColor2 = isDarkMode ? Color.WHITE : Color.BLACK;

        spannableText.setSpan(new BackgroundColorSpan(highlightColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableText.setSpan(new ForegroundColorSpan(textColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableText);
    }

    private void cleanHighLight(int start, int end) {
        spannableText.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableText.setSpan(new ForegroundColorSpan(textColor2), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableText);
    }

    private void updateSpeakButtonState() {
        if (isSpeaking) {
            speakButton.setBackgroundResource(R.drawable.ic_pause);
        } else {
            speakButton.setBackgroundResource(R.drawable.ic_play);
        }
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