package com.seniorproject.project.ui.Promotion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seniorproject.project.R
class PromotionFragment : Fragment() {

    private lateinit var promotionViewModel: PromotionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        promotionViewModel =
            ViewModelProvider(this).get(PromotionViewModel::class.java)
        val root = inflater.inflate(R.layout.promotion_fragment, container, false)
        return root
    }
}