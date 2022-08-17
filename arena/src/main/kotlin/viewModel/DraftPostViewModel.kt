package viewModel

import org.uqbar.commons.model.annotations.Observable

@Observable
class DraftPostViewModel(var portrait: String, var landscape: String, var description: String) {

}