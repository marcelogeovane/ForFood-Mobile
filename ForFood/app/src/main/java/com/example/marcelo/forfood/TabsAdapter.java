package com.example.marcelo.forfood;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.marcelo.forfood.view.fragments.FinalizarPedidoFragment;
import com.example.marcelo.forfood.view.fragments.ListaPratoFragment;

public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int idx) {
        if (idx == 0) {
            return new ListaPratoFragment();
        }
            return new FinalizarPedidoFragment();
    }

}
