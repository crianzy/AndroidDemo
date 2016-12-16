package com.czy.androiddialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by chenzhiyong on 2016/11/4.
 */

public class CustomAlertDialog extends AppCompatDialog {


    public CharSequence mTitle;
    public CharSequence mMessage;

    TextView mTitleTxt;
    TextView mMessageTxt;
    Button mPositiveButton;
    Button mNeutralButton;
    Button mNegativeButton;

    public CharSequence mPositiveButtonText;
    public View.OnClickListener mPositiveButtonListener;

    public CharSequence mNeutralButtonText;
    public View.OnClickListener mNeutralButtonListener;

    public CharSequence mNegativeButtonText;
    public View.OnClickListener mNegativeButtonListener;


    CustomAlertDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.custom_alert_dialog);

        mTitleTxt = (TextView) findViewById(R.id.alertTitle);
        mMessageTxt = (TextView) findViewById(R.id.message);
        mPositiveButton = (Button) findViewById(R.id.button_positive);
        mNegativeButton = (Button) findViewById(R.id.button_negative);
    }


    static int resolveDialogTheme(Context context, int resid) {
        if (resid >= 0x01000000) {   // start of real resource IDs.
            return resid;
        } else {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.alertDialogTheme, outValue, true);
            return outValue.resourceId;
        }
    }

    public static class Builder {

        private int mTheme;
        private Context mContext;

        public CharSequence mTitle;
        public CharSequence mMessage;

        public CharSequence mPositiveButtonText;
        public View.OnClickListener mPositiveButtonListener;

        public CharSequence mNegativeButtonText;
        public View.OnClickListener mNegativeButtonListener;

        public CharSequence mNeutralButtonText;
        public View.OnClickListener mNeutralButtonListener;

        public boolean mCancelable;

        public OnCancelListener mOnCancelListener;
        public OnDismissListener mOnDismissListener;

        public Builder(Context context) {
            this(context, resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int theme) {
            mContext = context;
            mTheme = theme;
        }

        public Context getContext() {
            return mContext;
        }

        public CustomAlertDialog.Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public CustomAlertDialog.Builder setTitle(int titleId) {
            mTitle = mContext.getString(titleId);
            return this;
        }

        public CustomAlertDialog.Builder setMessage(CharSequence message) {
            mMessage = message;
            return this;
        }

        public CustomAlertDialog.Builder setMessage(int messageId) {
            mMessage = mContext.getString(messageId);
            return this;
        }

        public CustomAlertDialog.Builder setPositiveButton(CharSequence text, final View.OnClickListener listener) {
            mPositiveButtonText = text;
            mPositiveButtonListener = listener;
            return this;
        }

        public CustomAlertDialog.Builder setPositiveButton(int textId, final View.OnClickListener listener) {
            mPositiveButtonText = mContext.getText(textId);
            mPositiveButtonListener = listener;
            return this;
        }

        public CustomAlertDialog.Builder setNegativeButton(CharSequence text, final View.OnClickListener listener) {
            mNegativeButtonText = text;
            mNegativeButtonListener = listener;
            return this;
        }

        public CustomAlertDialog.Builder setNegativeButton(int textId, final View.OnClickListener listener) {
            mNegativeButtonText = mContext.getText(textId);
            mNegativeButtonListener = listener;
            return this;
        }

        public CustomAlertDialog.Builder setNeutralButton(CharSequence text, final View.OnClickListener listener) {
            mNeutralButtonText = text;
            mNeutralButtonListener = listener;
            return this;
        }

        public CustomAlertDialog.Builder setNeutralButton(int textId, final View.OnClickListener listener) {
            mNeutralButtonText = mContext.getText(textId);
            mNeutralButtonListener = listener;
            return this;
        }

        public CustomAlertDialog.Builder setOnCancelListener(OnCancelListener onCancelListener) {
            mOnCancelListener = onCancelListener;
            return this;
        }

        public CustomAlertDialog.Builder setOnDismissListener(OnDismissListener onDismissListener) {
            mOnDismissListener = onDismissListener;
            return this;
        }

        public CustomAlertDialog.Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public void apply(CustomAlertDialog dialog) {
            if (!TextUtils.isEmpty(mTitle)) {
                dialog.mTitleTxt.setText(mTitle);
            }

            if (!TextUtils.isEmpty(mMessage)) {
                dialog.mMessageTxt.setText(mMessage);
            }
            if (!TextUtils.isEmpty(mPositiveButtonText)) {
                dialog.mPositiveButton.setVisibility(View.VISIBLE);
                dialog.mPositiveButton.setText(mPositiveButtonText);
                dialog.mPositiveButton.setOnClickListener(mPositiveButtonListener);
            } else {
                dialog.mPositiveButton.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mNegativeButtonText)) {
                dialog.mNegativeButton.setVisibility(View.VISIBLE);
                dialog.mNegativeButton.setText(mNegativeButtonText);
                dialog.mNegativeButton.setOnClickListener(mPositiveButtonListener);
            } else {
                dialog.mNegativeButton.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mNeutralButtonText)) {
                dialog.mNeutralButton.setVisibility(View.VISIBLE);
                dialog.mNeutralButton.setText(mNeutralButtonText);
                dialog.mNeutralButton.setOnClickListener(mPositiveButtonListener);
            } else {
                dialog.mNeutralButton.setVisibility(View.GONE);
            }
        }

        public CustomAlertDialog create() {
            final CustomAlertDialog dialog = new CustomAlertDialog(mContext, mTheme);
            apply(dialog);
            dialog.setCancelable(mCancelable);
            if (mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(mOnCancelListener);
            dialog.setOnDismissListener(mOnDismissListener);
            return dialog;
        }

        public CustomAlertDialog show() {
            CustomAlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }


}
