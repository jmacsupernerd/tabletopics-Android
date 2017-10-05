package com.mcwilliams.TableTopicsApp.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mcwilliams.TableTopicsApp.FragmentAdapter;
import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.TableTopicsApplication;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.arrayadapters.TopicsRvAdapter;
import com.mcwilliams.TableTopicsApp.databinding.DialogImportTopicsBinding;
import com.mcwilliams.TableTopicsApp.databinding.LvRowCategoryBinding;
import com.mcwilliams.TableTopicsApp.fragments.HomeFragment;
import com.mcwilliams.TableTopicsApp.fragments.People;
import com.mcwilliams.TableTopicsApp.fragments.Topics;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.mcwilliams.TableTopicsApp.model.response.CategoryResponse;
import com.mcwilliams.TableTopicsApp.model.response.CategoryTopicList;
import com.mcwilliams.TableTopicsApp.model.response.TopicsByCategory;
import com.mcwilliams.TableTopicsApp.utils.bottomnav.MyBottomNavigationView;
import com.mcwilliams.TableTopicsApp.utils.network.TopicServices;
import com.squareup.seismic.ShakeDetector;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
public class Main extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = Main.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind(R.id.bottom_nav)
    MyBottomNavigationView bottomNavigationView;
    @Bind(R.id.bottom_nav_native)
    BottomNavigationView bottomNavigationViewNative;
    private boolean isNativeVisible = true;
    TopicServices topicServices;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        ButterKnife.bind(this);
        topicServices = TableTopicsApplication.retrofit.create(TopicServices.class);
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(new MyBottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_my_usaa:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_paybills:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_transfer:
                        viewPager.setCurrentItem(2);
                        break;
                    case 200:
                        viewPager.setCurrentItem(1);
                        break;
                    case 300:
                        viewPager.setCurrentItem(0);
                        break;
                }
                return true;
            }
        });

        bottomNavigationViewNative.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_my_usaa:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_paybills:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_transfer:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.action_go_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_transfer_account:
                        viewPager.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new Topics(), "Topics");
        adapter.addFragment(new People(), "People");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) {
//            fab.setVisibility(View.GONE);
//            adView.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
//            adView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @OnClick(R.id.fab)
    public void fabClicked() {
        if (isNativeVisible) {
            bottomNavigationViewNative.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.VISIBLE);
            isNativeVisible = false;
        } else {
            bottomNavigationViewNative.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.GONE);
            isNativeVisible = true;
        }
//        showDialog(viewPager.getAdapter().getPageTitle(viewPager.getCurrentItem()));
    }

    public Activity getActivity() {
        return this;
    }

    public void showDialog(CharSequence pageTitle) {
        Log.d("", (String) pageTitle);
        String title = (String) pageTitle;
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        if (title.equals("Home")) {

        } else if (title.equals("Topics")) {
            alert.setTitle("Add Topic");
            View inputView = getLayoutInflater().inflate(R.layout.input_dialog, null);
            final EditText getInput = (EditText) inputView.findViewById(R.id.inputText);
            getInput.requestFocus();
            alert.setView(inputView);
            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TableTopicsApplication.db.addTopic(new Topic(getInput.getText().toString()));
                    Topics.reloadData();
                }
            });
            alert.setNeutralButton("Predefined Topics", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {
                    Call<CategoryResponse> categoriesCall = topicServices.getCategories();
                    categoriesCall.enqueue(new Callback<CategoryResponse>() {
                        @Override
                        public void onResponse(Response<CategoryResponse> response) {
                            Log.d("", String.valueOf(response.body().getData().size()));
                            showPredefinedTopicDialog(response.body().getData());
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d("", "Error");
                        }
                    });
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        } else {
            alert.setTitle("Add Person");
            View inputView = getLayoutInflater().inflate(R.layout.input_dialog, null);
            final EditText getInput = (EditText) inputView.findViewById(R.id.inputText);
            getInput.requestFocus();
            alert.setView(inputView);
            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TableTopicsApplication.db.addMember(new Member(getInput.getText().toString()));
                    People.reloadData();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        alert.show();
    }

    public void showPredefinedTopicDialog(List<CategoryResponse.Category> categoriesList) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Select a category");

        final List<String> categoryList = new ArrayList<>();

        for (CategoryResponse.Category category : categoriesList) {
            categoryList.add(category.getCategoryName());
        }
        String[] categoryArr = categoryList.toArray(new String[categoryList.size()]);

        alert.setSingleChoiceItems(categoryArr, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadTopicsFromCategories(categoryList.get(which));
            }
        });
        dialog = alert.create();
        dialog.show();
    }

    public void loadTopicsFromCategories(String category) {
        Call<CategoryTopicList> getTopics = topicServices.getTopicsByCategory(category);
        getTopics.enqueue(new Callback<CategoryTopicList>() {
            @Override
            public void onResponse(Response<CategoryTopicList> response) {
                dialog.dismiss();
                onCategoryClicked(response.body().getData());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void onCategoryClicked(List<CategoryTopicList.Topic> topics) {
        final List<Topic> topicList = new ArrayList<>();

        for (CategoryTopicList.Topic topic : topics) {
            Topic newTopic = new Topic(topic.getTopic());
            topicList.add(newTopic);
        }

        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        DialogImportTopicsBinding dialogBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_import_topics, null, false);

        dialogBinding.tbToolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        dialogBinding.tbToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogBinding.tbToolbar.inflateMenu(R.menu.menu);
        dialogBinding.tbToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                for (Topic topic : topicList) {
                    TableTopicsApplication.db.addTopic(new Topic(topic.get_topic()));
                }
                Topics.reloadData();
                dialog.dismiss();
                return true;
            }
        });

        dialogBinding.rvTopics.setHasFixedSize(true);
        dialogBinding.rvTopics.setLayoutManager(new LinearLayoutManager(this));
        dialogBinding.rvTopics.setAdapter(new TopicsRvAdapter(topicList));

        dialog.setContentView(dialogBinding.getRoot());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();

    }
}
