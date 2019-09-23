package com.example.testnewide.chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.testnewide.R
import com.example.testnewide.TestApp
import com.example.testnewide.chat.viewmodel.*
import com.example.testnewide.databinding.FragmentChatContactBinding
import com.example.testnewide.databinding.FragmentChatMsgListBinding
import com.example.testnewide.databinding.FragmentChatThreadBinding
import com.example.testnewide.databinding.FragmentContactDetailBinding
import com.example.testnewide.livedata.ChatData
import com.example.testnewide.livedata.data.ContactRepo
import com.example.testnewide.livedata.data.MsgRepo
import com.example.testnewide.livedata.data.ThreadDao
import com.example.testnewide.livedata.data.ThreadRepo
import com.google.android.material.snackbar.Snackbar
import com.google.samples.apps.sunflower.PlantDetailFragmentArgs


open class BaseDaggerFragment:Fragment(){
    val factory: ViewModelProvider.Factory by lazy {
        if (requireActivity().application is TestApp) {
            val mainApplication = requireActivity().application as TestApp
            return@lazy mainApplication.factory
        }else{
            throw IllegalStateException("application is not PaoApp")
        }
    }

    fun <T : ViewModel> getInjectViewModel (c:Class<T>)= ViewModelProviders.of(this,factory).get(c)

}


class MsgListFragment :Fragment(){
    private val contactId :String by lazy {
        arguments!!.getString("id")
    }
    private val viewModel: MsgVM by viewModels {
        val repository = MsgRepo.getInstance(
            ChatData.getInstance(requireContext().applicationContext).msgDao())
        MsgListViewModelFactory(repository,contactId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatMsgListBinding.inflate(inflater,container,false).apply {
            lifecycleOwner = this@MsgListFragment
            val adapter = MsgAdapter()
            gardenList.adapter = adapter
            subscribeUi(adapter,this)

            fab.setOnClickListener { view ->
                viewModel.addMsgAndUpdateThread(contactId)
                Snackbar.make(view, "add success", Snackbar.LENGTH_LONG).show()
            }

        }
        return binding.root
    }

    private fun subscribeUi(adapter: MsgAdapter, binding: FragmentChatMsgListBinding) {
        viewModel.msgList.observe(viewLifecycleOwner) { msgList ->

            if (!msgList.isNullOrEmpty()){
                binding.contact = msgList[0].contact
                if(!msgList[0].messages.isNullOrEmpty()){
                    binding.hasMsg = !msgList.isNullOrEmpty()
                    adapter.msgContact = msgList[0].contact
                    adapter.submitList(msgList[0].messages)
                }
            }
        }
    }

}





class ThreadFragment :Fragment(){


    private val viewModel: ThreadVM by viewModels {
        val repository = ThreadRepo.getInstance(
            ChatData.getInstance(requireContext().applicationContext).threadDao())
        ThreadListViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatThreadBinding.inflate(inflater,container,false).apply {
            val adapter = ThreadAdapter()
            gardenList.adapter = adapter
            subscribeUi(adapter,this)
        }
        return binding.root
    }

    private fun subscribeUi(adapter: ThreadAdapter, binding: FragmentChatThreadBinding) {
        viewModel.threadList.observe(viewLifecycleOwner) { threads ->
            binding.hasThread = !threads.isNullOrEmpty()
            if (!threads.isNullOrEmpty())
                adapter.submitList(threads)
        }
    }

}

class ContactDetailFragment:Fragment(){

    private val args: PlantDetailFragmentArgs by navArgs()

    private val contactId :String by lazy {
       arguments!!.getString("id")
    }

    private val contactDetailViewModel : ContactDetailViewModel by viewModels {
        val repository = ContactRepo.getInstance(requireActivity().application)
        val threadrepo = ThreadRepo.getInstance(
            ChatData.getInstance(requireContext().applicationContext).threadDao())

        ContactDetailViewModelFactory(repository,threadrepo,contactId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactDetailBinding.inflate(inflater,container,false).apply {
            viewModel = contactDetailViewModel
            lifecycleOwner = this@ContactDetailFragment
            fab.setOnClickListener { view ->
                //jump to msg list
                val bundle = Bundle()
                bundle.putString("id",contactId)
//                var options = NavOptions.Builder()
//                    .setLaunchSingleTop(true)
//                    .setPopUpTo(R.id.chat_thread_fragment,false)
//                    .build()

                view.findNavController().navigate(R.id.action_from_contactDetail_to_msg_list,bundle)
//                contactDetailViewModel.add2Thread(contactId)
//                Snackbar.make(view, "add success", Snackbar.LENGTH_LONG).show()
            }
        }

        contactDetailViewModel.contactInfo.observe(this) { plant ->
            val shareText = if (plant == null) {
                ""
            } else {
                getString(com.google.samples.apps.sunflower.R.string.share_text_plant, plant.name)
            }
        }

//        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}



class ContactFragment : BaseDaggerFragment(){

    private val viewModel: ContactListViewModel by viewModels {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentChatContactBinding.inflate(inflater, container, false)
        context.apply {
            val adapter = ContactAdapter()
            binding.contactList.adapter = adapter
            subscribeUi(adapter)
            setHasOptionsMenu(true)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_contact_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_age -> {
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: ContactAdapter) {
        viewModel.contacts.observe(viewLifecycleOwner) { plants ->
            /**
             *  Plant may return null, but the [observe] extension function assumes it will not be null.
             *  So there will be a warning（Condition `plants != null` is always `true`） here.
             *  I am not sure if the database return data type should be defined as nullable, Such as `LiveData<List<Plant>?>` .
             */
            if (plants != null) adapter.submitList(plants)
        }
    }

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                clearAge()
            } else {
                setAge(9)
            }
        }
    }

}