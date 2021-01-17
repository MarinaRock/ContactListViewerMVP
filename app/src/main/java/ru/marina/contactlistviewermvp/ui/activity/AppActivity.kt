package ru.marina.contactlistviewermvp.ui.activity

import android.os.Bundle
import moxy.MvpAppCompatActivity
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.marina.contactlistviewermvp.databinding.ActivityAppBinding
import ru.marina.contactlistviewermvp.di.DI
import ru.marina.contactlistviewermvp.presenter.AppPresenter
import toothpick.Toothpick

class AppActivity : MvpAppCompatActivity(), MvpView {

    @InjectPresenter
    lateinit var presenter: AppPresenter

    @ProvidePresenter
    fun providePresenter(): AppPresenter =
        Toothpick.openScope(DI.APP_SCOPE).getInstance(AppPresenter::class.java)

    lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(DI.APP_SCOPE))

        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}