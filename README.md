# HideFloationgLayout
这是个快速实现在AbsListView或ScrollView滚动时隐藏悬浮在在AbsListView或ScrollView之上的控件的项目
当我们使用AbsListView或ScrollView时，有时会有操作按钮悬浮在AbsListView或ScrollView之上，为了增强一些交互性，
有时候我们会有在AbsListView或ScrollView滚动时隐藏悬浮在AbsListView或ScrollView上面的操作按钮，那么这就是这个
项目的存在意义。

# 首先我们来看看效果图，注意右下角操作按钮
![Screenshot](https://github.com/15018777629/HideFloationgLayout/blob/master/screenshots/demo.gif)

# 怎么使用
```java
 //首先是布局文件，将AbsListView或ScrollView放到yxr.com.hidelibrary.HideLayout下就OK啦
 //app:position="right_bottom"这个属性是用来设置悬浮的操作按钮的位置
 //共八个位置(left,left_top,top,right_top,right,right_bottom,bottom,left_bottom)
 <yxr.com.hidelibrary.HideLayout
        android:id="@+id/hlLayout"
        android:layout_width="match_parent"
        app:position="right_bottom"
        android:layout_height="match_parent">
        <ListView
            android:id="@+id/lvContent"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
    </yxr.com.hidelibrary.HideLayout>

    
    //接下来就是代码部分了，可以将自定义的按钮设置进去，也可以使用默认的
    //具体包含的方法如下
    
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
