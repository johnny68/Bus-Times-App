package shubham.in.bustimings.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.util.UIUtils;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shubham.in.bustimings.Login.LoginActivity;
import shubham.in.bustimings.POJO.APIClient;
import shubham.in.bustimings.POJO.APIInterface;
import shubham.in.bustimings.POJO.User;
import shubham.in.bustimings.R;
import shubham.in.bustimings.UI.Fragments.FragmentTransaction;
import shubham.in.bustimings.Utils.CrossfadeWrapper;

public class MainActivity extends AppCompatActivity{

    private Drawer drawerResult = null;
    private AccountHeader accountHeader = null;
    private MiniDrawer miniDrawer = null;
    private Crossfader crossfader;
    private FragmentManager fragmentManager;
    public Fragment fragment;
    private Snackbar snackbar;
    private CoordinatorLayout coordinatorLayout;
    APIInterface apiInterface;
    private String Name, EmailID;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String ID = (String)bundle.get("ID");
        checks(bundle, ID);
        setContentView(R.layout.main_activity);
        fragmentManager = getSupportFragmentManager();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        final IProfile profile = new ProfileDrawerItem()
                .withName(Name)
                .withEmail(EmailID);

        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        profile
                )
                .withSelectionListEnabledForSingleProfile(false)
                .withSavedInstance(savedInstanceState)
                .build();

        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Time Table").withIcon(GoogleMaterial.Icon.gmd_monetization_on).withIdentifier(1)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1){
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.crossfade_content, new FragmentTransaction())
                                    .addToBackStack(null)
                                    .commitAllowingStateLoss();

                        }
                        else if (drawerItem.getIdentifier() == 4){

                        }
                        return false;
                    }
                })
                .withGenerateMiniDrawer(true)
                .withSavedInstance(savedInstanceState)
                .buildView();

        miniDrawer = drawerResult.getMiniDrawer();

        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

        crossfader = new Crossfader()
                .withContent(findViewById(R.id.crossfade_content))
                .withFirst(drawerResult.getSlider(), firstWidth)
                .withSecond(miniDrawer.build(this), secondWidth)
                .withSavedInstance(savedInstanceState)
                .build();

        miniDrawer.withCrossFader(new CrossfadeWrapper(crossfader));
        crossfader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);
    }

    private void checks(Bundle bundle, String ID) {
        if (!(bundle==null)){
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<User> call = apiInterface.getUser(ID);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        User resource = response.body();
                        List<User.DataEntity> datumList = resource.getData();
                        for( User.DataEntity datum : datumList){
                            Name = datum.getFirstname();
                            EmailID = datum.getEmailid();
                        }


                        /*Name = resource.getName();
                        EmailID = resource.getEmail();*/
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
        else{
            Intent intent1 = new Intent(this, LoginActivity.class);
            startActivity(intent1);
            finish();
        }
    }
}
