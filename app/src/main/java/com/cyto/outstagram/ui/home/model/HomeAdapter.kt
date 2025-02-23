package com.cyto.outstagram.ui.home.model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import coil3.compose.AsyncImage
import com.cyto.outstagram.R

@Composable
fun HomeImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier.fillMaxWidth() // Or other modifiers as needed
    )
}

class HomeAdapter(private var homeItems: List<HomeItem>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
        val composeView: ComposeView = itemView.findViewById(R.id.compose_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_card, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = homeItems[position]
        holder.titleTextView.text = currentItem.title
        holder.descriptionTextView.text = currentItem.description
        holder.composeView.apply {
            //disposing off the previous composition to avoid resource leaks
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // the composable call
                HomeImage(imageUrl = currentItem.imageUrl)
            }
        }
    }

    override fun getItemCount(): Int = homeItems.size

    fun updateData(newItems: List<HomeItem>) {

        // While liking the post
        homeItems = newItems
        notifyDataSetChanged()
    }
}