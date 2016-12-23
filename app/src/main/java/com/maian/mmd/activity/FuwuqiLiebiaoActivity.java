package com.maian.mmd.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.maian.mmd.R;
import com.maian.mmd.adapter.ServiceListViewAdapter;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.PersonService;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.HDbManager;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class FuwuqiLiebiaoActivity extends BaseActivity {
    private PopupWindow popupWindow;
    private ImageView imageViewMore;
    private EditText editText_service_name;
    private EditText editText_service_adress;
    private EditText editText_service_duankou;
    private EditText editText_service_dirk;
    private TextView textView_service_url;
    private List<PersonService> list_service;
    private ServiceListViewAdapter adapter;
    private AdapterView.AdapterContextMenuInfo lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuwuqi_liebiao);
        getData();
        initPopUpWindow();
        initHead();
        initListService();
    }

    private void addEditTextListener(EditText editext) {
        editext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String serviceAdress = editText_service_adress.getText().toString();
                String serviceDuankou = editText_service_duankou.getText().toString();
                String url = "http://"+serviceAdress+":"+serviceDuankou+"/"+editText_service_dirk.getText().toString();
                textView_service_url.setText(url);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getData() {
        list_service = new ArrayList<>();
        list_service.add(new PersonService("Demo","","", "http://gzmian.com:5465/mmd/vision/RMIServlet"));
        //list_service.add(new PersonService("81","192.168.0.13","81", "http://192.168.0.13:81/mmd/vision/RMIServlet"));
        DbManager db = x.getDb(HDbManager.getServiceDB());
        try {
            List<PersonService> list_db = db.selector(PersonService.class).findAll();
            list_service.addAll(list_db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void initListService() {
        final ListView listView_service = (ListView) findViewById(R.id.listView_service);
        adapter = new ServiceListViewAdapter(list_service);
        listView_service.setAdapter(adapter);
        registerForContextMenu(listView_service);//给listview注册上下文菜单
        listView_service.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact.serviceUrl = list_service.get(position).url;
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initPopUpWindow() {
        View popupWindow_service = View.inflate(getBaseContext(), R.layout.popupwindow_service, null);
        popupWindow = new PopupWindow(popupWindow_service);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popwindow_anim_style); // 设置动画
        //事件处理
        editText_service_name = (EditText) popupWindow_service.findViewById(R.id.editText_service_name);
        editText_service_adress = (EditText) popupWindow_service.findViewById(R.id.editText_service_adress);
        editText_service_duankou = (EditText) popupWindow_service.findViewById(R.id.editText_service_duankou);
        editText_service_dirk = (EditText) popupWindow_service.findViewById(R.id.editText_service_dirk);
        textView_service_url = (TextView) popupWindow_service.findViewById(R.id.textView_service_url);
        //改变textView的值
        addEditTextListener(editText_service_adress);
        addEditTextListener(editText_service_duankou);
        Button button_clear = (Button) popupWindow_service.findViewById(R.id.button_clear);
        Button button_submit = (Button) popupWindow_service.findViewById(R.id.button_submit);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_service_name.setText("");
                editText_service_adress.setText("");
                editText_service_duankou.setText("");
            }
        });
        //提交
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = editText_service_name.getText().toString().trim();
                String serviceAdress = editText_service_adress.getText().toString().trim();
                String serviceDuankou = editText_service_duankou.getText().toString().trim();
                if (serviceName != null && !"".equals(serviceName)
                        && serviceAdress != null && !" ".equals(serviceAdress)
                        && serviceDuankou != null && !" ".equals(serviceDuankou) ) {
                    popupWindow.dismiss();
                    judgeNameSame(serviceName);
                    String url = "http://"+serviceAdress+":"+serviceDuankou
                            +"/"+editText_service_dirk.getText().toString()
                            +"/vision/RMIServlet";
                    PersonService service = new PersonService(serviceName,serviceAdress,serviceDuankou, url);
                    insertDB(service);
                    list_service.add(service);
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(FuwuqiLiebiaoActivity.this, "请填写完整后提交", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initHead() {
        imageViewMore = (ImageView) findViewById(R.id.img_more);
        imageViewMore.setClickable(true);
        imageViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, WindowManager.LayoutParams.MATCH_PARENT, 200);
            }
        });


        TextView textView_back = (TextView) findViewById(R.id.textView_back);
        textView_back.setVisibility(View.GONE);
        for (int i = 0; i < MMDApplication.activityManagers.size() ; i++) {
            if (MMDApplication.activityManagers.get(i) instanceof WorkeActivity){
                textView_back.setVisibility(View.VISIBLE);
            }
        }


        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WorkeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // 加载xml中的上下文菜单
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.caidan, menu);
        lm = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //System.out.println("----第" + lm.id + "项,弹出菜单注册成功");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (lm.position == 0 ){
            Toast.makeText(this, "默认服务器无法操作", Toast.LENGTH_SHORT).show();
        }else{
            switch (item.getItemId()) {
                case R.id.edit:
                    Toast.makeText(this, "编辑操作", Toast.LENGTH_SHORT).show();
                    //popupWindow.showAtLocation(imageViewMore,No);
                    popupWindow.showAtLocation(imageViewMore, Gravity.NO_GRAVITY, WindowManager.LayoutParams.MATCH_PARENT, 200);
                    editText_service_name.setText(list_service.get(lm.position).serviceName);
                    editText_service_duankou.setText(list_service.get(lm.position).duankou);
                    editText_service_adress.setText(list_service.get(lm.position).serviceAddress);
                    addEditTextListener(editText_service_adress);
                    addEditTextListener(editText_service_duankou);
                    break;
                case R.id.delete:
                    delectDB(list_service.get(lm.position));
                    list_service.remove(lm.position);
                    System.out.println("------"+list_service.size());
                    adapter.notifyDataSetChanged();

                    Toast.makeText(this, "删除操作", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        return super.onContextItemSelected(item);
    }

    private void insertDB(PersonService service) {
        DbManager db = x.getDb(HDbManager.getServiceDB());
        try {
            db.save(service);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void delectDB(PersonService service){
        DbManager db = x.getDb(HDbManager.getServiceDB());
        try {
            db.delete(service);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    private void judgeNameSame(String name){
        for (int i = 0; i < list_service.size(); i++) {
            if (name.equals(list_service.get(i).serviceName)){
                delectDB(list_service.get(i));
                list_service.remove(i);

            }
        }
    }

}
