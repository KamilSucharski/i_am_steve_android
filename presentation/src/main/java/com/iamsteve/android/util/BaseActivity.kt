package com.iamsteve.android.util

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.iamsteve.domain.util.Presenter
import com.iamsteve.domain.util.View
import org.koin.core.component.KoinComponent

abstract class BaseActivity<V : View<Presenter<V>>, DB : ViewDataBinding>(
    @LayoutRes private val layoutResource: Int
) : AppCompatActivity(), KoinComponent {

    protected abstract val presenter: Presenter<V>
    protected lateinit var binding: DB

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResource)
        presenter.subscribeView(this as V)
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        binding.unbind()
        super.onDestroy()
    }
}