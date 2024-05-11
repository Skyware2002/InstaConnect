package com.skyware.instaconnect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.skyware.instaconnect.Models.Post
import com.skyware.instaconnect.R
import com.skyware.instaconnect.adapters.MyPostRvAdapter
import com.skyware.instaconnect.databinding.FragmentMyPostBinding


class MyPostFragment : Fragment() {
    private lateinit var binding: FragmentMyPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMyPostBinding.inflate(inflater,container,false)
        var posList=ArrayList<Post>()
        var adapter=MyPostRvAdapter(requireContext(),posList)
        binding.rvLayout.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.rvLayout.adapter=adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            var tempList = arrayListOf<Post>()
            for (i in it.documents){
                var post:Post=i.toObject<Post>()!!
                tempList.add(post)
            }
            posList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}