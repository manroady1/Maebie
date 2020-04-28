package com.manik.maebie;


import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerview;
    private CategoryAdapter categoryAdapter;

    ///////////////////todo:Strip Ad Layout
    private ImageView stripAdimage;
    private ConstraintLayout stripAdcontainer;

    ///////////////////todo:Strip Ad Layout

    ///////////////////todo:Horizontal Product Layout
    private TextView horizontallayoutTitle;
    private Button horizontalviewAllbtn;
    private RecyclerView horizontalRecyclerview;

    ///////////////////todo:Horizontal Product Layout



    ///////////////////todo:Banner slider
    private ViewPager bannersliderviewpager;
    private List<SliderModel> sliderModelList;
    private int currentpage = 2;
    private Timer timer;
    final private long Delay_Timer = 3000;
    final private long Period_timer = 3000;

    //////////////////todo:Banner Slider


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecyclerview =view.findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerview.setLayoutManager(layoutManager);

        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Appliances"));
        categoryModelList.add(new CategoryModel("link","furniture"));
        categoryModelList.add(new CategoryModel("link","Men"));
        categoryModelList.add(new CategoryModel("link","Women"));
        categoryModelList.add(new CategoryModel("link","Toys"));
        categoryModelList.add(new CategoryModel("link","Sports"));
        categoryModelList.add(new CategoryModel("link","Art"));
        categoryModelList.add(new CategoryModel("link","Groceries"));
        categoryModelList.add(new CategoryModel("link","Books"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerview.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        ///////////////////todo:Banner slider
        bannersliderviewpager = view.findViewById(R.id.banner_slider_viewpager);

        sliderModelList = new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.drawable.error_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.show_password_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.email,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.account,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.clikkart_logo,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.profile_placeholder,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_home_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.error_icon,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.show_password_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.email,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.account,"#077AE4"));

        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannersliderviewpager.setAdapter(sliderAdapter);
        bannersliderviewpager.setClipToPadding(false);
        bannersliderviewpager.setPageMargin(20);

        bannersliderviewpager.setCurrentItem(currentpage);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int il) {

            }

            @Override
            public void onPageSelected(int i) {
            currentpage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            if(i == ViewPager.SCROLL_STATE_IDLE){
                pageLooper();
            }
            }
        };

        bannersliderviewpager.addOnPageChangeListener(onPageChangeListener);

        startbannerslideshow();

        ///todo jab person slide ko touch krain to slid rukk jaye otherwise phir sa continue ho jaye
        bannersliderviewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopbannerslide();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    startbannerslideshow();
                }
                return false;
            }
        });
        ///todo jab person slide ko touch krain to slid rukk jaye otherwise phir sa continue ho jaye

///////////////////todo:Banner slider


        ///////////////////todo:Strip Ad Layout
        stripAdimage =view.findViewById(R.id.strip_ad_image);
        stripAdcontainer = view.findViewById(R.id.strip_ad_container);

        stripAdimage.setImageResource(R.drawable.stripad);
        stripAdcontainer.setBackgroundColor(Color.parseColor("#000000"));
        ///////////////////todo:Strip Ad Layout

        ///todo Horizontal Product Layout
        horizontallayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalviewAllbtn = view.findViewById(R.id.horizontal_Scrollviewall_button);
        horizontalRecyclerview = view.findViewById(R.id.horizontal_scrolllayout_recyclerview);

        List<HorizontalProductScroll_Model>horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScroll_Model(R.drawable.phone,"Redmi","dua processor","₹599"));
        horizontalProductScrollModelList.add(new HorizontalProductScroll_Model(R.drawable.ic_card_giftcard_black_24dp,"Redmi","dua processor","₹599"));
        horizontalProductScrollModelList.add(new HorizontalProductScroll_Model(R.drawable.account,"Redmi","dua processor","₹599"));
        horizontalProductScrollModelList.add(new HorizontalProductScroll_Model(R.drawable.ic_add_circle_black_24dp,"Redmi","dua processor","₹599"));
        horizontalProductScrollModelList.add(new HorizontalProductScroll_Model(R.drawable.ic_favorite_black_24dp,"Redmi","dua processor","₹599"));
        horizontalProductScrollModelList.add(new HorizontalProductScroll_Model(R.drawable.ic_menu_camera,"Redmi","dua processor","₹599"));
        horizontalProductScrollModelList.add(new HorizontalProductScroll_Model(R.drawable.ic_home_black_24dp,"Redmi","dua processor","₹599"));
        horizontalProductScrollModelList.add(new HorizontalProductScroll_Model(R.drawable.ic_menu_gallery,"Redmi","dua processor","₹599"));

        Horizontal_ProductScroll_Adapter horizontal_productScroll_adapter = new Horizontal_ProductScroll_Adapter(horizontalProductScrollModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) ;
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerview.setLayoutManager(linearLayoutManager);

        horizontalRecyclerview.setAdapter(horizontal_productScroll_adapter);
        horizontal_productScroll_adapter.notifyDataSetChanged();

        ///todo Horizontal Product Layout

        ///todo Grid Product Layout
        TextView gridLayoutTitle = view.findViewById(R.id.grid_product_layout_title);
        Button gridLayoutViewAllbtn = view.findViewById(R.id.grid_product_layout_viewbtn);
        GridView gridView = view.findViewById(R.id.grid_product_layout_gridview);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));


        ///todo Grid Product Layout

        return view;
    }

    ///////////////////todo:Banner slider
        private void pageLooper(){
        if(currentpage == sliderModelList.size() - 2){
            currentpage = 2;
            bannersliderviewpager.setCurrentItem(currentpage,false);
        }
            if(currentpage == 1){
                currentpage = sliderModelList.size() - 3;
                bannersliderviewpager.setCurrentItem(currentpage,false);
            }
    }

        private void startbannerslideshow(){
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if(currentpage >= sliderModelList.size()){
                        currentpage = 1;

                    }
                 bannersliderviewpager.setCurrentItem(currentpage++,true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            },Delay_Timer,Period_timer);
        }

        private void stopbannerslide(){
        timer.cancel();
        }

    ///////////////////todo:Banner slider
}
