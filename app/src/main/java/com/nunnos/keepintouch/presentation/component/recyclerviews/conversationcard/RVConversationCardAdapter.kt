package com.nunnos.keepintouch.presentation.component.recyclerviews.conversationcard

import com.nunnos.keepintouch.domain.model.complements.Conversation
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.nunnos.keepintouch.R
import androidx.cardview.widget.CardView
import android.widget.TextView
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.ContactInfoActivity
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.nunnos.keepintouch.utils.FileManager

class RVConversationCardAdapter(private val items: List<Conversation>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var context: Context? = null
    override fun getItemViewType(position: Int): Int {
        return position
    }

    /**
     * BASE FUNCTIONS
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context //Context del recyclerview
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(
            R.layout.component_conversations_recyclerview_conversation,
            parent,
            false
        )
        val viewHolder = RVConversationCardAdapterViewHolder(view)
        viewHolder.setIsRecyclable(false)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RVConversationCardAdapterViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * CLASS VIEWHOLDER
     */
    inner class RVConversationCardAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var conversation: Conversation? = null
        private var cardView: CardView? = null
        private var cardImageContainer: CardView? = null
        private var bigImage: ImageView? = null
        private var moreImage: ImageView? = null
        private var chatTv: TextView? = null
        private var timeTv: TextView? = null
        private var placeTv: TextView? = null
        private fun initView(itemView: View) {
            cardView = itemView.findViewById(R.id.recyclerView_conversation_card)
            cardImageContainer = itemView.findViewById(R.id.recyclerView_conversation_image_card)
            bigImage = itemView.findViewById(R.id.recyclerView_conversation_image)
            chatTv = itemView.findViewById(R.id.recyclerView_conversation_chat)
            timeTv = itemView.findViewById(R.id.recyclerView_conversation_time)
            placeTv = itemView.findViewById(R.id.recyclerView_conversation_place)
            moreImage = itemView.findViewById(R.id.recyclerView_conversation_more)
        }

        private fun setView() {}
        private fun setListeners() {
            moreImage!!.setOnClickListener { v: View? ->
                if (context is ContactInfoActivity) {
                    (context as ContactInfoActivity).shareViewModel.newConversation = conversation
                    (context as ContactInfoActivity).shareViewModel.showNewConversationFragment()
                }
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(conversation: Conversation) {
            this.conversation = conversation
            chatTv!!.text = conversation.chat
            setTime(conversation)
            placeTv!!.text = conversation.place
            setBigImage(conversation)
            cardView!!.setCardBackgroundColor(context!!.getColor(if (conversation.isImportant) R.color.background_red else R.color.white))
        }

        private fun setTime(conversation: Conversation) {
            var time = ""
            if (conversation.time != null) {
                time = conversation.time.trim { it <= ' ' }
            }
            if (conversation.date != null) {
                if (!time.isEmpty()) {
                    time = """
                        $time
                        
                        """.trimIndent()
                }
                time = time + conversation.date.replace(" ".toRegex(), "").trim { it <= ' ' }
            }
            timeTv!!.text = time
        }

        private fun setBigImage(conversation: Conversation) {
            val bitmap = FileManager.getBitmapPhoto(conversation.photo)
            if (bitmap == null) {
                cardImageContainer!!.visibility = View.GONE
            } else {
                bigImage!!.setImageBitmap(bitmap)
                bigImage!!.rotation = conversation.angle
            }
        }

        override fun onClick(v: View) {
            if (context is ContactInfoActivity) {
                /*
            TODO: navigate to edit
                ((MainActivity) context).getShareViewModel().setContactSelectedID(conversation.getId());
                ((MainActivity) context).getShareViewModel().navigateToContactInfo();*/
            }
        }

        init {
            initView(itemView)
            setView()
            setListeners()
            itemView.setOnClickListener(this)
        }
    }
}