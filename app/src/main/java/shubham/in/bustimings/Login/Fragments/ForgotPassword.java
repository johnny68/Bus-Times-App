package shubham.in.bustimings.Login.Fragments;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shubham.in.bustimings.Login.LoginActivity;
import shubham.in.bustimings.POJO.APIClient;
import shubham.in.bustimings.POJO.APIInterface;
import shubham.in.bustimings.R;
import shubham.in.bustimings.Utils.CustomToast;

import static shubham.in.bustimings.Misc.SharedConstants.regEx;

public class ForgotPassword extends Fragment implements
        View.OnClickListener {
    private static View view;
    private AppCompatButton btn_reset;
    private static EditText et_email;
    private static TextView back;
    private ProgressDialog progress;
    private boolean isResetInitiated = false;
    private String email;
    APIInterface apiInterface;

    public ForgotPassword() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forgot, container, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        et_email = (EditText) view.findViewById(R.id.et_email);
        btn_reset = (AppCompatButton) view.findViewById(R.id.btn_reset);
        back = (TextView) view.findViewById(R.id.back);
        btn_reset = (AppCompatButton)view.findViewById(R.id.btn_reset);
        progress = new ProgressDialog(getActivity());

        // Setting text selector over text Views
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            back.setTextColor(csl);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                new LoginActivity().replaceFragment("left");
                break;

            case R.id.btn_reset:
                submitButtonTask();
                break;

        }

    }

    private void submitButtonTask() {
        String getEmailId = et_email.getText().toString();
        Pattern p         = Pattern.compile(regEx);
        Matcher m         = p.matcher(getEmailId);

        if (getEmailId.equals("") || getEmailId.length() == 0) {
            new CustomToast().showToast(getActivity(), view, "Please enter your Email Id.");
        }
        else if (!m.find()) {
            new CustomToast().showToast(getActivity(), view, "Your Email Id is Invalid.");
        }
        else {
            if (!isResetInitiated)
            {
                email = et_email.getText().toString();
                progress.setMessage("Attempting to send you Verification Code...");
                showDialog();
                initiateResetProcess(email);

            }
            else {
                Log.d("Error", "Submit Button");
            }
        }
    }


    private void initiateResetProcess(String email){
        //TO-DO

    }

    private void showDialog()
    {
        if (!progress.isShowing() && getActivity().getWindow().getDecorView().isShown())
        {
            progress.show();
        }
    }
    private void hideDialog()
    {
        if (progress.isShowing() && getActivity().getWindow().getDecorView().isShown())
        {
            progress.hide();
        }

    }

    /*private void startCountdownTimer(){
        countDownTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Time remaining : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Snackbar.make(getView(), "Time Out ! Request again to reset password.", Snackbar.LENGTH_LONG).show();
                new LoginActivity().replaceFragment();
            }
        }.start();
    }*/
}