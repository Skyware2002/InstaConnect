package com.skyware.instaconnect.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skyware.instaconnect.Models.Reel
import com.skyware.instaconnect.R
import com.skyware.instaconnect.databinding.ReelDesignBinding
import com.squareup.picasso.Picasso

class ReelsAdapter(var context: Context,var reelList:ArrayList<Reel>) :RecyclerView.Adapter<ReelsAdapter.ViewHolder>(){

    inner class ViewHolder(var binding : ReelDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ReelDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load(reelList.get(position).profileLink).placeholder(R.drawable.user).into(holder.binding.profileImage)
        holder.binding.caption.setText(reelList.get(position).caption)
        holder.binding.videoView.setVideoPath(reelList.get(position).reelUrl)
        holder.binding.videoView.setOnPreparedListener {
            holder.binding.progressBar.visibility= View.GONE
            holder.binding.videoView.start()
        }
    }
}