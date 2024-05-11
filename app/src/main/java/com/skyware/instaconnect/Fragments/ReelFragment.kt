package com.skyware.instaconnect.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.skyware.instaconnect.Models.Reel
import com.skyware.instaconnect.adapters.ReelsAdapter
import com.skyware.instaconnect.databinding.FragmentReelBinding
import com.skyware.instaconnect.utils.REEL

class ReelFragment : Fragment() {
    private lateinit var binding: FragmentReelBinding
    lateinit var adapter:ReelsAdapter
    var reelList=ArrayList<Reel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentReelBinding.inflate(inflater, container, false)
        adapter= ReelsAdapter(requireContext(),reelList)
        binding.viewpager.adapter=adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList=ArrayList<Reel>()
            reelList.clear()

            for (i in it.documents){
                var reel=i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }

    companion object {

    }
}