package ipca.example.calculator

class ItemNumber : ItemCalc {

    var valueNumber : Int?
        get() = value?.toInt()
        set(v1) {
            value = v1.toString()
        }


    constructor(valueNumber: Int?) : super() {
        this.valueNumber = valueNumber
        this.value = valueNumber.toString()
    }
}