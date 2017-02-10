package com.jia.seven.view.favorite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jia.seven.R;
import com.jia.seven.network.parse.FavoriteList;
import com.jia.seven.utils.CommonHelper;
import com.jia.seven.view.common.SimpleRecyclerViewHolder;
import com.jia.seven.view.widget.GlideCircleTransformation;

import java.util.List;

/**
 * Created by wu on 2017/2/9.
 */
public class FavoriteCommonAdapter extends RecyclerView.Adapter<SimpleRecyclerViewHolder> {
    private static final String DATA_TYPE_NAME_TAG = "tag";
    private static final String DATA_TYPE_NAME_USER = "user";

    private static final String PARAM_FROM_MY = "my_follow"; //adapter的来源

    private static final int DATA_TYPE_CARD_TAG = 0;
    private static final int DATA_TYPE_CARD_USER = 1;

    private List<FavoriteList.Card> mData;
    private Context mContext;
    private LayoutInflater mInflater;
    private String mParamFrom = "";
    /****************************************/
    public FavoriteCommonAdapter(Context context,List<FavoriteList.Card> data,boolean fromMineFollow){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
        mParamFrom = fromMineFollow ? "my_follow" : "";
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).type == null) return DATA_TYPE_CARD_TAG; //ps.测试环境的数据有时是异常数据
        if(mData.get(position).type.equals(DATA_TYPE_NAME_TAG)){
            return DATA_TYPE_CARD_TAG;
        }
        return DATA_TYPE_CARD_USER;
    }

    @Override
    public SimpleRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SimpleRecyclerViewHolder holder = null;
        if(viewType == DATA_TYPE_CARD_TAG){
            holder = new SimpleRecyclerViewHolder(mContext,
                    mInflater.inflate(R.layout.item_favorite_tag_card, parent, false));
        }else{
            holder = new SimpleRecyclerViewHolder(mContext,
                    mInflater.inflate(R.layout.item_favorite_user_card, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(SimpleRecyclerViewHolder holder, int position) {
        int tempType = getItemViewType(position);
        FavoriteList.Card item = mData.get(position);
        if(tempType == DATA_TYPE_CARD_TAG){
            if(item.type == null){//PS.测试环境的数据有时是异常数据
                holder.getView(R.id.favorite_item_header).setTag("empty data");
                holder.getView(R.id.favorite_item_header).setOnClickListener(null);
                holder.setText(R.id.favorite_header_text1, "empty data");

                Spanned storyCount = Html.fromHtml(mContext.getString(R.string.favorite_user_story_count,
                        "<font color='#ffc300'>" + "0" + "</font>"));
                holder.setText(R.id.favorite_header_text2, storyCount);

                holder.getView(R.id.favorite_item_header).
                        setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.function_item_bg_selector));
                ((TextView) holder.getView(R.id.favorite_header_text1)).
                        setTextColor(mContext.getResources().getColor(R.color.color_333333));
                ((TextView) holder.getView(R.id.favorite_header_text2)).
                        setTextColor(mContext.getResources().getColor(R.color.color_737373));
                ((Button) holder.getView(R.id.favorite_header_button)).
                        setTextColor(mContext.getResources().getColor(R.color.color_694b37));
            }else{
                holder.getView(R.id.favorite_item_header).setTag(item.tag.name);
                holder.getView(R.id.favorite_item_header).setOnClickListener(null);
                holder.setText(R.id.favorite_header_text1, item.tag.name);

                Spanned storyCount = Html.fromHtml(mContext.getString(R.string.favorite_user_story_count,
                        "<font color='#ffc300'>" + (TextUtils.isDigitsOnly(item.tag.story_count) ? item.tag.story_count : "0") + "</font>"));
                holder.setText(R.id.favorite_header_text2, storyCount);

                holder.getView(R.id.favorite_item_header).
                        setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.function_item_bg_selector));
                ((TextView) holder.getView(R.id.favorite_header_text1)).
                        setTextColor(mContext.getResources().getColor(R.color.color_333333));
                ((TextView) holder.getView(R.id.favorite_header_text2)).
                        setTextColor(mContext.getResources().getColor(R.color.color_737373));
                ((Button) holder.getView(R.id.favorite_header_button)).
                        setTextColor(mContext.getResources().getColor(R.color.color_694b37));
            }

        }else{
            Glide.with(mContext)
                    .load(item.user.avatar)
                    .dontAnimate()
                    .crossFade()
                    .transform(new GlideCircleTransformation(mContext))
                    .placeholder(R.drawable.icon_avatar_middle)
                    .error(R.drawable.icon_avatar_middle)
                    .into(holder.getImageView(R.id.favorite_header_avatar));

            CommonHelper.changeWeiboVStatus(item.user.weibo_verified_type, holder.getImageView(R.id.story_item_weibo_v2));

            holder.getTextView(R.id.favorite_header_text1).setText(item.user.nickname);
            if (!TextUtils.isEmpty(item.user.description)) {
                holder.setText(R.id.favorite_header_text2, item.user.description);
                holder.getView(R.id.favorite_header_text2).setVisibility(View.VISIBLE);
            } else {
                holder.setText(R.id.favorite_header_text2, "");
                holder.getView(R.id.favorite_header_text2).setVisibility(View.GONE);
            }

            holder.getView(R.id.favorite_item_header).
                    setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.function_item_bg_selector));
            ((TextView) holder.getView(R.id.favorite_header_text1)).
                    setTextColor(mContext.getResources().getColor(R.color.color_333333));
            ((TextView) holder.getView(R.id.favorite_header_text2)).
                    setTextColor(mContext.getResources().getColor(R.color.color_737373));
            ((Button) holder.getView(R.id.favorite_header_button)).
                    setTextColor(mContext.getResources().getColor(R.color.color_694b37));
        }
        // 我的关注隐藏关注按钮
        if (mParamFrom == PARAM_FROM_MY)
            holder.getView(R.id.favorite_header_button).setVisibility(View.GONE);
        else { //推荐关注 显示关注按钮，但是需要设置显示的关注状态
            holder.getView(R.id.favorite_header_button).setVisibility(View.VISIBLE);
            setFollowState((Button) holder.getView(R.id.favorite_header_button), item.user, holder.getItemView());
        }
        SetStoryData(holder,item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void SetStoryData(SimpleRecyclerViewHolder holder, FavoriteList.Card data){
        int count = data.story_list != null ? data.story_list.size() : 0;
        ViewGroup viewGroup = (ViewGroup) holder.getView(R.id.favorite_story_group);
        if (count > 0) {
            viewGroup.setVisibility(View.VISIBLE);
            // view比数据多
            if (count <= viewGroup.getChildCount()) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View view = viewGroup.getChildAt(i);
                    if (i < count) {
                        setStoryToView(view, data.story_list.get(i));
                    } else {
                        setStoryToView(view, null);
                    }
                }
            } else { // view比数据少
                for (int i = 0; i < count; i++) {
                    View view;
                    if (i < viewGroup.getChildCount()) {
                        view = viewGroup.getChildAt(i);
                    } else {
                        view = mInflater.inflate(R.layout.item_favorite_story, null);
                        viewGroup.addView(view);
                    }

                    setStoryToView(view, data.story_list.get(i));
                }
            }
        } else {
            viewGroup.setVisibility(View.GONE);
        }
    }

    private void setStoryToView(View view, FavoriteList.Story storyInfo) {
        if (storyInfo != null) {
            setImageVisibleSquare((ImageView) view.findViewById(R.id.favorite_story_img),
                    TextUtils.isEmpty(storyInfo.cover) ? storyInfo.image : storyInfo.cover);
            view.setTag(storyInfo);
            view.setOnClickListener(null);
            view.setVisibility(View.VISIBLE);
            CommonHelper.updateStoryType(mContext, storyInfo.title, storyInfo.type, storyInfo.show_icon, view.findViewById(R.id.favorite_story_title));
            setStoryStyle(view);
        } else {
            view.setVisibility(View.GONE);
            view.setTag(null);
            view.setOnClickListener(null);
        }
    }

    private void setImageVisibleSquare(ImageView imgView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(mContext)
                    .load(url)
                    .dontAnimate()
                    .crossFade()
                    .placeholder(R.drawable.loading_for_follow)
                    .error(R.drawable.loading_for_follow_faild)
                    .into(imgView);
        } else {
            imgView.setVisibility(View.GONE);
        }
    }

    private void setStoryStyle(View view) {
        view.findViewById(R.id.favorite_story)
                .setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.function_item_bg_selector));
        view.findViewById(R.id.divider)
                .setBackgroundColor(mContext.getResources().getColor(R.color.color_eeeeee));
        ((TextView) view.findViewById(R.id.favorite_story_title))
                .setTextColor(mContext.getResources().getColor(R.color.color_333333));
    }

    /**
     * 设置关注按钮状态
     *
     * @param button 关注按钮
     * @param object 被关注的实体
     */
    private void setFollowState(Button button, Object object, final View view) {
        view.setTag(object);
        if (object != null && button.getVisibility() == View.VISIBLE) {
            boolean followed = false;
            if (object instanceof FavoriteList.User) {
                followed = ((FavoriteList.User) object).followed;

            } else if (object instanceof FavoriteList.Tag) {
                followed = ((FavoriteList.Tag) object).followed;
            }

            button.setTag(object);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getTag() != null ){
                        if(v.getTag() instanceof FavoriteList.User && !TextUtils.isEmpty(((FavoriteList.User) v.getTag()).id))
                            ;//follow(v.getTag());
                        if(v.getTag() instanceof FavoriteList.Tag && !TextUtils.isEmpty(((FavoriteList.Tag) v.getTag()).id))
                            ;//follow(v.getTag());
                    }
                }
            });

            if (!followed) {
                button.setText("+关注");
                button.setBackgroundResource(R.drawable.favorite_add_follow);
            } else {
                button.setText("已关注");
                button.setBackgroundResource(R.drawable.favorite_has_followed);
            }
        } else {
            button.setText("+关注");
            button.setBackgroundResource(R.drawable.favorite_add_follow);
            button.setTag(null);
            button.setOnClickListener(null);
        }
    }
}
