package com.sherwin.opengles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sherwin.opengles.databinding.HomeActivityBinding;
import com.sherwin.opengles.first.FirstActivity;
import com.sherwin.rapid.base.ui.annotation.ContentView;
import com.sherwin.rapid.base.util.ToastUtil;
import com.sherwin.rapid.databinding.ui.BaseDataBindingActivity;

import java.util.ArrayList;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/11/14.16:28
 * @desc
 */
@ContentView(layout = R.layout.home_activity)
public class HomeActivity extends BaseDataBindingActivity<HomeActivityBinding> {

    private ArrayList<MenuBean> data;

    @Override
    public void initView(Bundle savedInstanceState) {
        mDataBinding.mList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void initData() {

        data=new ArrayList<>();
        data.add(new MenuBean("第一个OpenGL+JNI",FirstActivity.class));
        data.add(new MenuBean("绘制形体",null));
        data.add(new MenuBean("图片处理",null));
        data.add(new MenuBean("图形变换",null));
        data.add(new MenuBean("相机",null));
//        add("绘制形体",FGLViewActivity.class);
//        add("图片处理",SGLViewActivity.class);
//        add("图形变换",VaryActivity.class);
//        add("相机",CameraActivity.class);
//        add("相机2 动画",Camera2Activity.class);
//        add("相机3 美颜",Camera3Activity.class);
//        add("压缩纹理动画",ZipActivity.class);
//        add("FBO使用",FBOActivity.class);
//        add("EGL后台处理",EGLBackEnvActivity.class);
//        add("3D obj模型",ObjLoadActivity.class);
//        add("obj+mtl模型",ObjLoadActivity2.class);
//        add("VR效果",VrContextActivity.class);
//        add("颜色混合",BlendActivity.class);
        mDataBinding.mList.setAdapter(new MenuAdapter());
    }
    private class MenuBean{
        String name;
        Class<?> clazz;
        public MenuBean(String name,Class<?> clazz){
            this.name = name;
            this.clazz = clazz;
        }
    }

    private class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder>{


        @Override
        public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MenuHolder(getLayoutInflater().inflate(R.layout.item_button,parent,false));
        }

        @Override
        public void onBindViewHolder(MenuHolder holder, int position) {
            holder.setPosition(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MenuHolder extends RecyclerView.ViewHolder{

            private Button mBtn;

            MenuHolder(View itemView) {
                super(itemView);
                mBtn= (Button)itemView.findViewById(R.id.mBtn);
                mBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position= (int)v.getTag();
                        MenuBean bean = data.get(position);
                        if (bean.clazz != null){
                            startActivity(new Intent(mActivity,bean.clazz));
                        } else{
                            ToastUtil.showToast("不存在该页面");
                        }
                    }
                });
            }

            public void setPosition(int position){
                MenuBean bean=data.get(position);
                mBtn.setText(bean.name);
                mBtn.setTag(position);
            }
        }

    }
}
