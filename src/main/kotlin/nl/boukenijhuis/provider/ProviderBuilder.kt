package nl.boukenijhuis.provider

import java.lang.reflect.InvocationTargetException

class ProviderBuilder(private val clazz: Class<*>) {
    private lateinit var model: String

    fun model(model: String): ProviderBuilder {
        this.model = model
        return this
    }

    fun build(): Provider {
        // TODO tests
        // TODO check if model is filled, if not use default model

        try {
            return clazz.getConstructor(String::class.java).newInstance(model) as Provider
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        }
    }
}
