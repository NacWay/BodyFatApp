package com.malov.bodyfat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment


class fatPersent : Fragment() {
    lateinit var binding: fatForFemale

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fat_persent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val femalebtn : Button = view.findViewById(R.id.femalebtn)
        val malebtn : Button = view.findViewById(R.id.malebtn)
        val frame_layout : FrameLayout = view.findViewById(R.id.frame_layout)
        val fat_forFemale = fatForFemale()
        val fat_forMale =fatForMale()
        setFemale(fat_forFemale)

            femalebtn.setOnClickListener{
                setFemale(fat_forFemale)
            }
            malebtn.setOnClickListener{
                setMale(fat_forMale)
            }

    }
    fun setFemale(fat_forFemale: fatForFemale) {
        val fat_forFemale = fatForFemale()
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fat_forFemale)
            commit()
        }
    }
    fun setMale(fat_forMale: fatForMale) {
        val fat_forMale =fatForMale()
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fat_forMale)
            commit()
        }
    }
}