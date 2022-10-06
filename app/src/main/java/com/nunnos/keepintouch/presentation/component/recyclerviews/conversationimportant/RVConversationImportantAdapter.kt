package com.nunnos.keepintouch.presentation.component.recyclerviews.conversationimportant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.domain.model.complements.Comment
import com.nunnos.keepintouch.domain.model.complements.Conversation
import com.nunnos.keepintouch.domain.model.complements.base.Complement
import com.nunnos.keepintouch.utils.FileManager

class RVConversationImportantAdapter(private var items : MutableList<Complement>, var listener : CustomClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        var layoutInflater = LayoutInflater.from(this.context)
        var view = layoutInflater.inflate(R.layout.component_conversation_important, parent, false)
        var viewholder = RVConverationImportantAdapterViewHolder(view)
        viewholder.setIsRecyclable(false)
        return viewholder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RVConverationImportantAdapterViewHolder).bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    interface CustomClick {
        fun onItemClick(complement: Complement)
    }

    /**
     * CLASS VIEWHOLDER
     **/
    inner class RVConverationImportantAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var complement: Complement
        private lateinit var cardView: CardView
        private lateinit var image: ImageView
        private lateinit var editImage: ImageView
        private lateinit var chatTv: TextView
        private lateinit var timeTv: TextView
        private lateinit var placeTv: TextView
        private lateinit var listener: CustomClick

        init {
            initView(itemView)
            setView()
            setListeners()
        }

        private fun initView(itemView: View) {
            cardView = itemView.findViewById(R.id.converation_important_card_container)
            image = itemView.findViewById(R.id.converation_important_image)
            editImage = itemView.findViewById(R.id.converation_important_edit)
            chatTv = itemView.findViewById(R.id.converation_important_text)
            timeTv = itemView.findViewById(R.id.converation_important_date)
            placeTv = itemView.findViewById(R.id.converation_important_location)
        }

        private fun setView() {
            //
        }

        private fun setListeners() {
            editImage.setOnClickListener {
                listener.onItemClick(complement);
            }
        }

        fun bind(complement: Complement, listener: CustomClick) {
            if(complement is Conversation){
                bindConversation(complement, listener)
            }
            if(complement is Comment){
                bindComment(complement, listener)
            }
        }

        private fun bindConversation(complement: Conversation, listener: CustomClick) {
            this.listener = listener
            this.complement = complement
            chatTv.text = complement.chat
            setTime(complement)
            placeTv.text = complement.place
            setImage(complement)
        }
        private fun bindComment(complement: Comment, listener: CustomClick) {
            this.listener = listener
            this.complement = complement
            chatTv.text = complement.info
            setTime(complement)
            placeTv.visibility = View.GONE
            cardView.visibility = View.GONE
        }

        private fun setTime(complement: Complement) {
            var time = ""
            if(complement is Conversation) {
                if (complement.time != null) {
                    time = complement.time.trim()
                }
            }
            if (complement.date.isNotEmpty()) {
                if (!time.isEmpty()) {
                    time += "\n"
                }
                time += complement.date.replace(" ", "").trim()
            }
            timeTv.text = time
        }

        private fun setImage(conversation: Conversation) {
            val bitmap = FileManager.getBitmapPhoto(conversation.photo)
            if (bitmap == null) {
                cardView.visibility = View.GONE
            } else {
                image.setImageBitmap(bitmap)
                image.rotation = conversation.angle
            }
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

}