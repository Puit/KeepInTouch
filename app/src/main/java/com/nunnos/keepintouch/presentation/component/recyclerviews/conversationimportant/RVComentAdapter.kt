package com.nunnos.keepintouch.presentation.component.recyclerviews.conversationimportant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.domain.model.complements.Comment
import com.nunnos.keepintouch.utils.TextUtils

class RVComentAdapter(private var items: MutableList<Comment>, var listener: CustomClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(this.context)
        val view = layoutInflater.inflate(R.layout.component_comment, parent, false)
        val viewholder = RVCommentAdapterViewHolder(view)
        viewholder.setIsRecyclable(false)
        return viewholder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RVCommentAdapterViewHolder).bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    interface CustomClick {
        fun onItemClick(complement: Comment)
    }

    /**
     * CLASS VIEWHOLDER
     **/
    inner class RVCommentAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var complement: Comment
        private lateinit var editImage: ImageView
        private lateinit var chatTv: TextView
        private lateinit var timeTv: TextView
        private lateinit var placeTv: TextView
        private lateinit var listener: CustomClick
        private lateinit var dotImage: ImageView

        init {
            initView(itemView)
            setView()
            setListeners()
        }

        private fun initView(itemView: View) {
            editImage = itemView.findViewById(R.id.comment_edit)
            chatTv = itemView.findViewById(R.id.comment_text)
            timeTv = itemView.findViewById(R.id.comment_date)
            placeTv = itemView.findViewById(R.id.comment_location)
            dotImage = itemView.findViewById(R.id.comment_dot)
        }

        private fun setView() {
            //
        }

        private fun setListeners() {
            editImage.setOnClickListener {
                listener.onItemClick(complement)
            }
        }

        fun bind(comment: Comment, listener: CustomClick) {
            bindComment(comment, listener)
        }

        private fun bindComment(complement: Comment, listener: CustomClick) {
            this.listener = listener
            this.complement = complement
            chatTv.text = complement.info
            setTime(complement)
            placeTv.visibility = View.GONE
            setDotVisibility()
        }

        private fun setDotVisibility() {
            if (TextUtils.isEmpty(chatTv.text as String?) || TextUtils.isEmpty(timeTv.text as String?)) {
                dotImage.visibility = View.GONE
            } else {
                dotImage.visibility = View.VISIBLE
            }
        }

        private fun setTime(complement: Comment) {
            var time = ""
            if (complement.date.isNotEmpty()) {
                if (!time.isEmpty()) {
                    time += "\n"
                }
                time += complement.date.replace(" ", "").trim()
            }
            timeTv.text = time
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

}