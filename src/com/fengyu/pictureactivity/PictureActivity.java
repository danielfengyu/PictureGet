package com.fengyu.pictureactivity;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.TabActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class PictureActivity extends TabActivity {
	private TabHost tabhost;
    private final String IMAGE_TYPE = "image/*";

    private final int IMAGE_CODE = 0;   //�����IMAGE_CODE���Լ����ⶨ���
    
    private ImageButton addPic=null,showPicPath=null;
    
    private ImageView imgShow=null;
    
    private TextView imgPath=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //��TabActivity�����ȡ����Tab��TabHost
        tabhost = getTabHost();

        
        TabHost.TabSpec tab1 = tabhost.newTabSpec("one");
        tab1.setIndicator("ͼƬ");
        tab1.setContent(R.id.widget_layout_red);
        
        
        TabHost.TabSpec tab2 = tabhost.newTabSpec("two");
        tab2.setIndicator("��Ƶ");
        tab2.setContent(R.id.widget_layout_yellow);
        
        tabhost.addTab(tab1);
        tabhost.addTab(tab2);
       
    }

    private void init() {
        // TODO Auto-generated method stub
        
        addPic=(ImageButton) findViewById(R.id.btnClose);
        showPicPath=(ImageButton) findViewById(R.id.btnSend);
        imgPath=(TextView) findViewById(R.id.img_path);
        imgShow=(ImageView) findViewById(R.id.imgShow);
        
        addPic.setOnClickListener(listener);
        
        showPicPath.setOnClickListener(listener);
        
    }
private OnClickListener listener=new OnClickListener(){

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        
        
        ImageButton btn=(ImageButton) v; 
        
        switch(btn.getId()){
        
        case R.id.btnClose:
            setImage();
            break;
            
    case R.id.btnSend:
        
            break;
        }
        
    }

    

    private void setImage() {
        // TODO Auto-generated method stub
         //ʹ��intent����ϵͳ�ṩ����Ṧ�ܣ�ʹ��startActivityForResult��Ϊ�˻�ȡ�û�ѡ���ͼƬ

         

        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);

        getAlbum.setType(IMAGE_TYPE);

        startActivityForResult(getAlbum, IMAGE_CODE);
        
        
    }};
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.picture, menu);
        return true;
    }
    
     protected void onActivityResult(int requestCode, int resultCode, Intent data){

            if (resultCode != RESULT_OK) {        //�˴��� RESULT_OK ��ϵͳ�Զ����һ������

                Log.e("TAG->onresult","ActivityResult resultCode error");

                return;

            }

         

            Bitmap bm = null;

         

            //���ĳ������ContentProvider���ṩ���� ����ͨ��ContentResolver�ӿ�

            ContentResolver resolver = getContentResolver();

         

            //�˴��������жϽ��յ�Activity�ǲ�������Ҫ���Ǹ�

            if (requestCode == IMAGE_CODE) {

                try {

                    Uri originalUri = data.getData();        //���ͼƬ��uri 

         

                    bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        
                    //�Եõ�bitmapͼƬ
                    imgShow.setImageBitmap(bm);
                    

//    ���￪ʼ�ĵڶ����֣���ȡͼƬ��·����

         

                    String[] proj = {MediaStore.Images.Media.DATA};

         

                    //������android��ý�����ݿ�ķ�װ�ӿڣ�����Ŀ�Android�ĵ�

                    Cursor cursor = managedQuery(originalUri, proj, null, null, null); 

                    //���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ

                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    //�����������ͷ ���������Ҫ����С�ĺ���������Խ��

                    cursor.moveToFirst();

                    //����������ֵ��ȡͼƬ·��

                    String path = cursor.getString(column_index);
                    //System.out.println(path);
                    imgPath.setText(path);
                }catch (IOException e) {

                    Log.e("TAG-->Error",e.toString()); 

                }

            }

        }
}