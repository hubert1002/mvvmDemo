package com.example.testnewide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_menu2.*
import kotlinx.android.synthetic.main.fragment_menu1.*


class Menu1Fragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu1,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data:String? =  arguments?.getString("Key")
        fragment1_text.text = data
    }
}

class Menu2Fragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu2,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_to_second_fragment.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("KEY", "我是从 First 过来的")
            Navigation.findNavController(getView()!!).navigate(R.id.action_jump_fragment)
        }
    }
}