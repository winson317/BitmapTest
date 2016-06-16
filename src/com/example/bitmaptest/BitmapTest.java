package com.example.bitmaptest;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class BitmapTest extends Activity {
	
	String[] images = null;
	AssetManager assets = null;
	int currentImg = 0;
	ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        imageView = (ImageView)findViewById(R.id.imageView);
        try {
        	assets = getAssets();
			images = assets.list("");  //��ȡ/assets/Ŀ¼�µ������ļ�
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
        final Button next = (Button)findViewById(R.id.next);  //��ȡbutton��ť
        //Ϊbutton���¼����������ü���������鿴��һ��ͼƬ
        next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View sources) {
				// �����������Խ��
				if (currentImg >= images.length)
				{
					currentImg = 0;
				}
				
				//�ҵ���һ��ͼƬ�ļ�
				while (!images[currentImg].endsWith(".png") 
						&& !images[currentImg].endsWith(".jpg") 
						&& !images[currentImg].endsWith(".gif"));
				{
					currentImg ++;
					//����ѷ�������Խ��
					if (currentImg >= images.length)
					{
						currentImg = 0;
					}
				}	
				InputStream assetFile = null;
				try {
					assetFile = assets.open(images[currentImg++]); //��ָ����Դ��Ӧ��������
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
				//���ͼƬ��δ���գ���ǿ�ƻ��ո�ͼƬ
				if (bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled())
				{
					bitmapDrawable.getBitmap().recycle();
				}
				
				imageView.setImageBitmap(BitmapFactory.decodeStream(assetFile)); //�ı�ImageView��ʾ��ͼƬ
			}
		});
        
       
    }

}
