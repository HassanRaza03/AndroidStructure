//package edu.aku.adapters.listadapters;
//
//import android.app.Activity;
//import android.support.annotation.NonNull;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.nostra13.universalimageloader.core.ImageLoader;
//import edu.aku.R;
//import edu.aku.libraries.imageloader.ImageLoaderHelper;
//import edu.aku.models.Category;
//
//import java.util.ArrayList;
//
///**
// * Created by khanhamza on 23-Feb-17.
// */
//
//public class SessionAdapter extends ArrayAdapter<Category> {
//    public ArrayList<Category> arrData = new ArrayList<>();
//
//    public SessionAdapter(Activity context, ArrayList<Category> arrCategory) {
//        super(context, 0, arrCategory);
//        arrData.addAll(arrCategory);
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 1;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View gridItemView = convertView;
//
//        final ViewHolder viewHolder;
//        Category currentItem = getItem(position);
//
//        if (gridItemView == null) {
//
//            gridItemView = LayoutInflater.from(getContext()).inflate(
//                    R.layout.item_home, parent, false);
//            viewHolder = new ViewHolder();
//
//            bindViews(gridItemView, viewHolder);
//
//            viewHolder.layoutItemHome.setTag(position);
//            gridItemView.setTag(viewHolder);
//
//        } else {
//            // View is being recycled, retrieve the viewHolder object from tag
//            viewHolder = (ViewHolder) gridItemView.getTag();
//        }
//
//        setViews(viewHolder, currentItem);
//        return gridItemView;
//    }
//
//    private void setViews(ViewHolder viewHolder, Category currentItem) {
//        viewHolder.txtViewHeading.setText(currentItem.categoryName);
//        viewHolder.txtViewSubHeading.setText(currentItem.subtitle);
//
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        ImageView iconView = viewHolder.imgViewGridItem;
//        imageLoader.displayImage(currentItem.image, iconView, ImageLoaderHelper.options);
//    }
//
//    private void bindViews(View gridItemView, ViewHolder viewHolder) {
//        viewHolder.txtViewHeading = (TextView) gridItemView.findViewById(R.id.txtItemHeading);
//        viewHolder.txtViewSubHeading = (TextView) gridItemView.findViewById(R.id.txtItemSubHeading);
//        viewHolder.imgViewGridItem = (ImageView) gridItemView.findViewById(R.id.imgItem);
//        viewHolder.layoutItemHome = (RelativeLayout) gridItemView.findViewById(R.id.layoutItem);
//    }
//
//
//    static class ViewHolder {
//        TextView txtViewHeading;
//        TextView txtViewSubHeading;
//        ImageView imgViewGridItem;
//        RelativeLayout layoutItemHome;
//    }
//
//
//}
