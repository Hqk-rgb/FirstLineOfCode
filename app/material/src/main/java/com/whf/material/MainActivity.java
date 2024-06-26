package com.whf.material;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private Fruit[] fruits = {
            new Fruit("Apple", R.drawable.apple),
            new Fruit("Banana", R.drawable.banana),
            new Fruit("Orange", R.drawable.orange),
            new Fruit("Watermelon", R.drawable.watermelon),
            new Fruit("Pear", R.drawable.pear),
            new Fruit("Grape", R.drawable.grape),
            new Fruit("Pineapple", R.drawable.pineapple),
            new Fruit("Strawberry", R.drawable.strawberry),
            new Fruit("Cherry", R.drawable.cherry),
            new Fruit("Mango", R.drawable.mango)
    };

    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Material");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);  //让导航按钮显示；在Toolbar最左侧的按钮叫作HomeAsUp按钮，它默认的图标是一个返回的箭头，意为返回上一个活动；这里我们对它默认的样式和作用都进行了修改；
            actionBar.setHomeAsUpIndicator(R.drawable.menu);  //设置导航按钮图标
        }

        navView.setCheckedItem(R.id.nav_call);  //将Call菜单设置为默认选中
        navView.setNavigationItemSelectedListener(menuItem -> {
            Toast.makeText(MainActivity.this, "You clicked the menuItem: " + menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
            mDrawerLayout.closeDrawers();  //点击菜单项时将滑动菜单关闭
            return true;
        });

        fab.setOnClickListener(v -> Snackbar.make(v, "数据删除！", Snackbar.LENGTH_SHORT)
                .setAction("撤回", v1 -> Toast.makeText(MainActivity.this, "撤回成功！", Toast.LENGTH_SHORT).show()).show());

        //加载水果数据及布局
        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);  //第二个参数spanCount：表示一行显示2列
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        //实现RecyclerView下拉刷新逻辑
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);  //设置下拉刷新进度条颜色
        //设置下拉监听器
        swipeRefreshLayout.setOnRefreshListener(this::refreshFruits);

    }

    /**
     * 加载toolbar.xml中菜单项
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        } else if (itemId == R.id.backup) {
            Toast.makeText(this, "You clicked the menu of Backup.", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.delete) {
            Toast.makeText(this, "You clicked the menu of Delete.", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.settings) {
            Toast.makeText(this, "You clicked the menu of Settings.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    /**
     * 初始化要加载的水果数据（将水果数组fruits中的数据随机取出并放到List中）
     */
    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    /**
     * 下拉RecyclerView刷新水果列表
     */
    private void refreshFruits() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //切换回主线程，更新UI
            runOnUiThread(() -> {
                initFruits();
                adapter.notifyDataSetChanged();  //通知数据发生了变化
                swipeRefreshLayout.setRefreshing(false);  //false-表示刷新事件结束，并隐藏刷新进度条
            });
        }).start();
    }
}