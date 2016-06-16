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
			images = assets.list("");  //获取/assets/目录下的所有文件
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
        final Button next = (Button)findViewById(R.id.next);  //获取button按钮
        //为button绑定事件监听器，该监听器将会查看下一张图片
        next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View sources) {
				// 如果发生数组越界
				if (currentImg >= images.length)
				{
					currentImg = 0;
				}
				
				//找到下一张图片文件
				while (!images[currentImg].endsWith(".png") 
						&& !images[currentImg].endsWith(".jpg") 
						&& !images[currentImg].endsWith(".gif"));
				{
					currentImg ++;
					//如果已发生数组越界
					if (currentImg >= images.length)
					{
						currentImg = 0;
					}
				}	
				InputStream assetFile = null;
				try {
					assetFile = assets.open(images[currentImg++]); //打开指定资源对应的输入流
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
				//如果图片还未回收，先强制回收该图片
				if (bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled())
				{
					bitmapDrawable.getBitmap().recycle();
				}
				
				imageView.setImageBitmap(BitmapFactory.decodeStream(assetFile)); //改变ImageView显示的图片
			}
		});
        
       
    }

}
