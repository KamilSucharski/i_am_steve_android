package com.iamsteve.android.view.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.iamsteve.domain.view.base.BasePresenter
import com.iamsteve.domain.view.base.BaseView

abstract class BaseActivity<V : BaseView<P>, P : BasePresenter<V>, DB : ViewDataBinding>(
    @LayoutRes private val layoutResource: Int
) : AppCompatActivity() {

    protected abstract val presenter: P
    protected lateinit var binding: DB

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResource)
        presenter.subscribe(this as V)
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }
}