package com.nunnos.keepintouch.presentation.component.recyclerviews.conversationimportant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.domain.model.Conversation
import com.nunnos.keepintouch.utils.FileManager

class RVConversationImportantAdapter(private var items : MutableList<Conversation>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        (holder as RVConverationImportantAdapterViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    interface CustomClick {
        fun onItemClick(conversation: Conversation)
    }

    /**
     * CLASS VIEWHOLDER
     **/
    inner class RVConverationImportantAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var conversation: Conversation
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
            itemView.setOnClickListener(this)
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
                listener.onItemClick(conversation);
            }
        }

        fun bind(conversation: Conversation) {
            this.conversation = conversation
            chatTv.text = conversation.chat
            setTime(conversation)
            placeTv.text = conversation.place
            setImage(conversation)
        }

        private fun setTime(conversation: Conversation) {
            var time = ""
            if (conversation.time != null) {
                time = conversation.time.trim()
            }
            if (conversation.date != null) {
                if (!time.isEmpty()) {
                    time += "\n"
                }
                time += conversation.date.replace(" ", "").trim()
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