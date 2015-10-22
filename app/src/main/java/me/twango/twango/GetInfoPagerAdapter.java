package me.twango.twango;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by AVIATER on 13-Oct-15.
 */
public class GetInfoPagerAdapter extends PagerAdapter{
    private  int[] images ={R.drawable.image1,R.drawable.image2,R.drawable.image3};
    private  int[] helloStrings ={R.string.getStartedString1,R.string.getStartedString2,R.string.getStartedString3};
    private Context context;
    private LayoutInflater layoutInflater;
    public GetInfoPagerAdapter(Context context)
    {
        this.context = context;
    }
    @Override
    public int getCount(){
        return images.length;
    }
    @Override
    public boolean isViewFromObject(View view,Object o){
        return (view==(RelativeLayout)o);
    }
    @Override
    public Object instantiateItem(ViewGroup container,int position) {
        if (position==0){
            return loadBasicInfo(container);
        }else if (position==1){
            return loadBasicInfo(container);
        }else {
            return loadBasicInfo(container);
        }

    }

    private View loadBasicInfo(ViewGroup container){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipegetstarted,container,false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.image);
        TextView hello_tv = (TextView)item_view.findViewById(R.id.hello_tv);
        hello_tv.setText(helloStrings[0]);
        imageView.setImageResource(images[0]);
        container.addView(item_view);
        return item_view;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((RelativeLayout)object);

    }

}
