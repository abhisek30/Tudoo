package com.abhisek.tudoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.abhisek.tudoo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.clearAnimation()
        setContentView(view)

        binding.bottomNavigationView.setOnItemSelectedListener { menu ->
            when(menu.itemId){

                R.id.homeFragment -> {
                    setFragment(HomeFragment())
                    true
                }

                R.id.completedFragment -> {
                    setFragment(CompletedFragment())
                    true
                }

                R.id.blankFragment -> {
                    setFragment(BlankFragment())
                    true
                }

                else -> false
            }
        }
    }

    fun setFragment(fragment : Fragment){
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(R.id.fragmentContainerView2,fragment)
        frag.commit()
    }
}