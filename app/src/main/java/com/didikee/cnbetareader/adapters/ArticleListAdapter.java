package com.didikee.cnbetareader.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.didikee.cnbetareader.R;
import com.didikee.cnbetareader.bean.ArticleListBean;
import com.didikee.cnbetareader.ui.views.interf.OnCommentsButtonClickListener;
import com.didikee.cnbetareader.ui.views.interf.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by didik 
 * Created time 2017/2/9
 * Description: 
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {

    private OnItemClickListener<String> itemClickListener;
    private OnCommentsButtonClickListener onCommentsButtonClickListener;
    private List<ArticleListBean.ResultBean> mArticleList;

    public void setItemClickListener(OnItemClickListener<String> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnCommentsButtonClickListener(OnCommentsButtonClickListener
                                                         onCommentsButtonClickListener) {
        this.onCommentsButtonClickListener = onCommentsButtonClickListener;
    }

    public void setData(List<ArticleListBean.ResultBean> articleList) {
        this.mArticleList = articleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .layout_item_article_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ArticleListBean.ResultBean bean = mArticleList.get(position);
        final String comments = bean.getComments();
        holder.tvComments.setText(comments);
        holder.tvReads.setText(bean.getCounter());
        holder.tvTime.setText(bean.getPubtime());
        holder.tvTitle.setText(bean.getTitle());

        final String sid = bean.getSid();
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener !=null){
                    itemClickListener.onItemClick(v,sid);
                }
            }
        });

        holder.tvComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCommentsButtonClickListener!=null){
                    onCommentsButtonClickListener.onCommentsBtnClick(sid,getInt(comments));
                }
            }
        });
    }

    private int getInt(String num){
        int result = 0;
        if (!TextUtils.isEmpty(num)){
            try {
                result = Integer.valueOf(num);
            } catch (NumberFormatException e) {
                // empty
            }
        }
        return  result;
    }

    @Override
    public int getItemCount() {
        return  mArticleList == null ? 0 : mArticleList.size();
    }


    /**
     *  获取最后一个 item 的 sid
     * @return last sid
     */
    public String getLastSid(){
        String sid;
        if (mArticleList == null || mArticleList.size()<=0){
            sid = Integer.MAX_VALUE +"";
        }else {
            int size = mArticleList.size();
            sid = mArticleList.get(size - 1).getSid();
        }
        return sid;
    }

    public void update(List<ArticleListBean.ResultBean> result) {
        if (result == null )return;
        if (mArticleList == null){
            mArticleList = result;
        }else {
            mArticleList.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_reads)
        TextView tvReads;
        @BindView(R.id.tv_comments)
        TextView tvComments;
        @BindView(R.id.item)
        View item;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
