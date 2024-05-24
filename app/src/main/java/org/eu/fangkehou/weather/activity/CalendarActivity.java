package org.eu.fangkehou.weather.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;

import org.eu.fangkehou.weather.BaseActivity;
import org.eu.fangkehou.weather.R;
import org.eu.fangkehou.weather.model.bean.TaskBean;
import org.eu.fangkehou.weather.service.data.TaskDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends BaseActivity {

    MaterialToolbar toolbar;
    CalendarView calenderView;
    FloatingActionButton floatingButton;
    RecyclerView taskRecycler;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        toolbar = findViewById(R.id.calendar_toolbar);
        calenderView = findViewById(R.id.calendar_view);
        floatingButton = findViewById(R.id.calendar_floating_button);
        taskRecycler = findViewById(R.id.calendar_task_recycler);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupView();
        setupData();
    }

    private void setupView() {
        floatingButton.setOnClickListener((v) -> {
            AlertDialog.Builder customizeDialog =
                    new MaterialAlertDialogBuilder(CalendarActivity.this);
            final View dialogView = LayoutInflater.from(CalendarActivity.this)
                    .inflate(R.layout.item_new_task, null);

            EditText timeEditText = dialogView.findViewById(R.id.new_task_time_edit_text);
            timeEditText.setOnFocusChangeListener((v1, hasFocus) -> {
                if (hasFocus) {
                    MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                            .build();

                    timePicker.addOnPositiveButtonClickListener((timePickerView) -> {
                        timeEditText.setText(LocalTime.of(timePicker.getHour(), timePicker.getMinute()).format(DateTimeFormatter.ofPattern("HH:mm")));
                    });
                    timePicker.addOnDismissListener((timePickerView) -> {
                        timeEditText.clearFocus();
                    });

                    timePicker.show(getSupportFragmentManager(), "DATE_PICKER_TAG");
                }
            });

            customizeDialog.setTitle("添加日程");
            customizeDialog.setView(dialogView);
            customizeDialog.setPositiveButton("确定",
                    (dialog, which) -> {
                        // 获取EditView中的输入内容
                        EditText edit_text =
                                dialogView.findViewById(R.id.new_task_edit_text);

                        String name = edit_text.getText().toString();

                        String time = timeEditText.getText().toString();

                        TaskDatabase.getInstance(CalendarActivity.this).saveTask(TaskBean.builder()
                                .name(name)
                                .date(date)
                                .time(time)
                                .build());

                        runOnUiThread(this::setupData);
                    });
            customizeDialog.show();
        });

        calenderView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Calendar c = Calendar.getInstance();
            c.set(year,month,dayOfMonth);
            date = format.format(c.getTime());
            setupData();
        });

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        date = format.format(calenderView.getDate());
    }

    private void setupData() {


        Log.i("CalendarActivity", date);

        List<TaskBean> taskBeanList = TaskDatabase.getInstance(this).getDateTasks(date);

        taskRecycler.setAdapter(new RecyclerView.Adapter() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View basicView = View.inflate(CalendarActivity.this, R.layout.item_task, null);

                return new TaskViewHolder(basicView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                TaskBean taskBean;

                if (taskBeanList.size() == 0) {
                    taskBean = TaskBean.builder()
                            .name("")
                            .time("无")
                            .build();
                } else {
                    taskBean = taskBeanList.get(position);
                }

                TaskViewHolder viewHolder = (TaskViewHolder) holder;

                viewHolder.itemTaskNameText.setText(taskBean.getName());
                viewHolder.itemTaskTimeText.setText(taskBean.getTime());
            }

            @Override
            public int getItemCount() {
                return Math.max(taskBeanList.size(), 1);
            }

            class TaskViewHolder extends RecyclerView.ViewHolder {
                TextView itemTaskTimeText;
                TextView itemTaskNameText;

                public TaskViewHolder(@NonNull View itemView) {
                    super(itemView);

                    itemTaskTimeText = itemView.findViewById(R.id.item_task_time_text);
                    itemTaskNameText = itemView.findViewById(R.id.item_task_name_text);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        taskRecycler.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}