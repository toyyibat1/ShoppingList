/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.toyyibat.shoppinglist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toyyibat.shoppinglist.databinding.RecyclerItemShoppingModelBinding


class ShoppingListFragment : Fragment() {

  private lateinit var names: Array<String>
  private lateinit var price: Array<String>
  private lateinit var descriptions: Array<String>
  private lateinit var listener: OnShoppingSelected

  companion object {

    fun newInstance(): ShoppingListFragment {
      return ShoppingListFragment()
    }
  }
  override fun onAttach(context: Context) {
    super.onAttach(context)

    if (context is OnShoppingSelected) {
      listener = context
    } else {
      throw ClassCastException(context.toString() + " must implement OnShoppingSelected.")
    }

    // Get shopping names and descriptions.
    val resources = context.resources
    names = resources.getStringArray(R.array.names)
    descriptions = resources.getStringArray(R.array.descriptions)
    price = resources.getStringArray(R.array.price)

  }

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view: View?
    view = inflater.inflate(R.layout.fragment_shopping_list, container,
      false)
    val activity = activity as Context
    val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
    recyclerView.layoutManager = GridLayoutManager(activity, 1)
    recyclerView.adapter = ShoppingListAdapter(activity)
    return view
  }


  internal inner class ShoppingListAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
      val recyclerItemShoppingModelBinding =
          RecyclerItemShoppingModelBinding.inflate(layoutInflater, viewGroup, false)
      return ViewHolder(recyclerItemShoppingModelBinding.root, recyclerItemShoppingModelBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
      val shopping = ShoppingModel(names[position], price[position],
          descriptions[position])
      viewHolder.setData(shopping)
      viewHolder.itemView.setOnClickListener { listener.onShoppingSelected(shopping) }

    }

    override fun getItemCount() = names.size
  }

  internal inner class ViewHolder constructor(itemView: View,
                                              private val recyclerItemShoppingModelBinding:
                                              RecyclerItemShoppingModelBinding
  ) :
      RecyclerView.ViewHolder(itemView) {

    fun setData(shoppingModel: ShoppingModel) {
      recyclerItemShoppingModelBinding.shoppingModel = shoppingModel
    }
  }

  interface OnShoppingSelected {
    fun onShoppingSelected(shoppingModel: ShoppingModel)
  }

}
