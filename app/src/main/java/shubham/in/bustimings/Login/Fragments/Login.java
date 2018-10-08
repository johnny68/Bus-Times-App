package shubham.in.bustimings.Login.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shubham.in.bustimings.Misc.SharedConstants;
import shubham.in.bustimings.POJO.APIClient;
import shubham.in.bustimings.POJO.APIInterface;
import shubham.in.bustimings.POJO.User;
import shubham.in.bustimings.R;
import shubham.in.bustimings.UI.MainActivity;
import shubham.in.bustimings.Utils.CustomToast;

import static shubham.in.bustimings.Misc.SharedConstants.IS_LOGGED_IN;
import static shubham.in.bustimings.Misc.SharedConstants.PREF_LOGIN;
import static shubham.in.bustimings.Misc.SharedConstants.UNIQUE_ID;
import static shubham.in.bustimings.Misc.SharedConstants.regEx;

public class Login extends Fragment implements View.OnClickListener{

    protected View view;
    private static FragmentManager fragmentManager;
    private static ProgressDialog progressDialog;
    private static SharedPreferences sharedPreferences;
    private EditText MobileNumber, password;
    private Button loginButton;
    private TextView forgotPassword, signUp;
    private CheckBox show_hide_password;
    private RelativeLayout loginLayout;
    private Animation shakeAnimation;
    APIInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        view = layoutInflater.inflate(R.layout.login_layout, null, false);
        sharedPreferences = getActivity().getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        initViews();
        setListeners();


        return view;
    }

    private void setListeners() {

        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    show_hide_password.setText(R.string.hide_pwd);
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    show_hide_password.setText(R.string.show_pwd);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    private void initViews() {
        fragmentManager = this.getFragmentManager();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        MobileNumber = (EditText)view.findViewById(R.id.login_emailid);
        password = (EditText)view.findViewById(R.id.login_password);
        loginButton = (Button)view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView)view.findViewById(R.id.forgot_password);
        signUp = (TextView)view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox)view.findViewById(R.id.show_hide_password);
        loginLayout = (RelativeLayout)view.findViewById(R.id.login_layout);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try
        {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),xrp);
            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            show_hide_password.setTextColor(csl);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.loginBtn:
                checkValidation();
                break;
            case R.id.forgot_password:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        .replace(R.id.frame, new ForgotPassword(), SharedConstants.Forgot_Password).commit();
                break;
            case R.id.createAccount:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frame, new SignUp(), SharedConstants.SignUp_Fragment).commit();
                break;
        }

    }

    private void checkValidation() {

        String CemailID = MobileNumber.getText().toString();
        String Cpaassword = password.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UNIQUE_ID, String.valueOf(CemailID)).apply();
        Log.d("UserProlie :", UNIQUE_ID);
        if (CemailID.equals("") || Cpaassword.equals("") || Cpaassword.length() == 0){
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().showToast(getActivity(), view, getString(R.string.credentials));
        }
        else
        {
            progressDialog.setMessage(getString(R.string.logging));
            showDialog();
            LoginProcess(CemailID, Cpaassword);
        }

    }

    private void LoginProcess(final String MobileNumber, final String cpaassword) {

        Call<User> call = apiInterface.getUser(MobileNumber);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("User Data", response.code()+"");
                User resource = response.body();
                List<User.DataEntity> datumList = resource.getData();
                for (User.DataEntity datum : datumList){
                    String ID = datum.getEmailid();
                    goToProfile(ID);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("User Name ", "Unable to Login");
                t.getCause().printStackTrace();
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.notregistered)
                        .content(R.string.contentNotRegistered, false)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                fragmentManager
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                        .replace(R.id.frame, new SignUp(), SharedConstants.SignUp_Fragment).commit();
                            }
                        })
                        .build();
            }
        });

    }

    private void hideDialog() {
        if (progressDialog.isShowing() && getActivity().getWindow().getDecorView().isShown())
        {
            progressDialog.hide();
        }
    }

    private void showDialog(){
        if (!progressDialog.isShowing() && getActivity().getWindow().getDecorView().isShown())
        {
            progressDialog.show();
        }

    }
    private  void goToProfile(String id){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
        getActivity().finish();
    }
}