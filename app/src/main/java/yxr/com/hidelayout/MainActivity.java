package yxr.com.hidelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yxr.com.hidelibrary.HideLayout;

public class MainActivity extends AppCompatActivity {

    private HideLayout hlLayout;
    private ListView lvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        hlLayout = (HideLayout) findViewById(R.id.hlLayout);
        lvContent = (ListView) findViewById(R.id.lvContent);
    }

    private void initData() {
        initHLLayout();
        initListViewData();
    }

    private void initHLLayout() {
        ImageView mView = new ImageView(this);

        // 设置在AbsListView 或 ScrollView 滚动时可以隐藏的控件
        // 如果不设置控件的话将使用默认控件，一个ImageView
        hlLayout.setFloatingView(mView);

        // 设置该控件的Margin，left,top,right,bottom
        hlLayout.setFloatingMargin(dip2px(25),dip2px(25),dip2px(25),dip2px(25));

        // 设置该控件的宽高
        hlLayout.setFloatingWidthHeight(dip2px(50),dip2px(50));

        // 如果该控件为ImageView时，该方法才会起作用
        hlLayout.setFloatingRes(R.mipmap.cancel);

        // 设置该控件的点击事件
        hlLayout.setFloatingClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"点击了. . ",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initListViewData() {
        List<String> contents = new ArrayList<>();
        for (int i = 0 ;i < 100 ; i++)
            contents.add("item : " + i);
        lvContent.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contents));
    }
    public int dip2px(int dip){
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip*density+0.5);
    }
}
