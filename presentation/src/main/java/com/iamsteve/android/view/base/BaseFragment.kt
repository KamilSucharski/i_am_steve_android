package com.iamsteve.android.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.iamsteve.android.util.persistence.Persistence
import com.iamsteve.domain.view.base.Presenter
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

abstract class BaseFragment<V : com.iamsteve.domain.view.base.View<Presenter<V>>, DB : ViewDataBinding>(
    @LayoutRes private val layoutResource: Int
) : Fragment(), KoinComponent {

    private val persistence by inject<Persistence>()
    protected abstract val presenter: Presenter<V>
    protected lateinit var binding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (savedInstanceState != null) {
            persistence.loadFields(this, savedInstanceState)
        }
        binding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.subscribeView(this as V)
    }

    override fun onDestroyView() {
        presenter.unsubscribe()
        binding.unbind()
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        persistence.saveFields(this, outState)
    }
}