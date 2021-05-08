package com.seniorproject.project

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.seniorproject.project.ui.Emergency.EmergencyFragment
import com.seniorproject.project.ui.Favourites.FavouriteFragment
import com.seniorproject.project.ui.Home.HomeFragment
import com.seniorproject.project.ui.ProfileFragment
import com.seniorproject.project.ui.Promotion.PromotionFragment



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        val  bottomNavigation: MeowBottomNavigation = findViewById(R.id.nav_view)
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_home_black_24dp))

        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_favorite_24))

        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_hamburger))

        bottomNavigation.show(1, true)
        replace(HomeFragment())
        bottomNavigation.setOnClickMenuListener { model: MeowBottomNavigation.Model ->
            when (model.id) {
                1 -> replace(HomeFragment())

                2 -> replace(FavouriteFragment())

                3 -> replace(ProfileFragment())

            }

        }
    }
    private fun replace(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }
}
