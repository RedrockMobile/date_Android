package com.redrock.date2.moudel.user;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.redrock.date2.app.BaseRecyclerActivity;
import com.redrock.date2.model.bean.User;

/**
 * Created by Mr.Jude on 2015/8/11.
 */
@RequiresPresenter(FansPresenter.class)
public class FansActivity extends BaseRecyclerActivity<FansPresenter,User> {
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(parent);
    }
}
