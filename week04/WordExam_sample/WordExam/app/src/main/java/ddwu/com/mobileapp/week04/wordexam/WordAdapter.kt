package ddwu.com.mobileapp.week04.wordexam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobileapp.week04.wordexam.data.Word
import ddwu.com.mobileapp.week04.wordexam.databinding.ListWordBinding

class WordAdapter(var words: ArrayList<Word>) : RecyclerView.Adapter<WordAdapter.WordViewHolder> () {

    // RecyclerView가 얼마나 많은 아이템을 표시해야 하는지 알려주는 함수
    override fun getItemCount(): Int = words.size

    // RecyclerView가 새로운 뷰 홀더를 생성할 때 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        // list_word.xml을 인플레이트 함
        val wordBinding = ListWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(wordBinding)
    }

    // ViewHolder가 재사용될때 데이터를 해당 View에 Binding하는 함수
    // View에 데이터를 binding
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        // words[position].toString()을 호출해서 해당 위치의 단어를 tvWord testview(ListWordBinding에서 참조되는 텍스트뷰)에 표시
        holder.wordBinding.tvWord.text = words[position].toString()
        // 클릭 리스너를 설정, root는 전체 항목 레이아웃을 나타내며, 이 항목이 클릭됐을때, wordClickListener의 onWordClick함수가 호출
        holder.wordBinding.root.setOnClickListener {
            // it은 클릭된 뷰(루트 레이아웃), position은 클릭된 항목의 위치
            wordClickListener?.onWordClick(it, position)
        }
    }

    // wordBinding은 뷰 바인딩을 통해서 단어 항목 레이아웃의 모든 뷰를 참조할 수 있음
    // wordBinding.root는 전체 항목의 루트 레이아웃을 의미
    class WordViewHolder(val wordBinding: ListWordBinding) : RecyclerView.ViewHolder(wordBinding.root)

    // OnWordClickListener는 클릭 이벤트를 처리하는 인터페이스
    interface OnWordClickListener {
        fun onWordClick(view: View, pos: Int) // 단어 항목이 클릭되었을때 호출 (클릭된 뷰와 pos를 전달받음)
    }

    // 클릭 리스너를 설정하는 메서드
    // 외부에서 이 어댑터에 클릭 리스너를 연결할 수 있도록 하는 역할
    var wordClickListener: OnWordClickListener? = null

    fun setOnWordClickListener (listener: OnWordClickListener?) {
        wordClickListener = listener
    }



}