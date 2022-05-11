package com.mmt.smartloan.ui.main

import com.lrx.module_base.base.BaseMVPActivity

/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
class MainActivity:BaseMVPActivity<IMainView,MainPresenter>(),IMainView {
    override fun createPresenter(): MainPresenter {
        TODO("Not yet implemented")
    }

    override fun getContentViewResId(): Int {
        TODO("Not yet implemented")
    }
}