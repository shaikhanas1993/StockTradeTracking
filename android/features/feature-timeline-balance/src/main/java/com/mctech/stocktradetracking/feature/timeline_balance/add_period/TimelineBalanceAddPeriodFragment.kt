package com.mctech.stocktradetracking.feature.timeline_balance.add_period

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.library.keyboard.visibilitymonitor.extentions.closeKeyboard
import com.mctech.library.view.ktx.getValue
import com.mctech.stocktradetracking.feature.timeline_balance.databinding.FragmentTimelineAddPeriodBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimelineBalanceAddPeriodFragment : Fragment() {

	private val viewModel 	: TimelineBalanceAddViewModel by viewModel()
	private var binding   	: FragmentTimelineAddPeriodBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		return FragmentTimelineAddPeriodBinding.inflate(inflater, container, false).let {
			binding = it
			binding?.lifecycleOwner = this
			it.root
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		bindCommand(viewModel){ handleCommands(it) }
		bindListeners()
	}

	private fun handleCommands(command: ViewCommand) {
		when(command){
			is TimelineBalanceAddCommand.NavigationBack -> {
				findNavController().popBackStack()
			}
		}
	}

	private fun bindListeners() {
		binding?.let { binding ->
			binding.btAddPeriod.setOnClickListener {
				viewModel.interact(
					TimelineBalanceAddInteraction.CreatePeriod(
						binding.etPeriodTag.getValue(),
						binding.etInvestment.getValue().toDouble(),
						binding.etProfit.getValue().toDouble()
					)
				)

				activity?.currentFocus?.run {
					if(this is EditText){
						context?.closeKeyboard(this)
					}
				}
			}
		}
	}
}