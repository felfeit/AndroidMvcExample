package com.felfeit.androidmvcexample.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felfeit.androidmvcexample.databinding.ItemPostBinding
import com.felfeit.androidmvcexample.model.post.response.Post

class PostAdapter: ListAdapter<Post, PostAdapter.PostViewHolder>(DIFF_CALLBACK) {
    private var onClick: ((postId: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (postId: Int) -> Unit) {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.apply {
                textTitle.text = post.title
                textBody.text = post.body
                textTags.text = "Tags: ${post.tags.joinToString(", ")}"
                textLikes.text = post.reactions.likes.toString()
                textDislikes.text = post.reactions.dislikes.toString()
                textViews.text = post.views.toString()

                root.setOnClickListener {
                    onClick?.invoke(post.id)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}