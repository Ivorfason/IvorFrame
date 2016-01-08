package com.ivor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivor.model.Person;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class IvorLogin extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "bmob";

    private EditText mUserET;
    private EditText mPassWordET;
    private Button mRegisterBtn;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ivor_login);

        initView();
        initListener();
        Bmob.initialize(this, "658b562c77f6a37b487fa05e6e40b525");
    }

    private void initView() {
        this.mUserET = (EditText) findViewById(R.id.bmob_user_et);
        this.mPassWordET = (EditText) findViewById(R.id.bmob_password_et);
        this.mRegisterBtn = (Button) findViewById(R.id.bmob_register_btn);
        this.mLoginBtn = (Button) findViewById(R.id.bmob_login_btn);
    }

    private void initListener() {
        this.mRegisterBtn.setOnClickListener(this);
        this.mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bmob_register_btn:
                addData();
                break;
            case R.id.bmob_login_btn:
                queryData();
                break;
        }
    }

    private void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }

    // 增（注册）
    private void addData() {
        final Person p = new Person();
        BmobQuery<Person> query = new BmobQuery<Person>();
        query.addWhereEqualTo("user", mUserET.getText().toString());
        query.findObjects(this, new FindListener<Person>() {
            @Override
            public void onSuccess(List<Person> object) {
                // TODO Auto-generated method stub
                if (object.size() != 0) {
                    toast("该用户已存在！");
                } else {
                    if(mUserET.equals("") || mPassWordET.equals("")) {
                        toast("请不要输入空的用户名或密码！");
                    } else {
                        p.setUser(mUserET.getText().toString());
                        p.setPassword(mPassWordET.getText().toString());
                        p.save(getApplicationContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                toast("注册成功！");
                                mUserET.setText("");
                                mPassWordET.setText("");
                            }
                            @Override
                            public void onFailure(int code, String msg) {
                                // TODO Auto-generated method stub
                                toast("注册失败！" + msg);
                            }
                        });
                    }
                }
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                p.setUser(mUserET.getText().toString());
                p.setPassword(mPassWordET.getText().toString());
                p.save(getApplicationContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        toast("注册成功！");
                        mUserET.setText("");
                        mPassWordET.setText("");
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        // TODO Auto-generated method stub
                        toast("注册失败！" + msg);
                    }
                });
            }
        });
    }

    // 登录查询
    private void queryData() {
        BmobQuery<Person> query = new BmobQuery<Person>();
        query.addWhereEqualTo("user", mUserET.getText().toString());
        query.findObjects(this, new FindListener<Person>() {
            @Override
            public void onSuccess(List<Person> object) {
                // TODO Auto-generated method stub
                for (Person p : object) {
                    //获得Password的信息
                    if(p.getPassword().equals(mPassWordET.getText().toString())) {
                        toast("登录成功");
                        Intent i = new Intent(IvorLogin.this, IvorMain.class);
                        i.putExtra("userName", p.getUser());
                        startActivity(i);
                        IvorLogin.this.finish();
                    } else {
                        toast("登录失败");
                    }
                    //获得数据的objectId信息
                    p.getObjectId();
                    //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                    p.getCreatedAt();
                }
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                toast("登录失败："+ msg);
            }
        });
    }

    // 修改更新
    private void updateData(String objectId) {
        final Person p = new Person();
        p.setUser("123");
        p.update(this, objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                toast("更新成功：" + p.getUpdatedAt());
            }
            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                toast("更新失败：" + msg);
            }
        });
    }

    // 删除
    private void deleteData(String objectId) {
        final Person p = new Person();
        p.setObjectId(objectId);
        p.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                toast("删除成功");
            }
            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                toast("删除失败：" + msg);
            }
        });
    }
}
