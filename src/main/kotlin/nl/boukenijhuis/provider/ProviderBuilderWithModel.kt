package nl.boukenijhuis.provider

import java.lang.reflect.InvocationTargetException

class ProviderBuilderWithModel(private val clazz: Class<*>, private val model: String) {

    fun build(): Provider {
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
