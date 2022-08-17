package viewModel

import org.uqbar.commons.model.annotations.Observable

@Observable
class PostViewModel(val ide:String,var description:String,var landScape:String,var portrait:String) {

}