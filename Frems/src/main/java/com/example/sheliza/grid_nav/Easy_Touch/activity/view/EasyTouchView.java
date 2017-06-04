package com.example.sheliza.grid_nav.Easy_Touch.activity.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sheliza.grid_nav.Notifi.Get;
import com.example.sheliza.grid_nav.Notifi.Notification;
import com.example.sheliza.grid_nav.R;

import java.util.Timer;
import java.util.TimerTask;

public class EasyTouchView extends View {
    private Context mContext;
    private WindowManager mWManager;
    private WindowManager.LayoutParams mViewEventMParams;
    private WindowManager.LayoutParams mRocketMParams;
    private View mTouchView;

    private ImageView mIconImageView = null;
    private PopupWindow mPopuWin;
    private View mSettingTable;
    private TextView home, notification;


    private int mTag = 0;
    private int midX;
    private int midY;
    private int mOldOffsetX;
    private int mOldOffsetY;

    private ImageView mRocketImageView = null;

    private Timer mTimer = null;
    private TimerTask mTask = null;
    private Camera camera;

    public EasyTouchView(Context context) {
        super(context);
        mContext = context;
    }

    public void initTouchViewEvent() {
        initEasyTouchViewEvent();

        initSettingTableView();
    }

    private void initEasyTouchViewEvent() {
        // 设置载入view WindowManager参数
        mWManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        midX = mWManager.getDefaultDisplay().getWidth() / 2;
        midY = mWManager.getDefaultDisplay().getHeight() / 2;
        mTouchView = LayoutInflater.from(mContext).inflate(R.layout.easy_touch_view, null);
        mIconImageView = (ImageView) mTouchView.findViewById(R.id.easy_touch_view_imageview);
        mTouchView.setBackgroundColor(Color.TRANSPARENT);

        mTouchView.setOnTouchListener(mTouchListener);
        WindowManager wm = mWManager;
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        mViewEventMParams = wmParams;
        //适配小米、魅族等手机需要悬浮框权限的问题
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
            wmParams.type = LayoutParams.TYPE_PHONE;
        } else {
            wmParams.type = LayoutParams.TYPE_TOAST;
        }

        wmParams.flags=40;
        wmParams.width = 100;
        wmParams.height = 100;
        wmParams.format = -3; // 透明
        wm.addView(mTouchView, wmParams);
    }


    private void initSettingTableView() {
        mSettingTable = LayoutInflater.from(mContext).inflate(R.layout.show_setting_table, null);
        home = (TextView) mSettingTable.findViewById(R.id.show_setting_table_item_home);
        home.setOnClickListener(mClickListener);

        notification = (TextView) mSettingTable.findViewById(R.id.notification);
        notification.setOnClickListener(mClickListener);

    }




    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.show_setting_table_item_home:
                    HomeBack();
                    break;

                case R.id.notification:
                     Notification();
                    break;
            }

        }
    };

    /**
     * 回到主界面
     */
    private void HomeBack() {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        mContext.startActivity(mHomeIntent);
        hideSettingTable();
    }

    void Notification(){
        Intent intent = new Intent(mContext, Get.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        hideSettingTable();
    }


    public void quitTouchView() {
        mWManager.removeView(mTouchView);
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        clearTimerThead();
        hideSettingTable();
    }

    private OnTouchListener mTouchListener = new OnTouchListener() {
        float lastX, lastY;
        int paramX, paramY;

        public boolean onTouch(View v, MotionEvent event) {
            final int action = event.getAction();

            float x = event.getRawX();
            float y = event.getRawY();

            if (mTag == 0) {
                mOldOffsetX = mViewEventMParams.x;
                mOldOffsetY = mViewEventMParams.y;
            }

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    motionActionDownEvent(x, y);
                    break;

                case MotionEvent.ACTION_MOVE:
                    motionActionMoveEvent(x, y);
                    break;

                case MotionEvent.ACTION_UP:
                    motionActionUpEvent(x, y);
                    break;

                default:
                    break;
            }

            return true;
        }

        private void motionActionDownEvent(float x, float y) {
            lastX = x;
            lastY = y;
            paramX = mViewEventMParams.x;
            paramY = mViewEventMParams.y;
        }

        private void motionActionMoveEvent(float x, float y) {
            int dx = (int) (x - lastX);
            int dy = (int) (y - lastY);
            mViewEventMParams.x = paramX + dx;
            mViewEventMParams.y = paramY + dy;
            mTag = 1;

            // 更新悬浮窗位置
            mWManager.updateViewLayout(mTouchView, mViewEventMParams);
        }

        private void motionActionUpEvent(float x, float y) {
            int newOffsetX = mViewEventMParams.x;
            int newOffsetY = mViewEventMParams.y;
            if (mOldOffsetX == newOffsetX && mOldOffsetY == newOffsetY) {

                mPopuWin = new PopupWindow(mSettingTable, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                mPopuWin.setTouchInterceptor(new OnTouchListener() {

                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            hideSettingTable();
                            return true;
                        }
                        return false;
                    }
                });

                mPopuWin.setBackgroundDrawable(new BitmapDrawable());
                mPopuWin.setTouchable(true);
                mPopuWin.setFocusable(true);
                mPopuWin.setOutsideTouchable(true);
                mPopuWin.setContentView(mSettingTable);

                if (Math.abs(mOldOffsetX) > midX) {
                    if (mOldOffsetX > 0) {
                        mOldOffsetX = midX;
                    } else {
                        mOldOffsetX = -midX;
                    }
                }

                if (Math.abs(mOldOffsetY) > midY) {
                    if (mOldOffsetY > 0) {
                        mOldOffsetY = midY;
                    } else {
                        mOldOffsetY = -midY;
                    }
                }

                mPopuWin.setAnimationStyle(R.style.AnimationPreview);
                mPopuWin.setFocusable(true);
                mPopuWin.update();
                mPopuWin.showAtLocation(mTouchView, Gravity.CENTER, -mOldOffsetX, -mOldOffsetY);

                // TODO
                mIconImageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

                catchSettingTableDismiss();
            } else {
                mTag = 0;
            }
        }
    };

    private void catchSettingTableDismiss() {
        mTimer = new Timer();
        mTask = new TimerTask() {

            @Override
            public void run() {
                if (mPopuWin == null || !mPopuWin.isShowing()) {
                    handler.sendEmptyMessage(0x0);
                }
            }
        };

        mTimer.schedule(mTask, 0, 100);
    }

    private void clearTimerThead() {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            mIconImageView.setBackgroundDrawable(getResources().getDrawable(R.mipmap.touch_ic));
        }

        ;
    };

    private void hideSettingTable() {
        if (null != mPopuWin) {
            mPopuWin.dismiss();
        }
    }
}