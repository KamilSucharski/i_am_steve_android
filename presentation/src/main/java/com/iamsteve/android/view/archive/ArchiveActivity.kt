package com.iamsteve.android.view.archive

import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityArchiveBinding
import com.iamsteve.android.view.base.BaseActivity
import com.iamsteve.domain.view.archive.ArchiveContract
import org.koin.android.ext.android.inject

class ArchiveActivity : BaseActivity<ArchiveContract.View, ArchiveContract.Presenter, ActivityArchiveBinding>(
    layoutResource = R.layout.activity_archive
), ArchiveContract.View {

    override val presenter: ArchiveContract.Presenter by inject()
}