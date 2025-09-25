package nl.boukenijhuis.provider

class ProviderBuilder(private val clazz: Class<*>) {

    fun model(model: String): ProviderBuilderWithModel {
        return ProviderBuilderWithModel(clazz, model)
    }

    override fun toString(): String {
        return "ProviderBuilder(clazz=$clazz)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProviderBuilder

        return clazz == other.clazz
    }

    override fun hashCode(): Int {
        return clazz.hashCode()
    }


}
